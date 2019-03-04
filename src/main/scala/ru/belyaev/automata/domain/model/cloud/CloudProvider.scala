package ru.belyaev.automata.domain.model.cloud

/**
  * @author avbelyaev
  */
trait CloudProvider {

  def activeInstances(): List[CloudResource] = List.empty

  def activeVolumes(): List[CloudResource] = List.empty
}
