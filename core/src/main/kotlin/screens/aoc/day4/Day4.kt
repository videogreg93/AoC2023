package screens.aoc.day4

import Globals
import actions.OnEventAction
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import gaia.managers.MegaManagers
import gaia.ui.utils.*
import gaia.utils.green
import gaia.utils.wrapped
import screens.aoc.AdventScreen
import screens.aoc.TextPanel
import kotlin.math.pow

class Day4 : AdventScreen("day4") {
    var finished = false

    override fun isDone(): Boolean {
        return finished
    }

    override fun firstShown() {
        super.firstShown()
        part2()
    }

    data class Card(val winningNumbers: List<Int>, val myNumbers: List<Int>, val copyPower: Int, var count: Long)

    fun handleChest(cardArray: List<CardChest>, index: Int) {
        val slideDelay = 1f
        val cardChest = cardArray.getOrNull(index)
        if (cardChest == null) {
            finished = true
            return
        }
        val cardSequence = Actions.sequence()
        cardSequence.run {
            addAction(cardChest.openChestAction())
            addAction(Actions.delay(2f))
            addAction(Actions.run {
                handleCard(cardChest.card, cardArray, index)
            })
            addAction(OnEventAction(NextChestAction().identifier))
            addAction(cardChest.closeChestAction())
            addAction(Actions.delay(2f))
            addAction(
                Actions.run {
                    crew.members.filterIsInstance<CardChest>().forEach {
                        it.addAction(Actions.moveBy(-60f, 0f, slideDelay, Interpolation.fastSlow))
                    }
                }
            )
            addAction(Actions.delay(slideDelay))
            addAction(Actions.run {
                handleChest(cardArray, index + 1)
            })
        }
        cardChest.addAction(cardSequence)
    }

    private fun handleCard(card: Card, cardArray: List<CardChest>, index: Int) {
        val sb = StringBuilder()
        card.winningNumbers.forEach { sb.append("$it ") }
        sb.appendLine()
        sb.appendLine()
        card.myNumbers.forEach { sb.append("$it ") }
        val firstText = sb.toString().wrapped(MegaManagers.fontManager.largeFont, 1400, " ")
        sb.clear()
        card.winningNumbers.forEach {
            if (card.myNumbers.contains(it)) {
                sb.append("${it.toString().green()} ")
            } else {
                sb.append("$it ")
            }
        }
        sb.appendLine()
        sb.appendLine()
        card.myNumbers.forEach {
            if (card.winningNumbers.contains(it)) {
                sb.append("${it.toString().green()} ")
            } else {
                sb.append("$it ")
            }
        }
        val finalText = sb.toString().wrapped(MegaManagers.fontManager.largeFont, 1400, " ")
        val cardPanel = TextPanel(firstText).apply {
            x = -700f
            y = -100f
            val s = Actions.sequence(
                Actions.delay(2f),
                Actions.run {
                    this.baseText = finalText
                },
                Actions.delay(2f),
            )
            repeat(card.copyPower) {
                val chest = cardArray.get(index + it + 1)
                chest.addAction(
                    Actions.delay(4f + (0.2f * it), Actions.sequence(chest.openChestAction(), chest.closeChestAction()))
                )
            }
            s.addAction(Actions.delay(2f + (0.2f * card.copyPower)))
            s.addAction(
                Actions.run {
                    MegaManagers.eventManager.sendEvent(NextChestAction())
                }
            )
            s.addAction(
                Actions.run {
                    this@Day4.crew.members.filterIsInstance<TextPanel>().forEach { it.removeFromCrew() }
                }
            )
            addAction(s)
        }
        val countPanel = TextPanel("${card.count.toInt()}x").apply {
            addForeverAction {
                centerOn(cardPanel)
                alignRightToLeftOf(cardPanel, -20f)
            }
        }
        val titleSb = StringBuilder()
        titleSb.appendLine("Winning Numbers: ${card.copyPower}")
        titleSb.appendLine("Copies: ${card.count}").appendLine()
        titleSb.append("Adding " + "${card.count}".green() + " copies of the next " + "${card.copyPower}".green() + " cards.")
        val titlePanel = TextPanel(titleSb.toString()).apply {
            alignLeft(50f)
            alignTop(-200f)
        }
        crew.addMembers(cardPanel, titlePanel)
    }

    private fun part2() {
        val cardArray = getInput().map { line ->
            val card = line.split(": ")[1].split(" | ")
            val winningNumbers = card.first().split(" ").mapNotNull { it.toIntOrNull() }
            val myNumbers = card.last().split(" ").mapNotNull { it.toIntOrNull() }
            val winningCount = myNumbers.count { winningNumbers.contains(it) }
            Card(winningNumbers, myNumbers, winningCount, 1)
        }
        cardArray.forEachIndexed { index, card ->
            repeat(card.copyPower) {
                cardArray[index + (it + 1)].count += card.count
            }
        }
        val startingX = -Globals.WORLD_WIDTH / 2f
        val chestArray = cardArray.mapIndexed { index, card ->
            CardChest("$index", card).apply {
                alignBottom(300f)
                x = startingX + index * 60f
                this@Day4.crew.addMember(this)
            }
        }
        handleChest(chestArray, 0)


        val answer = cardArray.sumOf { it.count }
        println(answer)
    }

    private fun part1() {
        val answer = getInput().sumOf { line ->
            val card = line.split(": ")[1].split(" | ")
            val winningNumbers = card.first().split(" ").mapNotNull { it.toIntOrNull() }
            val myNumbers = card.last().split(" ").mapNotNull { it.toIntOrNull() }
            val winningCount = myNumbers.count { winningNumbers.contains(it) }
            if (winningCount == 0) {
                0
            } else {
                2.toDouble().pow((winningCount - 1).toDouble()).toInt()
            }
        }
        println(answer)
    }
}
