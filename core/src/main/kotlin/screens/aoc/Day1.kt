package screens.aoc

class Day1 : AdventScreen("day1") {

    override fun firstShown() {
        super.firstShown()
        part2()
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

    private fun part2() {
        val answer = getInput().sumOf { line ->
            var temp = line.cleaned().filter { it.isDigit() }
            "${temp.first()}${temp.last()}".toInt()
//            "$firstChar$lastChar".toInt()
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
        return false
    }
}
