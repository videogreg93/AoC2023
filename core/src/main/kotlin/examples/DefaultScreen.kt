package examples

import base.BaseActor
import gaia.managers.MegaManagers
import gaia.managers.input.ActionListener
import screens.scene.SceneEditorScreen
import ui.BasicScreen

class DefaultScreen(val initialMembers: List<BaseActor>) : BasicScreen("Default") {

    init {
        crew.addMembers(initialMembers)
    }

    override fun onAction(action: ActionListener.InputAction): Boolean {
        when (action) {
            ActionListener.InputAction.QUIT -> MegaManagers.screenManager.returnToScreen(SceneEditorScreen::class.java)
            else -> return false
        }

        return true
    }
}
