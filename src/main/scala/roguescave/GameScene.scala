package roguescave

import indigo._
import indigo.scenes._

import roguescave.terminal.{TerminalEntity, TerminalText}
import roguescave.terminal.MapTile
import roguescave.terminal.TerminalEmulator
import roguescave.entities.Model

object GameScene extends Scene[Unit, Model, Unit]:
  type SceneModel     = Model
  type SceneViewModel = Unit

  val name: SceneName =
    SceneName("first floor")

  val modelLens: Lens[Model, Model] =
    Lens.keepLatest

  val viewModelLens: Lens[Unit, Unit] =
    Lens.keepLatest

  val eventFilters: EventFilters =
    EventFilters.Permissive

  val subSystems: Set[SubSystem] =
    Set()

  def updateModel(context: FrameContext[Unit], model: Model): GlobalEvent => Outcome[Model] =
    case KeyboardEvent.KeyUp(Key.UP_ARROW) =>
      Outcome(model.copy(player = model.player.moveUp))

    case KeyboardEvent.KeyUp(Key.DOWN_ARROW) =>
      Outcome(model.copy(player = model.player.moveDown))

    case KeyboardEvent.KeyUp(Key.LEFT_ARROW) =>
      Outcome(model.copy(player = model.player.moveLeft))

    case KeyboardEvent.KeyUp(Key.RIGHT_ARROW) =>
      Outcome(model.copy(player = model.player.moveRight))

    case _ =>
      Outcome(model)

  def updateViewModel(
      context: FrameContext[Unit],
      model: Model,
      viewModel: Unit
  ): GlobalEvent => Outcome[Unit] =
    _ => Outcome(viewModel)

  // This shouldn't live here really, just keeping it simple for demo purposes.
  val terminal: TerminalEmulator =
    TerminalEmulator(RogueLikeGame.screenSize)

  def present(context: FrameContext[Unit], model: Model, viewModel: Unit): Outcome[SceneUpdateFragment] =
    Outcome(
      SceneUpdateFragment(
        terminal
          .put(model.player.position, DfTiles.Tile.`@`, RGB.fromHexString("ffb732"))
          .draw(Assets.tileMap, RogueLikeGame.charSize, MapTile(DfTiles.Tile.SPACE))
      )
    )
