package ru.belyaev.automata.port.adapter

import ru.belyaev.automata.domain.model.CloudResource

import scala.xml.{Elem, XML}

/**
  * @author avbelyaev
  */
object PrettyPrinter {

  def generateTable(resourceList: List[CloudResource]): String =
  // @formatter:off
    <table>
      <thead>
        { tableHeader() }
      </thead>
      <tbody>
        { resourceList.map(resource => toTableRow(resource)) }
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

  def createTableRow(cells: Any*): Elem =
  // @formatter:off
    <tr>{ cells.map(cell =>
      <td>{ cell }</td>
    )}
    </tr>
  // @formatter:on

  def tableHeader(): Elem =
    createTableRow("Runtime, h", "Name", "Type", "IP")

  def toTableRow(resource: CloudResource): Elem =
    createTableRow(resource.runtimeHours().toString, resource.name, resource.resourceType, resource.ip)
}
