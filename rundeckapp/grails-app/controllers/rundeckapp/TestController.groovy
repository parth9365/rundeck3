package rundeckapp

import grails.config.Config

class TestController {

    def index() {
        Config config = grailsApplication.config

        render  'config value: ' + grailsApplication.config.get("rundeck.security.authorization.preauthenticated.redirectLogout")

    }
}
