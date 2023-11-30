package gaia.base

import base.BaseActor
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.NinePatch

open class BaseNinePatchActor(var ninePatch: NinePatch) : BaseActor() {

    override fun draw(batch: Batch, parentAlpha: Float) {
        ninePatch.draw(batch, x, y, width, height)
    }
}
