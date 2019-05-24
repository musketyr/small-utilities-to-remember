package sutr

import com.agorapulse.dru.Dru
import com.agorapulse.dru.PreparedDataSet
import org.junit.Rule
import spock.lang.Specification



class DruSpec extends Specification {

    @Rule Dru dru = Dru.plan {
        from 'conference.json', {
            map { to Conference }
        }
    }

    static final PreparedDataSet CONFERENCE_DATA_SET = Dru.prepare {
        from 'conference.json', {
            map { to Conference }
        }
    }

    void setup() {
        dru.load()
    }

    void 'expect hello'() {
        when:
            Conference conference = dru.findByType(Conference)
        then:
            conference
            conference.speakers
            conference.speakers.size() == 1
            conference.speakers[0].name == 'Vladimir Orany'
            conference.sessions
            conference.sessions.size() == 1
            conference.sessions[0].name == 'Simple Utilities to Remember'
            conference.sessions[0].speakers
            conference.sessions[0].speakers.size() == 1
            conference.sessions[0].speakers[0].bio == "The Testing Guy"
    }

}