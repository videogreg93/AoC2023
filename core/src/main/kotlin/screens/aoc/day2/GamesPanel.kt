package screens.aoc.day2

import Globals
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.odencave.assets.Assets
import gaia.base.BaseNinePatchActor
import gaia.base.Crew
import gaia.managers.MegaManagers
import gaia.managers.assets.Asset
import gaia.managers.assets.AssetManager.Companion.get
import gaia.ui.generic.Label
import gaia.ui.utils.*
import gaia.utils.green
import gaia.utils.red

class GamesPanel : BaseNinePatchActor(ninepatch) {
    var currentGame = 0
    private val gameLabels = ArrayList<Label>()

    init {
        width = 380f
        height = Globals.WORLD_HEIGHT * 0.8f
    }

    override fun onAddedToCrew(crew: Crew) {
        super.onAddedToCrew(crew)
        alignLeft(16f)
        alignTop(-20f)
    }

    fun newGame() {
        currentGame++
        val label = Label("Game ${currentGame}", MegaManagers.fontManager.defaultFont)
        label.centerOn(this)
        label.alignTopToTopOf(this, -10f)
        label.alignLeftToLeftOf(this, 20f + 107 * (currentGame / 35))
        label.y -= ((currentGame) % 35) * 20
        gameLabels.add(label)
        crew?.addMember(label)
    }

    fun updateLabel(isPossible: Boolean) {
        gameLabels.last().let {
            it.text = if (isPossible) it.text.green() else it.text.red()
        }
    }

    companion object {
        @Asset
        private val texture = AssetDescriptor(Assets.small_panel, Texture::class.java)

        val ninepatch by lazy { NinePatch(texture.get(), 14, 14, 14, 14) }
    }
}
