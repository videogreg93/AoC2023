package screens.aoc

import Globals
import base.BaseActor
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.odencave.assets.Assets
import gaia.managers.MegaManagers
import gaia.managers.assets.Asset
import gaia.managers.assets.AssetManager.Companion.get
import gaia.managers.fonts.FontManager
import gaia.managers.input.ActionListener
import gaia.ui.generic.Label
import gaia.ui.utils.*
import ui.BasicScreen

class Day1: AdventScreen("day1") {

    class Elf: BaseActor(texture.get()) {
        var weight = 0
        private val label = Label("0", MegaManagers.fontManager.defaultFont).apply {
            center()
            alignTop(-50f)
            addForeverAction {
                text = weight.toString()
                centerOn(this@Elf)
                alignTopToBottomOf(this@Elf, -20f)
            }
        }

        init {
            children.add(label)
        }

        companion object {
            @Asset
            private val texture = AssetDescriptor(Assets.elf, Texture::class.java)
        }
    }

    val elves = ArrayList<Elf>()
    var currentElf: Elf? = null
    var currentMax = 0
    val label = Label("0", MegaManagers.fontManager.defaultFont).apply {
        center()
        alignTop(-50f)
        addForeverAction {
            text = currentMax.toString()
        }
    }

    override fun firstShown() {
        super.firstShown()
        var currentElf = Elf()
        getInput().forEach {
            val weight = it.toIntOrNull()
            if (weight != null) {
                currentElf.weight += weight
            } else {
                elves.add(currentElf)
                currentElf = Elf()
            }
        }
        addNewElf()
        //println(elves.maxByOrNull { it.weight }?.weight!!)
    }

    fun addNewElf() {
        val elf = elves.removeFirst()
        elf.setPosition(-Globals.WORLD_WIDTH/2f, 0f)
        val sequence = Actions.sequence(
            Actions.moveBy(Globals.WORLD_WIDTH/2f, 0f, 2f),
            Actions.run {
                val newSequence = Actions.sequence()
                if (elf.weight > currentMax) {
                    val dest = elf.calculatePositionFor {
                        alignTopToBottomOf(label, -50f)
                    }
                    currentElf?.let {
                        it.addAction(
                            Actions.sequence(
                                Actions.delay(0.5f, Actions.moveBy(0f, 150f, 1f)),
                                Actions.run {
                                    it.removeFromCrew()
                                }
                            )
                        )
                    }
                    newSequence.addAction(
                        Actions.run {
                            currentMax = elf.weight
                        }
                    )
                    newSequence.addAction(Actions.moveTo(dest.x, dest.y, 2f))
                    newSequence.addAction(
                        Actions.run {
                            currentElf = elf
                            if (elves.isNotEmpty()) {
                                addNewElf()
                            }
                        }
                    )
                } else {
                    newSequence.addAction(
                        Actions.moveBy(Globals.WORLD_WIDTH/2f + 20, 0f, 2f)
                    )
                    newSequence.addAction(
                        Actions.run {
                            if (elves.isNotEmpty()) {
                                addNewElf()
                            }
                        }
                    )
                }
                elf.addAction(newSequence)
            }
        )
        elf.addAction(sequence)
        crew.addMember(elf)
    }

    override fun onAction(action: ActionListener.InputAction): Boolean {
        return true
    }
}
