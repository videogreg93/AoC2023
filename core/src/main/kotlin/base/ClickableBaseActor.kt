package gaia.base

import base.BaseActor
import com.badlogic.gdx.graphics.Texture

/**
 * For creating 'anonymous' clickable base actors
 */
open class ClickableBaseActor(texture: Texture?, var clickAction: () -> Boolean = DEFAULT_CALLBACK) :
    BaseActor(texture),
    Clickable {
    override var enabled: Boolean = true
    var rightclickAction: () -> Boolean = DEFAULT_CALLBACK
    override fun onClick(): Boolean = clickAction()
    open fun onRightClick(): Boolean = rightclickAction()
    override fun onRelease() {}

    companion object {
        private val DEFAULT_CALLBACK: () -> Boolean = { false }
    }
}
