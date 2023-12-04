package screens.aoc

import gaia.base.BaseNinePatchActor
import gaia.managers.MegaManagers
import gaia.ui.generic.Label
import gaia.ui.utils.addForeverAction
import screens.aoc.day2.GamesPanel
import kotlin.math.max

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
            width = max(baseLabel.width * 1.2f, 150f)
            height = max(150f, baseLabel.height * 2)
        }
        baseLabel.addForeverAction {
            baseLabel.centerOn(this)
        }
        children.add(baseLabel)
    }
}
