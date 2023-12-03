package screens.aoc.day2

import gaia.base.BaseNinePatchActor
import gaia.managers.MegaManagers
import gaia.ui.generic.Label
import gaia.ui.utils.addForeverAction

class ColorLabel(var baseText: String, var count: String = "?"): BaseNinePatchActor(GamesPanel.ninepatch) {
    private val baseLabel = Label(baseText, MegaManagers.fontManager.largeFont).apply {
        this.addForeverAction {
            text = baseText + count
        }
    }

    init {
        width = 300f
        height = 150f
        baseLabel.addForeverAction {
            baseLabel.centerOn(this)
        }
        children.add(baseLabel)
    }
}
