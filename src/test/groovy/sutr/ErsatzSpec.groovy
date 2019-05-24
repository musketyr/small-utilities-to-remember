package sutr

import com.stehno.ersatz.ContentType
import com.stehno.ersatz.ErsatzServer
import spock.lang.AutoCleanup
import spock.lang.Specification


class ErsatzSpec extends Specification {

    @AutoCleanup ErsatzServer server = new ErsatzServer()

    void setup() {
        server.expectations {
            get('/hello') {
                responds().body('Hello World', ContentType.TEXT_PLAIN)
            }
        }
    }

    void 'expect hello'() {
        expect:
            new URL(server.httpUrl + '/hello').text == "Hello World"
    }

}