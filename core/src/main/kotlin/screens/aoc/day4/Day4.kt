package screens.aoc.day4

import Globals
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import gaia.ui.utils.alignBottom
import screens.aoc.AdventScreen
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
        val startingX = -Globals.WORLD_WIDTH/2f
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
