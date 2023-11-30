@file:OptIn(ExperimentalStdlibApi::class)

package screens.scene

import Globals
import base.BaseActor
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Json
import com.kotcrab.vis.ui.widget.Menu
import com.kotcrab.vis.ui.widget.MenuBar
import com.kotcrab.vis.ui.widget.MenuItem
import com.kotcrab.vis.ui.widget.VisTable
import dev.files.chooseFile
import examples.DefaultScreen
import gaia.managers.MegaManagers
import gaia.managers.context.MainContext
import gaia.managers.input.ActionListener
import ktx.actors.onClick
import screens.base.DevScreen


class SceneEditorScreen : DevScreen("") {
    var file = Gdx.files.external("Protogaia/scenes/test.gaia")
    val sceneTree = SceneViewer(ArrayList(listOf())).apply {
        height = 1080f
        align(Align.left)
    }
    val actorDragHandler by lazy { ActorDragHandler() }

    override fun firstShown() {
        super.firstShown()
        stage.addActor(sceneTree)
        hudCrew.addMember(actorDragHandler)
        val menuBar = MenuBar()
        val root = VisTable().apply {
            setFillParent(true)
        }

        stage.addActor(root)
        root.add(menuBar.table).expandX().fillX().expandY().align(Align.top).row()

        createMenu(menuBar)
        TabGroup(
            stage,
            TabGroup.TabButton("Play", ::onPlay)
        )
    }

    private fun createMenu(menuBar: MenuBar) {
        val fileMenu = Menu("File").apply {
            addItem(MenuItem("New Scene...").apply {
                onClick {
                    chooseFile {
                        file = FileHandle(it)
                        loadScene()
                    }
                }
            })
        }
        val toolsMenu = Menu("Tools").apply {
        }
        val windowMenu = Menu("Window").apply {
            addItem(MenuItem("Scene Viewer"))
        }
        val helpMenu = Menu("Help")

        menuBar.addMenu(fileMenu)
        menuBar.addMenu(toolsMenu)
        menuBar.addMenu(windowMenu)
        menuBar.addMenu(helpMenu)
    }

    fun onPlay() {
        MegaManagers.screenManager.changeScreen(DefaultScreen(loadSceneForPlay().members))
    }

    fun onAddActor(actor: BaseActor) {
        crew.addMember(actor)
        sceneTree.addMember(actor)
    }

    fun onRemoveActor(actor: BaseActor) {
        actor.removeFromCrew()
        sceneTree.removeMember(actor)
    }

    fun removeAllSceneMembers() {
        sceneTree.members.forEach { it.removeFromCrew() }
        sceneTree.removeAllMembers()
    }

    fun save() {
        val json = MainContext.inject<Json>()
        val serialized = json.toJson(SceneModel(sceneTree.members))
        file.writeString(serialized, false)
        println(file.file().absolutePath)
    }

    fun loadScene() {
        val json = MainContext.inject<Json>()
        val sceneModel: SceneModel = json.fromJson(SceneModel::class.java, file.readString()) ?: SceneModel()
        removeAllSceneMembers()
        sceneModel.members.forEach {
            if (it.texturePath.isNotBlank()) {
                it.setTextureFromFile(it.texturePath)
            }
            onAddActor(it)
        }
    }

    fun loadSceneForPlay(): SceneModel {
        val json = MainContext.inject<Json>()
        val serialized = json.toJson(SceneModel(sceneTree.members))
        val sceneModel = json.fromJson(SceneModel::class.java, serialized)
        sceneModel.members.forEach {
            if (it.texturePath.isNotBlank()) {
                it.setTextureFromFile(it.texturePath)
            }
        }
        return sceneModel
    }

    override fun keyDown(keycode: Int): Boolean {
        when (keycode) {
            Input.Keys.S -> {
                if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
                    save()
                }
            }

            Input.Keys.O -> {
                if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
                    loadScene()
                }
            }

            Input.Keys.BACKSPACE -> {
                sceneTree.memberTree.selection.filterIsInstance<SceneViewer.SceneViewNode>().firstOrNull()?.let {
                    onRemoveActor(it.sceneActor)
                }
            }

            else -> return false
        }
        return true
    }

    @OptIn(ExperimentalStdlibApi::class)
    override fun onAction(action: ActionListener.InputAction): Boolean {
        when (action) {
            ActionListener.InputAction.CLICK -> {
                getMembersUnderMouse().firstOrNull()?.let {
                    stage.actors.filterIsInstance<SceneMemberDetails>().forEach {
                        it.remove()
                    }
                    stage.addActor(SceneMemberDetails(it))
                }
                println(action)
            }

            ActionListener.InputAction.LONG_PRESS -> {
                getMembersUnderMouse().firstOrNull()?.let {
                    actorDragHandler.grabActor(it, getMousePosition())
                }
            }

            else -> return false
        }
        return true
    }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
        if (button != 0) {
            stage.addActor(ScenePopupMenu(::onAddActor).apply {
                val pos = getMousePosition()
                this.setPosition(pos.x + Globals.WORLD_WIDTH / 2f, pos.y + Globals.WORLD_HEIGHT / 2f)
            })
            return true
        }
        return false
    }

    override fun onActionReleased(action: ActionListener.InputAction): Boolean {
        when (action) {
            ActionListener.InputAction.CLICK -> {
                actorDragHandler.letActorGo()
            }

            else -> return false
        }
        return true
    }

}
