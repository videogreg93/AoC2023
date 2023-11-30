package com.odencave.i18n.gaia.base

import Globals
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import base.BaseActor
import gaia.ui.utils.withColor

class BackgroundGrid(
    gridTexture: Texture,
    var speedX: Int = 32,
    var speedY: Int = 0,
    val posX: Float = -Globals.WORLD_WIDTH,
    val posY: Float = -Globals.WORLD_HEIGHT

) : BaseActor() {

    private val textureRegion = TextureRegion(gridTexture.apply {
        setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat)
    }, (1920 * 2), (1080 * 3))

    init {
        drawIndex = 2000
    }

    var currentOffsetX = 0f
    var currentOffsetY = 0f

    override fun act(delta: Float) {
        super.act(delta)
        currentOffsetX += (delta * speedX)
        currentOffsetY += (delta * speedY)
        textureRegion.setRegion(
            currentOffsetX.toInt(),
            currentOffsetY.toInt(),
            (Globals.WORLD_WIDTH * 1.5).toInt(),
            (Globals.WORLD_HEIGHT * 1.5).toInt()
        )
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.withColor(color) {
            it.draw(textureRegion, posX, posY)
        }
    }
}
