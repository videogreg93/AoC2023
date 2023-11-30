package screens.aoc

import Globals
import com.badlogic.gdx.Gdx
import gaia.managers.input.ActionListener
import gaia.ui.utils.alignBottom
import gaia.ui.utils.alignLeft
import ui.BasicScreen

abstract class AdventScreen(day: String): BasicScreen(day) {
    var heldSliderButton: SpeedSlider? = null

    val filename: String = "assets/$day.txt"

    protected fun getInput(): List<String> {
        val file = Gdx.files.local(filename)
        return file.file().readLines()
    }

    override fun firstShown() {
        super.firstShown()
        val slider = SpeedSlider("Speed").apply {
            alignBottom(20f)
            alignLeft(20f)
            forceValue(0.3f)
            onValueChange = {
                val goodValue = ((it * 10) * 10).toInt() / 10f
                Globals.gameSpeed = goodValue
            }
        }
        crew.addMember(slider)
    }

    override fun onAction(action: ActionListener.InputAction): Boolean {
        when (action) {
            ActionListener.InputAction.CLICK -> {
                getMembersUnderMouse().firstOrNull()?.let {
                    when (it) {
                        is SpeedSlider -> {
                            val mousePos = getMousePosition()
                            it.startFollowingMouse(mousePos.x, mousePos.y)
                            heldSliderButton = it
                        }
                    }
                }
            }
            else -> {
                return false
            }
        }
        return true
    }

    override fun onActionReleased(action: ActionListener.InputAction): Boolean {
        when (action) {
            ActionListener.InputAction.CLICK -> {
                heldSliderButton?.stopFollowingMouse()
            }
            else -> return false
        }
        return true
    }
}
