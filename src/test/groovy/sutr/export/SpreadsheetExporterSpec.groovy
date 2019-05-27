package sutr.export

import builders.dsl.spreadsheet.builder.api.SpreadsheetBuilder
import builders.dsl.spreadsheet.builder.poi.PoiSpreadsheetBuilder
import builders.dsl.spreadsheet.query.api.SpreadsheetCriteria
import builders.dsl.spreadsheet.query.poi.PoiSpreadsheetCriteria
import com.agorapulse.dru.Dru
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import spock.lang.Specification
import sutr.Conference
import sutr.DruSpec

import java.awt.Desktop


class SpreadsheetExporterSpec extends Specification {

    @Rule TemporaryFolder tmp = new TemporaryFolder()

    @Rule Dru dru = Dru.plan {
        include DruSpec.CONFERENCE_DATA_SET
    }

    void setup() {
        dru.load()
    }

    void 'expect hello'() {
        when:
            Conference conference = dru.findByType(Conference)

            File excel = tmp.newFile("example${System.currentTimeMillis()}.xlsx")

            SpreadsheetBuilder spreadsheetBuilder = PoiSpreadsheetBuilder.create(excel)

            SpreadsheetExporter.exportConference(spreadsheetBuilder, conference)

            open excel

            SpreadsheetCriteria criteria = PoiSpreadsheetCriteria.FACTORY.forFile(excel)
        then:
            criteria.query {
                sheet('Sessions')
            }.sheets.size() == 1
            criteria.query {
                sheet('Sessions')
            }.rows.size() == 2
            criteria.query {
                sheet('Sessions') {
                    row(2) {
                        cell('A') {
                            value 'Vladimir Orany'
                        }
                    }
                }
            }.cells.size() == 1
    }


    private static void open(File file) {
        if (Desktop.isDesktopSupported() && Desktop.desktop.isSupported(Desktop.Action.OPEN)) {
            Desktop.desktop.open(file)
            Thread.sleep(10_000)
        }
    }

}