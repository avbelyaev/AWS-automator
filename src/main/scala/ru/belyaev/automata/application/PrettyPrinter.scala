package ru.belyaev.automata.application

/**
  * @author avbelyaev
  */
object PrettyPrinter {

  def generateTable(data: List[List[Any]], header: List[String]): String =
  // @formatter:off
    <table>
      <thead>
        <tr>{ header.map(cell => <td>{ cell }</td>) }</tr>
      </thead>
      <tbody>
        { data.map(row =>
          <tr>
            { row.map(cell => <td>{ cell }</td>) }
          </tr>
        )}
      </tbody>
    </table>.toString()
    // @formatter:on
}
