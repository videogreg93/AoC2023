package screens.scene

import base.BaseActor
import com.badlogic.gdx.math.Vector2
import gaia.base.Crew
import gaia.managers.MegaManagers
import ktx.actors.setPosition
import ktx.math.minus
import ui.BasicScreen

class ActorDragHandler() : BaseActor() {
    var actor: BaseActor? = null
    var deltaFromMouse: Vector2 = Vector2(0f, 0f)
    var currentScreen: BasicScreen? = null
    var grid = 1

    override fun onAddedToCrew(crew: Crew) {
        super.onAddedToCrew(crew)
        currentScreen = MegaManagers.screenManager.getCurrentScreen()
    }

    fun grabActor(actor: BaseActor, mousePos: Vector2) {
        this.actor = actor
        deltaFromMouse = actor.pos() - mousePos
    }

    fun letActorGo() {
        actor = null
    }

    override fun act(delta: Float) {
        super.act(delta)
        actor?.let {
            currentScreen?.let { screen ->
                val mousePos = screen.getMousePosition()
                val gridClampX = (mousePos.x + deltaFromMouse.x).toInt() % grid
                val gridClampy = (mousePos.y + deltaFromMouse.y).toInt() % grid
                it.setPosition(
                    (mousePos.x + deltaFromMouse.x).toInt() - gridClampX,
                    (mousePos.y + deltaFromMouse.y).toInt() - gridClampy
                )
            }
        }
    }
}
