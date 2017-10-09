/*
 * Copyright 2016 SimplifyOps, Inc. (http://simplifyops.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
* PrefixGenerator.java
* 
* User: Greg Schueler <a href="mailto:greg@dtosolutions.com">greg@dtosolutions.com</a>
* Created: May 26, 2010 11:16:47 AM
* $Id$
*/
package dtolabs.rundeck.core.utils;

import java.util.Map;

/**
 * Reformatter producess a formatted message using contextual data and an input message.
 *
 * @author Greg Schueler <a href="mailto:greg@dtosolutions.com">greg@dtosolutions.com</a>
 * @version $Revision$
 */
public interface Reformatter {
    /**
     * Produce reformatted message based on context data and the input message.
     *
     * @param context data
     * @param message message
     *
     * @return new message string
     */
    public String reformat(Map<String, String> context, String message);

    public String getTail();

    public String getHead();
}
