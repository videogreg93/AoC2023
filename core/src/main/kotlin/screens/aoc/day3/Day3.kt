package screens.aoc.day3

import base.BaseActor
import screens.aoc.AdventScreen
import java.util.regex.Matcher
import java.util.regex.Pattern

class Day3 : AdventScreen("day3") {

    override val background: List<BaseActor> = emptyList()

    override fun firstShown() {
        super.firstShown()
        //println(Day03().solvePart1(getInput()))
        part2()
    }

    private fun part1() {
        val numberArray = ArrayList<ArrayList<Pair<Int, IntRange>>>()
        val symbolArray = ArrayList<ArrayList<Pair<String, Int>>>()
        val integerPattern: Pattern = Pattern.compile("\\d+")
        var symbolPattern: Pattern = Pattern.compile("[^A-Za-z0-9.]")
        getInput().forEachIndexed { index, line ->
            // find numbers
            numberArray.add(ArrayList())
            val matcher: Matcher = integerPattern.matcher(line)
            while (matcher.find()) {
                val result = matcher.group()
                val startIndex = (matcher.start() - 1).coerceAtLeast(0)
                val endIndex = matcher.end()
                numberArray[index].add(result.toInt() to startIndex..endIndex)
            }
            // find symbols
            symbolArray.add(ArrayList())
            val symbolMatcher = symbolPattern.matcher(line)
            while (symbolMatcher.find()) {
                val result = symbolMatcher.group()
                val startIndex = symbolMatcher.start()
                symbolArray[index].add(result to startIndex)
            }
        }
        // find part numbers
        val parts = ArrayList<Int>()
        numberArray.forEachIndexed { index, items ->
            val lineAbove = symbolArray.getOrNull(index - 1).orEmpty()
            val currentLine = symbolArray.get(index)
            val lineBelow = symbolArray.getOrNull(index + 1).orEmpty()
            val allSymbols = lineAbove + currentLine + lineBelow
            val actualParts = items.filter { item ->
                allSymbols.any {
                    item.second.contains(it.second)
                }
            }
            parts.addAll(actualParts.map { it.first })
        }

        println(parts.sum())
    }

    private fun part2() {
        val numberArray = ArrayList<ArrayList<Pair<Int, IntRange>>>()
        val symbolArray = ArrayList<ArrayList<Pair<String, Int>>>()
        val integerPattern: Pattern = Pattern.compile("\\d+")
        var symbolPattern: Pattern = Pattern.compile("[*]")
        getInput().forEachIndexed { index, line ->
            // find numbers
            numberArray.add(ArrayList())
            val matcher: Matcher = integerPattern.matcher(line)
            while (matcher.find()) {
                val result = matcher.group()
                val startIndex = (matcher.start() - 1).coerceAtLeast(0)
                val endIndex = matcher.end()
                numberArray[index].add(result.toInt() to startIndex..endIndex)
            }
            // find symbols
            symbolArray.add(ArrayList())
            val symbolMatcher = symbolPattern.matcher(line)
            while (symbolMatcher.find()) {
                val result = symbolMatcher.group()
                val startIndex = symbolMatcher.start()
                symbolArray[index].add(result to startIndex)
            }
        }
        // find part numbers
        var force = 0
        symbolArray.forEachIndexed { index, items ->
            val lineAbove = numberArray.getOrNull(index - 1).orEmpty()
            val currentLine = numberArray.get(index)
            val lineBelow = numberArray.getOrNull(index + 1).orEmpty()
            val allLines = lineAbove + currentLine + lineBelow
            items.forEach {  gear ->
                val adjacentNumbers = allLines.filter {
                    it.second.contains(gear.second)
                }
                if (adjacentNumbers.size == 2) {
                    force += (adjacentNumbers[0].first * adjacentNumbers[1].first)
                }
            }
        }
        println(force)
    }

    override fun isDone(): Boolean {
        return false
    }
}
