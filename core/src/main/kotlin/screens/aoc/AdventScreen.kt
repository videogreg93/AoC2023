package screens.aoc

import Globals
import base.BaseActor
import com.badlogic.gdx.Gdx
import gaia.base.Clickable
import gaia.managers.MegaManagers
import gaia.managers.assets.AssetManager.Companion.get
import gaia.managers.input.ActionListener
import gaia.ui.utils.addForeverAction
import gaia.ui.utils.alignBottom
import gaia.ui.utils.alignLeft
import gaia.ui.utils.alignLeftToRightOf
import screens.aoc.day2.Day2
import screens.aoc.main.CalendarScreen
import ui.BasicScreen

abstract class AdventScreen(day: String): BasicScreen(day) {
    var heldSliderButton: SpeedSlider? = null

    val filename: String = "assets/$day.txt"

    protected fun getInput(): List<String> {
        val file = Gdx.files.local(filename)
        return file.file().readLines()
    }

    abstract fun isDone(): Boolean
    open fun onFinish() {}
    open val background: List<BaseActor> by lazy { listOf(BaseActor(Day2.backgroundTexture.get())) }

    override fun firstShown() {
        super.firstShown()
        background.forEach {
            it.center()
            backgroundCrew.addMember(it)
        }
        val slider = SpeedSlider("Speed").apply {
            alignBottom(20f)
            alignLeft(20f)
            forceValue(0.3f)
            onValueChange = {
                val goodValue = ((it * 50) * 10).toInt() / 10f
                Globals.gameSpeed = goodValue
            }
        }
        val insaneButton = InsaneButton().apply {
            addForeverAction {
                centerOn(slider)
                alignLeftToRightOf(slider, 20f)
            }
        }
        hudCrew.addMembers(slider, insaneButton)
    }

    var calledFinished = false

    override fun render(delta: Float) {
        super.render(delta)
        if (isDone()) {
            Globals.gameSpeed = 1f
            if (!calledFinished) {
                calledFinished = true
                onFinish()
            }
        }
    }

    override fun onAction(action: ActionListener.InputAction): Boolean {
        when (action) {
            ActionListener.InputAction.CLICK -> {
                getMembersUnderMouse(hudCrew.members).firstOrNull()?.let {
                    when (it) {
                        is SpeedSlider -> {
                            val mousePos = getMousePosition()
                            it.startFollowingMouse(mousePos.x, mousePos.y)
                            heldSliderButton = it
                        }
                        is Clickable -> {
                            it.onClick()
                        }

                        else -> {}
                    }
                }
            }
            ActionListener.InputAction.QUIT -> {
                MegaManagers.screenManager.returnToScreen(CalendarScreen::class.java, true)
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
