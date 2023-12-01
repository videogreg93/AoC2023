package screens.aoc.main

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.odencave.assets.Assets
import gaia.base.BaseNinePatchActor
import gaia.base.Clickable
import gaia.managers.MegaManagers
import gaia.managers.assets.Asset
import gaia.managers.assets.AssetManager.Companion.get
import gaia.ui.generic.Label
import gaia.ui.utils.addForeverAction
import ui.BasicScreen
import kotlin.properties.Delegates

class DayButton(dayNumber: String, val screen: BasicScreen?): BaseNinePatchActor(buttonNinePatch()), Clickable {
    val notPressedNinePatch = buttonNinePatch()
    val pressedNinePatch = buttonPressedNinePatch()
    val enabled: Boolean = screen != null
    private val label = Label(dayNumber, MegaManagers.fontManager.defaultFont).apply {
        addForeverAction {
            centerOn(this@DayButton)
        }
    }

    var isPressed by Delegates.observable(false) { property, oldValue, newValue ->
        ninePatch = if (newValue) {
            pressedNinePatch
        } else {
            notPressedNinePatch
        }
    }

    init {
        children.add(label)
        width = 175f
        height = 200f
        if (!enabled) {
            isPressed = true
        }
    }

    override fun onClick(): Boolean {
        return if (enabled && screen != null) {
            isPressed = true
            true
        } else {
            false
        }
    }

    fun goToScreen() {
        if (screen != null) {
            MegaManagers.screenManager.changeScreen(screen)
        }
    }

    companion object {
        @Asset
        private val buttonTexture = AssetDescriptor(Assets.Buttons.button, Texture::class.java)

        @Asset
        private val buttonPressedTexture = AssetDescriptor(Assets.Buttons.button_pressed, Texture::class.java)

        private fun buttonNinePatch() = NinePatch(buttonTexture.get(), 24, 24, 24 ,24 )
        private fun buttonPressedNinePatch() = NinePatch(buttonPressedTexture.get(), 24, 24, 24 ,24 )
    }
}
