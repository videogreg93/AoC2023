package screens.aoc.main

import Globals
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.graphics.Texture
import com.odencave.assets.Assets
import com.odencave.i18n.gaia.base.BackgroundGrid
import gaia.managers.assets.Asset
import gaia.managers.assets.AssetManager.Companion.get
import gaia.managers.input.ActionListener
import screens.aoc.Day1
import ui.BasicScreen

class CalendarScreen: BasicScreen("Calendar Screen") {

    private val existingDays = listOf<BasicScreen>(
        Day1()
    )

    override fun firstShown() {
        super.firstShown()
        backgroundCrew.addMembers(getBackground4())
        val buttons = (0..24).map {
            val dayScreen = existingDays.getOrNull(it)
            DayButton((it + 1).toString(), dayScreen)
        }
        val startingMargin = (-Globals.WORLD_WIDTH/2f + 170f)
        val margin = 50f
        val yMargin = 50f
        val itemsPerRow = 7
        buttons.forEachIndexed { index, dayButton ->
            val xPos = startingMargin + (index % itemsPerRow) * (margin + dayButton.width)
            val row = (index / itemsPerRow)
            val yPos = 250f - row * (dayButton.height + yMargin)
            dayButton.setPosition(xPos, yPos)
            crew.addMember(dayButton)
        }
    }

    override fun onAction(action: ActionListener.InputAction): Boolean {

        return true
    }

    fun getBackground4(): List<BackgroundGrid> {
        return listOf(
            BackgroundGrid(bg4_1Asset.get(), 1 * 2, 0),
            BackgroundGrid(bg4_2Asset.get(), 2 * 2, 0)
        )
    }

    companion object {
        @Asset
        val bg4_1Asset = AssetDescriptor(Assets.Backgrounds.Bg4.bg1, Texture::class.java)

        @Asset
        val bg4_2Asset = AssetDescriptor(Assets.Backgrounds.Bg4.bg2, Texture::class.java)
    }
}
