package screens.scene

import base.BaseActor
import com.kotcrab.vis.ui.widget.MenuItem
import com.kotcrab.vis.ui.widget.PopupMenu
import gaia.managers.context.MainContext
import ktx.actors.onClick
import org.reflections.Reflections
import screens.base.EditorEntity

class AddMemberPopupMenu(callback: (BaseActor) -> Unit) : PopupMenu() {

    init {
        val reflection = MainContext.inject<Reflections>()
        val entities = reflection.getTypesAnnotatedWith(EditorEntity::class.java).filter {
            it.constructors.any {
                it.parameterCount == 0
            }
        }
        entities.forEach {
            val item = MenuItem(it.simpleName).apply {
                onClick {
                    val actor = it.getConstructor().newInstance() as BaseActor
                    callback(actor)
                }
            }
            addItem(item)
        }
    }
}
