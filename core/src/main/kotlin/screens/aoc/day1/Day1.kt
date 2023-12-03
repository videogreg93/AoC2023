package screens.aoc.day1

import Globals
import base.BaseActor
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.odencave.assets.Assets
import com.odencave.i18n.gaia.base.BackgroundGrid
import gaia.managers.MegaManagers
import gaia.managers.assets.Asset
import gaia.managers.assets.AssetManager.Companion.get
import gaia.ui.utils.alignRight
import gaia.ui.utils.alignTop
import gaia.ui.utils.setOffscreenLeft
import gaia.utils.wrapped
import screens.aoc.AdventScreen

class Day1 : AdventScreen("day1") {

    override val background: List<BaseActor> = emptyList()
    private val house1 by lazy {
        BaseActor(houseTexture.get()).apply {
            x = -400f
            alignTop(-300f)
            drawIndex = -1000
        }
    }

    private val house2 by lazy {
        BaseActor(houseTexture.get()).apply {
            x = 200f
            alignTop(-300f)
            drawIndex = -1000
        }
    }

    private val panel by lazy { Panel().apply {
        alignRight(-100f)
        alignTop(-100f)
    } }

    val buttons by lazy {
        ArrayList(getInput().map {
            LineButton(it).apply {
                xSpeed = 300f
                addAction(
                    Actions.sequence(
                        Actions.delay(2.75f, Actions.run {
                            label.text = it.cleaned().wrapped(MegaManagers.fontManager.smallerFont, 125)
                        }),
                        Actions.delay(1.75f, Actions.run {
                            val temp = label.text.filter { it.isDigit() }
                            label.text = "${temp.first()}${temp.last()}"
                        }),
                        Actions.delay(2.5f, Actions.run {
                            panel.actualTotal += label.text.toInt()
                            this.removeFromCrew()
                        })
                    )
                )
            }
        })
    }

    override fun firstShown() {
        super.firstShown()
        backgroundCrew.addMember(BaseActor(backgroundTexture.get()).apply {
            center()
        })
        MegaManagers.screenManager.addGlobalAction(
            Actions.repeat(
                buttons.size,
                Actions.delay(2f, Actions.run {
                    val b = buttons.removeFirst()
                    b.setOffscreenLeft()
                    b.alignTop()
                    b.y += (b.height - 575f)
                    crew.addMember(b)
                })
            )
        )
        crew.addMembers(house1, house2, panel)
    }

    private val words = listOf(
        "one",
        "two",
        "three",
        "four",
        "five",
        "six",
        "seven",
        "eight",
        "nine",
    )

    private val mapping = mapOf(
        "one" to "o1e",
        "two" to "t2o",
        "three" to "t3ree",
        "four" to "f4our",
        "five" to "f5ve",
        "six" to "s6x",
        "seven" to "s7ven",
        "eight" to "e8ght",
        "nine" to "n9e",
    )

    fun String.cleaned(): String {
        var t = this
        words.forEach {
            t = t.replace(it, mapping.getValue(it))
        }
        return t
    }

    override fun render(delta: Float) {
        super.render(delta)
    }

    private fun part2() {
        val answer = getInput().sumOf { line ->
            var temp = line.cleaned().filter { it.isDigit() }
            "${temp.first()}${temp.last()}".toInt()
        }
        println(answer)
    }

    private fun part1() {
        val answer = getInput().sumOf {
            val temp = it.filter { it.isDigit() }
            "${temp.first()}${temp.last()}".toInt()
        }
        println(answer)
    }

    override fun isDone(): Boolean {
        return buttons.isEmpty()
    }

    companion object {
        @Asset
        private val backgroundTexture = AssetDescriptor(Assets.Backgrounds.day1, Texture::class.java)

        @Asset
        private val houseTexture = AssetDescriptor(Assets.house1, Texture::class.java)
    }
}
