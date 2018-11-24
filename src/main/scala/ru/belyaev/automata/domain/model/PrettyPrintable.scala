package ru.belyaev.automata.domain.model

import scala.xml.Elem

/**
  * @author avbelyaev
  */
trait PrettyPrintable {

  def tableHeader(): Elem

  def tableRow(): Elem

  protected final def toTableRow(cells: Any*): Elem =
  // @formatter:off
    <tr>{ cells.map(cell =>
      <td>{ cell }</td>
    )}
    </tr>
  // @formatter:on
}
