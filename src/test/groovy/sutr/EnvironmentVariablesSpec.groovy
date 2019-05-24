package sutr

import org.junit.Rule
import org.junit.contrib.java.lang.system.EnvironmentVariables
import spock.lang.Specification

class EnvironmentVariablesSpec extends Specification {

    @Rule EnvironmentVariables environmentVariables = new EnvironmentVariables()

    void 'set env vars'() {
        when:
            environmentVariables.set("name", "value")
        then:
            System.getenv("name") == "value"
    }
}