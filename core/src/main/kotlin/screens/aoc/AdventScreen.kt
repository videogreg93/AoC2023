package screens.aoc

import com.badlogic.gdx.Gdx
import ui.BasicScreen

abstract class AdventScreen(day: String): BasicScreen(day) {

    val filename: String = "assets/$day.txt"

    protected fun getInput(): List<String> {
        val file = Gdx.files.local(filename)
        return file.file().readLines()
    }
}
