package screens.scene

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.badlogic.gdx.utils.Align
import ktx.actors.onClick
import ui.Skins


class TabGroup(val stage: Stage, vararg tabButtons: TabButton) {
    private val skin = Skins.default

    val buttons = mutableMapOf<TextButton, TabButton>().apply {
        tabButtons.forEach {
            this[TextButton(it.name, skin, "toggle").apply {
                onClick {
                    isChecked = false
                    it.onClick.invoke()
                }
            }] = it
        }
    }

    init {

        val main = Table()
        main.setFillParent(true)

        // Create the tab buttons
        val group = HorizontalGroup()
        buttons.keys.forEach {
            group.addActor(it)
        }
        main.add(group)
        main.row()

        val tabs: ButtonGroup<TextButton> = ButtonGroup()
        tabs.setMinCheckCount(0)
        tabs.setMaxCheckCount(0)
        buttons.keys.forEach {
            tabs.add(it)
        }

        main.align(Align.top)

        stage.addActor(main)
    }

    data class TabButton(val name: String, val onClick: () -> Unit)
}
