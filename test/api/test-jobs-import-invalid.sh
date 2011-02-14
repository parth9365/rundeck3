#!/bin/bash

#test /api/jobs/import with invalid input

errorMsg() {
   echo "$*" 1>&2
}
assert(){
    # assert expected, actual
    if [ "$1" != "$2" ] ; then
        errorMsg "FAIL: Expected value \"$1\" but saw: \"$2\" ${3}"
        exit 2
    fi
}

DIR=$(cd `dirname $0` && pwd)

# accept url argument on commandline, if '-' use default
url="$1"
if [ "-" == "$1" ] ; then
    url='http://localhost:4440/api'
fi
apiurl="${url}/api"

VERSHEADER="X-RUNDECK-API-VERSION: 1.2"

# curl opts to use a cookie jar, and follow redirects, showing only errors
CURLOPTS="-s -S -L -c $DIR/cookies -b $DIR/cookies"
CURL="curl $CURLOPTS"


XMLSTARLET=xml
args="echo hello there"

project=$2
if [ "" == "$2" ] ; then
    project="test"
fi

#escape the string for xml
xmlargs=$($XMLSTARLET esc "$args")
xmlproj=$($XMLSTARLET esc "$project")

#produce job.xml content corresponding to the dispatch request
cat > $DIR/temp.out <<END
<joblist>
   <job>
      <name>cli job</name>
      <group>api-test</group>
      <description></description>
      <loglevel>INFO</loglevel>
      <context>
          <project>$xmlproj</project>
      </context>
      <dispatch>
        <threadcount>1</threadcount>
        <keepgoing>true</keepgoing>
      </dispatch>
      <sequence>
        <command>
        <exec>$xmlargs</exec>
        </command>
      </sequence>
   </job>
</joblist>

END

# now submit req
runurl="${apiurl}/jobs/import"

echo "TEST: /jobs/import with invalid format"

#specify incorrect format
params="format=DNEformat"

# specify the file for upload with curl, named "xmlBatch"
ulopts="-F xmlBatch=@$DIR/temp.out"

CURL_REQ_OPTS=$ulopts sh $DIR/api-expect-error.sh "${runurl}" "${params}" "The specified format is not supported: DNEformat" || exit 2
echo "OK"

##
# try to make GET request without import file content
##
echo "TEST: /jobs/import with wrong http Method"

sh $DIR/api-expect-code.sh 405 "${runurl}" "${params}" || exit 2
echo "OK"


##
# try to make POST request without import file content
##

echo "TEST: /jobs/import without expected file content"

CURL_REQ_OPTS="-F x=y" sh $DIR/api-expect-error.sh "${runurl}" "${params}" "No file was uploaded" || exit 2
echo "OK"

##
# specify invalid import content
##

#No context/project value
cat > $DIR/temp.out <<END
<joblist>
   <job>
      <name>cli job</name>
      <group>api-test</group>
      <description></description>
      <loglevel>INFO</loglevel>
      <context>
          <project>DNEProj</project>
      </context>
      <dispatch>
        <threadcount>1</threadcount>
        <keepgoing>true</keepgoing>
      </dispatch>
      <sequence>
        <command>
        <exec>$xmlargs</exec>
        </command>
      </sequence>
   </job>
</joblist>

END

params="format=xml"
# specify the file for upload with curl, named "xmlBatch"
ulopts="-F xmlBatch=@$DIR/temp.out"

echo "TEST: /jobs/import with bad definition"

# get listing
$CURL $ulopts --header "$VERSHEADER" ${runurl}?${params} > $DIR/curl.out
if [ 0 != $? ] ; then
    errorMsg "ERROR: failed query request"
    exit 2
fi

sh $DIR/api-test-success.sh $DIR/curl.out || exit 2

# expect a /result/failed item

failedcount=$($XMLSTARLET sel -T -t -v "/result/failed/@count" $DIR/curl.out)
succount=$($XMLSTARLET sel -T -t -v "/result/succeeded/@count" $DIR/curl.out)
skipcount=$($XMLSTARLET sel -T -t -v "/result/skipped/@count" $DIR/curl.out)

if [ "1" != "$failedcount" ] ; then
    errorMsg  "Upload was not successful."
    exit 2
fi

# verify results
jid=$($XMLSTARLET sel -T -t -v "/result/failed/job/id" $DIR/curl.out)
jname=$($XMLSTARLET sel -T -t -v "/result/failed/job/name" $DIR/curl.out)
jgroup=$($XMLSTARLET sel -T -t -v "/result/failed/job/group" $DIR/curl.out)
jproj=$($XMLSTARLET sel -T -t -v "/result/failed/job/project" $DIR/curl.out)

assert "cli job" "$jname" "Wrong job name: $jname"
assert "api-test" "$jgroup" "Wrong job group: $jgroup"
assert "DNEProj" "$jproj" "Wrong job project: $jproj"

if [ "" != "$jid" ] ; then
    errorMsg "Did not expect job id in result: $jid"
    exit 2
fi

echo "OK"



rm $DIR/curl.out
rm $DIR/temp.out

