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
* FileCopierException.java
* 
* User: Greg Schueler <a href="mailto:greg@dtosolutions.com">greg@dtosolutions.com</a>
* Created: 3/22/11 2:33 PM
* 
*/
package dtolabs.rundeck.core.execution.service;

import com.dtolabs.rundeck.core.execution.workflow.steps.FailureReason;


/**
 * FileCopierException is ...
 *
 * @author Greg Schueler <a href="mailto:greg@dtosolutions.com">greg@dtosolutions.com</a>
 */
public class FileCopierException extends Exception {
    private FailureReason failureReason;

    public FileCopierException(String msg, FailureReason failureReason) {
        super(msg);
        this.failureReason = failureReason;
    }

    public FileCopierException(String msg, FailureReason failureReason, Exception cause) {
        super(msg, cause);
        this.failureReason = failureReason;
    }

    public FailureReason getFailureReason() {
        return failureReason;
    }
}
