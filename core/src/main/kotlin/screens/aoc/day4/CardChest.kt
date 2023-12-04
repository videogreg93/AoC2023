package screens.aoc.day4

import base.BaseActor
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.odencave.assets.Assets
import gaia.managers.assets.Asset
import gaia.managers.assets.AssetManager.Companion.get
import gaia.utils.createAnimation

class CardChest(val cardName: String, val card: Day4.Card): BaseActor(idleTexture.get()) {

    init {
        hangOnLastFrame = true
    }

    fun openChest() {
        currentAnimationTime = 0f
        addAction(
            Actions.sequence(
                Actions.moveBy(0f, distance, 0.5f, Interpolation.fastSlow),
                Actions.run {
                    currentAnimation = openAnimation
                }
            )
        )
    }

    fun closeChest() {
        addAction(
            Actions.sequence(
                Actions.moveBy(0f, -distance, 0.5f, Interpolation.fastSlow),
                Actions.run {
                    currentAnimation = null
                    currentAnimationTime = 0f
                }
            )
        )
    }

    companion object {
        private val distance = 50f

        @Asset
        private val idleTexture = AssetDescriptor(Assets.chest_closed, Texture::class.java)

        @Asset
        private val openTexture = AssetDescriptor(Assets.chest_open_animation, Texture::class.java)

        private val openAnimation by lazy { createAnimation(openTexture.get(), 1, 4, 1/60f, false) }
    }
}
