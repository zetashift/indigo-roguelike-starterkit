package roguescave.entities

import indigo._

case class Model(screen: Size, player: Player)

object Model:
  def initial(screenSize: Size): Model =
    Model(screenSize, Player.initial(screenSize))

case class Player(position: Point):
  def moveUp: Player =
    this.copy(position = position + Point(0, -1))

  def moveDown: Player =
    this.copy(position = position + Point(0, 1))

  def moveLeft: Player =
    this.copy(position = position + Point(-1, 0))

  def moveRight: Player =
    this.copy(position = position + Point(1, 0))

object Player:
  def initial(screenSize: Size): Player =
    Player(screenSize.toPoint / 2)
