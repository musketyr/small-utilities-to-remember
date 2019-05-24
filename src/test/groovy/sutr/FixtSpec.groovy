package sutr

import com.agorapulse.testing.fixt.Fixt
import com.stehno.ersatz.ContentType
import com.stehno.ersatz.ErsatzServer
import org.junit.Rule
import spock.lang.AutoCleanup
import spock.lang.Specification


class FixtSpec extends Specification {

    @AutoCleanup ErsatzServer server = new ErsatzServer()

    @Rule Fixt fixt = Fixt.create(FixtSpec)

    void setup() {
        server.expectations {
            get('/hello') {
                responds().body(fixt.readText('hello.txt'), ContentType.TEXT_PLAIN)
            }
        }
    }

    void 'expect hello'() {
        expect:
            new URL(server.httpUrl + '/hello').text == "Hello World"
    }

}