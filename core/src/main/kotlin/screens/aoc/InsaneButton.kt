package screens.aoc

import Globals
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.graphics.Texture
import com.odencave.assets.Assets
import gaia.base.ClickableBaseActor
import gaia.managers.MegaManagers
import gaia.managers.assets.Asset
import gaia.managers.assets.AssetManager.Companion.get

class InsaneButton: ClickableBaseActor(texture.get()) {

    var isPressed = false

    override fun onClick(): Boolean {
        isPressed = true
        sprite?.texture = pressed.get()
        Globals.gameSpeed = 3000f
        return true
    }

    override fun onRelease() {
        isPressed = false
        sprite?.texture = texture.get()

    }

    override fun act(delta: Float) {
        super.act(delta)
    }

    companion object {
        @Asset
        private val texture = AssetDescriptor(Assets.Buttons.insane, Texture::class.java)

        @Asset
        private val pressed = AssetDescriptor(Assets.Buttons.insane_pressed, Texture::class.java)
    }
}
