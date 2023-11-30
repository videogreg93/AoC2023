package screens.scene

import base.BaseActor
import com.kotcrab.vis.ui.widget.MenuItem
import com.kotcrab.vis.ui.widget.PopupMenu

class ScenePopupMenu(callback: (BaseActor) -> Unit) : PopupMenu() {

    val newMenuItem = MenuItem("New...").apply {
        subMenu = AddMemberPopupMenu(callback)
    }

    init {
        addItem(newMenuItem)
    }

}
