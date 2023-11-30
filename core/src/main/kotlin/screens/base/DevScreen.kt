package screens.base

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.kotcrab.vis.ui.util.ToastManager
import gaia.managers.context.MainContext
import gaia.managers.input.ActionListener
import ktx.app.KtxInputAdapter
import ui.BasicScreen

abstract class DevScreen(name: String) : BasicScreen(name), KtxInputAdapter {
    val stage: Stage by lazy { MainContext.inject() }

    val toastManager: ToastManager
        get() = ToastManager(stage)

    override fun show() {
        super.show()
        val inputProcessor = Gdx.input.inputProcessor
        val multiplexer = InputMultiplexer(this, inputProcessor)
        Gdx.input.inputProcessor = multiplexer
    }

    override fun hide() {
        super.hide()
        (Gdx.input.inputProcessor as? InputMultiplexer)?.removeProcessor(this)
    }

    override fun onAction(action: ActionListener.InputAction): Boolean {
        // DO nothing
        return false
    }

//    fun showToast(text: String) {
//        toastManager.show(text)
//        hudCrew.addMember(Toast(text))
//    }


    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        stage.viewport.update(width, height, true);
    }
}
