package dev

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.kotcrab.vis.ui.VisUI
import com.kotcrab.vis.ui.widget.VisTable
import gaia.managers.MegaManagers
import gaia.managers.context.MainContext
import ktx.app.KtxInputAdapter
import screens.base.DevScreen

class DevTools : MegaManagers.Manager, KtxInputAdapter {
    private lateinit var batch: SpriteBatch
    lateinit var stage: Stage

    val isShown
        get() = (MegaManagers.screenManager.getCurrentScreen() is DevScreen)
//    private var screenshotSystem: DebugScreenRecordingSystem? = null

    /**
     * Make sure to call this AFTER the inputmanager is initialized
     */
    override fun init() {
        batch = MegaManagers.currentContext.inject()
        stage = MainContext.inject()

        val inputProcessor = Gdx.input.inputProcessor
        val multiplexer = InputMultiplexer(stage, this, inputProcessor)
        Gdx.input.inputProcessor = multiplexer
//        val root = VisTable().apply {
//            setFillParent(true)
//        }
//
//        stage.addActor(root)

//        val menuBar = MenuBar()
//        root.add(menuBar.table).expandX().fillX().expandY().align(Align.top).row()
//
//        createMenu(menuBar)
//
//        TabGroup(
//            stage, ::onTabChange,
//            TabGroup.TabButton("Setups", SetupEditorScreen::class.java),
//            TabGroup.TabButton("Encounters", EncounterEditorScreen::class.java),
//            TabGroup.TabButton("Cards", AllCardsScreen::class.java),
//            TabGroup.TabButton("Main Menu", MainMenu::class.java)
//        )
    }

    fun render() {
        if (!isShown) return
        stage.act(Gdx.graphics.getDeltaTime())
        stage.draw()
    }
}
