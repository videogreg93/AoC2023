package screens.aoc.day1

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.odencave.assets.Assets
import gaia.base.BaseNinePatchActor
import gaia.managers.MegaManagers
import gaia.managers.assets.Asset
import gaia.managers.assets.AssetManager.Companion.get
import gaia.ui.generic.Label
import gaia.ui.utils.addForeverAction
import gaia.utils.wrapped
import java.awt.SystemColor.text

class LineButton(text: String) : BaseNinePatchActor(buttonNinePatch()) {
    var xSpeed = 0f
    var ySpeed = 0f

    val label =
        Label(text.wrapped(MegaManagers.fontManager.smallerFont, 125), MegaManagers.fontManager.smallerFont).apply {
            addForeverAction {
                centerOn(this@LineButton)
            }
        }

    init {
        children.add(label)
        width = 150f
        height = 150f
    }

    override fun act(delta: Float) {
        super.act(delta)
        moveBy(xSpeed * delta, ySpeed * delta)
    }

    companion object {
        @Asset
        private val buttonTexture = AssetDescriptor(Assets.Buttons.button, Texture::class.java)

        @Asset
        private val buttonPressedTexture = AssetDescriptor(Assets.Buttons.button_pressed, Texture::class.java)

        private fun buttonNinePatch() = NinePatch(buttonTexture.get(), 24, 24, 24, 24)
        private fun buttonPressedNinePatch() = NinePatch(buttonPressedTexture.get(), 24, 24, 24, 24)
    }
}
