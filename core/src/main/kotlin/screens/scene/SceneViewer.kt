package screens.scene

import base.BaseActor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Tree
import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTree
import com.kotcrab.vis.ui.widget.VisWindow
import ktx.actors.onClick
import ktx.collections.toGdxArray

@OptIn(ExperimentalStdlibApi::class)
class SceneViewer(val members: ArrayList<BaseActor> = ArrayList()) : VisWindow(title) {
    val root = VisTable().apply {
        setFillParent(true)
    }
    val memberTree = VisTree()
    private val memberNodes = ArrayList<SceneViewNode>(emptyList())

    init {
        isResizable = true
        setSize(200f, 1080f)
        val dummyNode = object : Tree.Node<SceneViewNode, BaseActor, VisLabel>(VisLabel("")){}
        dummyNode.isSelectable = false
        memberTree.add(dummyNode)
        members.forEach { member ->
            addMemberToSceneTree(member)
        }
        root.add(memberTree)
        add(root)
        root.y -= 10f
    }

    private fun addMemberToSceneTree(member: BaseActor) {
        // TODO include children of children as well
        val node = SceneViewNode(VisLabel(member.toString()), member)
        node.actor.onClick {
            showDetails(node)
        }
        node.addAll(member.children.map {
            SceneViewNode(VisLabel(it.toString()), it).apply {
                actor.onClick {
                    stage.addActor(SceneMemberDetails(sceneActor).apply {
                        align(Align.right)
                    })
                }
            }
        }.toTypedArray().toGdxArray())
        memberTree.add(node)
        memberNodes.add(node)
    }

    private fun showDetails(node: SceneViewNode) {
        stage.actors.filterIsInstance<SceneMemberDetails>().forEach {
            it.remove()
        }
        stage.addActor(SceneMemberDetails(node.sceneActor).apply {
            align(Align.right)
        })
    }

    fun addMember(actor: BaseActor) {
        members.add(actor)
        addMemberToSceneTree(actor)
    }

    fun removeMember(actor: BaseActor) {
        members.remove(actor)
        val nodeToRemove = memberNodes.first {
            it.sceneActor == actor
        }
        memberTree.remove(nodeToRemove)
        memberNodes.remove(nodeToRemove)
        println("REMOVING")
    }

    fun removeAllMembers() {
        ArrayList(members).forEach {
            removeMember(it)
        }
    }

    companion object {
        val title = "Scene Viewer"
    }

    class SceneViewNode(label: VisLabel, val sceneActor: BaseActor) :
        Tree.Node<SceneViewNode, BaseActor, VisLabel>(label) {
    }
}
