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

package dtolabs.client.utils;

/**
 * Constants for client-server communication
 */
public class Constants {
    /**
     * HTTP Header for result string
     */
    public static final String X_RUNDECK_RESULT_HEADER = "X-Rundeck-Result";
    /**
     * HTTP header for unauthorized message
     */
    public static final String X_RUNDECK_ACTION_UNAUTHORIZED_HEADER = "X-Rundeck-Action-Unauthorized";
}