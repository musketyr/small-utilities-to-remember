package sutr.export

import builders.dsl.spreadsheet.builder.api.SpreadsheetBuilder
import groovy.transform.CompileStatic
import sutr.Conference
import sutr.Session

@CompileStatic
class SpreadsheetExporter {

    static void exportConference(SpreadsheetBuilder builder, Conference conference) {
        builder.build {
            style('header') {
                font {
                    style bold
                }
            }

            sheet('Sessions') {
                freeze(0, 1)
                filter auto
                row {
                    style 'header'
                    cell {
                        value "Speakers"
                        width auto
                    }
                    cell {
                        value "Name"
                        width auto
                    }
                    cell {
                        value "Annotation"
                        width auto
                    }
                }

                for (Session session in conference.sessions) {
                    row {
                        cell session.speakers.join(', ')
                        cell session.name
                        cell session.annotation
                    }
                }
            }
        }
    }

}
