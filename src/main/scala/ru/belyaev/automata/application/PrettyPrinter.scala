package ru.belyaev.automata.application

import ru.belyaev.automata.domain.model.CloudResource

import scala.xml.XML

/**
  * @author avbelyaev
  */
object PrettyPrinter {

  def generateTable(data: List[CloudResource]): String =
  // @formatter:off
    <table>
      <thead>
        { data.head.tableHeader() }
      </thead>
      <tbody>
        { data.map(row => row.tableRow()) }
      </tbody>
    </table>.toString()
  // @formatter:on

  def generateMessage(content: String): String =
  // @formatter:off
    <html>
      <head></head>
      <body>
        { XML.loadString(content) }
        <br/>
        <p>Sincerely yours, cloud-host-automation</p>
      </body>
    </html>.toString()
  // @formatter:on
}
