package sutr

import com.agorapulse.gru.Gru
import com.agorapulse.gru.http.Http
import com.agorapulse.testing.fixt.Fixt
import com.stehno.ersatz.ContentType
import com.stehno.ersatz.ErsatzServer
import org.junit.Rule
import spock.lang.AutoCleanup
import spock.lang.Specification


class JsonUnitWithGruSpec extends Specification {

    @AutoCleanup ErsatzServer server = new ErsatzServer()
    @Rule Gru<Http> gru = Gru.equip(Http.steal(this))
    @Rule Fixt fixt = Fixt.create(JsonUnitWithGruSpec)

    void setup() {
        server.expectations {
            get('/conference') {
                responds().body(fixt.readText('actual.json'), ContentType.APPLICATION_JSON)
            }
        }

        gru.prepare {
            baseUri server.httpUrl
        }
    }

    void 'expect hello'() {
        expect:
            gru.test {
                get '/conference'
                expect {
                    json 'expected.json'
                }
            }
    }

}