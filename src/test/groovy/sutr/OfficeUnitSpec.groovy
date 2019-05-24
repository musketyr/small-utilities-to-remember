package sutr

import com.agorapulse.testing.fixt.Fixt
import com.agorapulse.testing.officeunit.OfficeUnit
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import org.testng.reporters.Files
import spock.lang.Specification

class OfficeUnitSpec extends Specification {

    @Rule TemporaryFolder tmp = new TemporaryFolder()
    @Rule Fixt fixt = Fixt.create(OfficeUnitSpec)

    void 'expect hello'() {
        expect:
            new OfficeUnit().compare(
                    loadFile('control.pptx'),
                    loadFile('test.pptx')
            ).empty
    }

    private File loadFile(String name) {
        File file = tmp.newFile(name)
        Files.copyFile(fixt.readStream(name), file)
        return file
    }

}