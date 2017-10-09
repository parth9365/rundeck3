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

package dtolabs.rundeck.core.authorization;

import java.util.Map;
import java.util.Set;

public class AuthorizationRequestBuilder {
    private Set<Attribute> environment;
    private String action;
    private AclSubject subject;
    private Map<String, String> resource;

    private AuthorizationRequestBuilder() {
    }

    public AuthorizationRequestBuilder environment(final Set<Attribute> environment) {
        this.environment = environment;
        return this;
    }

    public AuthorizationRequestBuilder action(final String action) {
        this.action = action;
        return this;
    }

    public AuthorizationRequestBuilder subject(final AclSubject subject) {
        this.subject = subject;
        return this;
    }

    public AuthorizationRequestBuilder resource(final Map<String, String> resource) {
        this.resource = resource;
        return this;
    }

    public static AuthorizationRequestBuilder builder() {
        return new AuthorizationRequestBuilder();
    }

    public AuthorizationRequest build() {
        return new AuthorizationRequestImpl(environment, action, subject, resource);
    }
}