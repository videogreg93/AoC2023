package screens.aoc

import base.BaseActor
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.odencave.assets.Assets
import gaia.base.Crew
import gaia.managers.MegaManagers
import gaia.managers.assets.Asset
import gaia.managers.assets.AssetManager.Companion.get
import gaia.ui.generic.Label
import gaia.ui.utils.centerVerticallyOn
import kotlin.math.roundToInt

class SpeedSlider(var text: String = ""): BaseActor(barTexture.get()) {

    val button = BaseActor(buttonTexture.get())
    private val label = Label(text, MegaManagers.fontManager.defaultFont)

    var value: Float = 0f
        private set(value) {
            field = value.coerceIn(0f, 1f)
            onValueChange?.invoke(field)
        }

    private var followingMouse = false
    private var mouseOrigin = Vector2(0f, 0f)
    var onValueChange: ((Float) -> Unit)? = null

    init {
        button.addAction(Actions.forever(Actions.run {
            button.centerVerticallyOn(this)
            value = (button.x - x)/(width - button.width)
        }))
        label.addAction(Actions.forever(Actions.run {
            label.centerOn(this)
            label.y += 15f
            val goodValue = ((value * 10) * 10).toInt() / 10f
            label.text = "$text: ${(goodValue)}"
        }))
        addAction(Actions.forever(Actions.run {
            if (followingMouse) {
                MegaManagers.screenManager.getCurrentScreen()?.getMousePosition()?.let { mousePos ->
                    button.x = (mousePos.x - button.width/2).coerceIn(this.x, this.x + width - button.width)
                }
            }
        }))
    }

    override fun onAddedToCrew(crew: Crew) {
        super.onAddedToCrew(crew)
        crew.addMembers(label, button)
    }

    override fun onRemovedFromCrew(crew: Crew) {
        crew.removeMembers(button, label)
        super.onRemovedFromCrew(crew)
    }

    fun forceValue(v: Float) {
        button.x = x + (v * (width - button.width))
    }

    fun startFollowingMouse(originX: Float, originY: Float) {
        followingMouse = true
        mouseOrigin.set(originX, originY)
    }

    fun stopFollowingMouse() {
        followingMouse = false
    }

    companion object {
        @Asset
        private val barTexture = AssetDescriptor(Assets.slider_bar, Texture::class.java)

        @Asset
        private val buttonTexture = AssetDescriptor(Assets.slider_button, Texture::class.java)
    }
}
