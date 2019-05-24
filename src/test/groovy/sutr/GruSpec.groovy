package sutr

import com.agorapulse.gru.Gru
import com.agorapulse.gru.http.Http
import com.stehno.ersatz.ContentType
import com.stehno.ersatz.ErsatzServer
import org.junit.Rule
import spock.lang.AutoCleanup
import spock.lang.Specification


class GruSpec extends Specification {

    @AutoCleanup ErsatzServer server = new ErsatzServer()
    @Rule Gru<Http> gru = Gru.equip(Http.steal(this))

    void setup() {
        server.expectations {
            get('/hello') {
                responds().body('Hello World', ContentType.TEXT_PLAIN)
            }
        }

        gru.prepare {
            baseUri server.httpUrl
        }
    }

    void 'expect hello'() {
        expect:
            gru.test {
                get '/hello'
                expect {
                    text inline('Hello World')
                }
            }
    }

}