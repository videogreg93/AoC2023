package screens.aoc.day1

import base.BaseActor
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.graphics.Texture
import com.odencave.assets.Assets
import gaia.managers.MegaManagers
import gaia.managers.assets.Asset
import gaia.managers.assets.AssetManager.Companion.get
import gaia.ui.generic.Label
import gaia.ui.utils.addForeverAction
import gaia.ui.utils.alignTopToTopOf
import gaia.utils.FloatLerpAction.Companion.createLerpAction

class Panel() : BaseActor(texture.get()) {
    private var displayedTotal: Int = 0
    var actualTotal: Int = 0
        set(value) {
            field = value
            addAction(createLerpAction(displayedTotal.toFloat(), value.toFloat()) { currentValue ->
                displayedTotal = currentValue.toInt()
            })
        }

    private val titleLabel = Label("Total", MegaManagers.fontManager.defaultFont).apply {
        addForeverAction {
            centerOn(this@Panel)
            alignTopToTopOf(this@Panel, -24f)
        }
    }

    private val totalLabel = Label("0", MegaManagers.fontManager.defaultFont).apply {
        addForeverAction {
            text = displayedTotal.toString()
            centerOn(this@Panel)
        }
    }

    init {
        children.addAll(listOf(titleLabel, totalLabel))
    }

    companion object {
        @Asset
        private val texture = AssetDescriptor(Assets.panel, Texture::class.java)
    }
}
