package ru.belyaev.automata.domain.model

/**
  * @author avbelyaev
  */
trait CloudApi {

  def activeInstances(): List[CloudResource] = List.empty

  def activeVolumes(): List[CloudResource] = List.empty
}
