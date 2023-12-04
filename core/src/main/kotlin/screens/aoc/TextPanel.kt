package screens.aoc

import gaia.base.BaseNinePatchActor
import gaia.managers.MegaManagers
import gaia.ui.generic.Label
import gaia.ui.utils.addForeverAction
import screens.aoc.day2.GamesPanel

class TextPanel(var baseText: String): BaseNinePatchActor(GamesPanel.ninepatch) {
    private val baseLabel = Label(baseText, MegaManagers.fontManager.largeFont).apply {
        this.addForeverAction {
            text = baseText
        }
    }

    init {
        width = 300f
        height = 150f
        addForeverAction {
            width = baseLabel.width * 1.2f
        }
        baseLabel.addForeverAction {
            baseLabel.centerOn(this)
        }
        children.add(baseLabel)
    }
}
