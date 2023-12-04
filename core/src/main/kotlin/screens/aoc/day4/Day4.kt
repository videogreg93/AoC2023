package screens.aoc.day4

import base.BaseActor
import screens.aoc.AdventScreen
import kotlin.math.pow

class Day4: AdventScreen("day4") {

    override val background: List<BaseActor> = emptyList()

    override fun isDone(): Boolean {
        return false
    }

    override fun firstShown() {
        super.firstShown()
        part2()
    }

    data class Card(val winningNumbers: List<Int>, val myNumbers: List<Int>, val copyPower: Int, var count: Long)

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
