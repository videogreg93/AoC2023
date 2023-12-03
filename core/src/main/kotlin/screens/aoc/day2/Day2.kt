package screens.aoc.day2

import Globals
import base.BaseActor
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.odencave.assets.Assets
import gaia.base.BaseNinePatchActor
import gaia.managers.MegaManagers
import gaia.managers.assets.Asset
import gaia.managers.assets.AssetManager.Companion.get
import gaia.ui.generic.Label
import gaia.ui.utils.alignBottom
import screens.aoc.AdventScreen

class Day2 : AdventScreen("day2") {

    class Game(val id: Int, val games: List<String>)
    class CoolGame(val id: Int, val throws: List<Throw>) {
        class Throw(val redCount: Int, val blueCount: Int, val greenCount: Int)
    }

    override val background: List<BaseActor> by lazy { listOf(BaseActor(backgroundTexture.get())) }

    override fun firstShown() {
        super.firstShown()
        visuals()
    }

    var isfinished = false

    var presents = arrayListOf<BaseActor>()
    val allGamesPanel by lazy { GamesPanel() }

    private fun visuals() {
        val games = getInput().map { line ->
            val gameId = line.split(":").first().split(" ").last().toInt()
            val games = line.split(":").last().drop(1).split("; ")
            val throws = games.map {
                val t = it.split(", ").groupBy { it.split(" ").last() }
                CoolGame.Throw(
                    redCount = t.get("red")?.sumOf { it.split(" ").first().toInt() } ?: 0,
                    blueCount = t.get("blue")?.sumOf { it.split(" ").first().toInt() } ?: 0,
                    greenCount = t.get("green")?.sumOf { it.split(" ").first().toInt() } ?: 0,
                )
            }
            CoolGame(gameId, throws)
        }
        val redLabel = Label("Red: 12", MegaManagers.fontManager.largeFont).apply {
            x = -200f
            alignBottom(100f)
        }
        val greenLabel = Label("Green: 13", MegaManagers.fontManager.largeFont).apply {
            x = 0f
            alignBottom(100f)
        }
        val blueLabel = Label("Blue: 14", MegaManagers.fontManager.largeFont).apply {
            x = 200f
            alignBottom(100f)
        }
        val bottomPanel = BaseNinePatchActor(GamesPanel.ninepatch).apply {
            width = 700f
            height = 150f
            center()
            x += 50
            alignBottom(25f)
        }
        hudCrew.addMembers(allGamesPanel, bottomPanel, redLabel, greenLabel, blueLabel)
        allGamesPanel.newGame()
        val throws = games.first().throws
        handleThrow(throws, 0, games, 0)

    }

    fun handleThrow(throws: List<CoolGame.Throw>, throwIndex: Int, games: List<CoolGame>, gamesIndex: Int) {
        var thrw = throws.getOrNull(throwIndex)
        if (thrw != null) {
            val waitTime = (maxOf(thrw.redCount, thrw.greenCount, thrw.blueCount) * 0.5f) + 1f
            repeat(thrw.redCount) {
                addPresent(redGiftTexture.get(), -500, it)
            }
            repeat(thrw.greenCount) {
                addPresent(greenGiftTexture.get(), 0, it)
            }
            repeat(thrw.blueCount) {
                addPresent(blueGiftTexture.get(), 550, it)
            }
            MegaManagers.screenManager.addGlobalAction(Actions.delay(waitTime, Actions.run {
                presents.forEach {
                    it.removeFromCrew()
                }
                if (thrw.redCount > 12 || thrw.greenCount > 13 || thrw.blueCount > 14) {
                    // game invalid
                    allGamesPanel.updateLabel(false)
                    startNextGame(gamesIndex, games)
                } else {
                    handleThrow(throws, throwIndex + 1, games, gamesIndex)
                }
            }))
        } else {
            allGamesPanel.updateLabel(true)
            startNextGame(gamesIndex, games)
        }
    }

    private fun startNextGame(gamesIndex: Int, games: List<CoolGame>) {
        val newGamesIndex = gamesIndex + 1
        val newThrowIndex = 0
        val game = games.getOrNull(gamesIndex)
        if (game == null) {
            isfinished = true
            return
        }
        val newThrows = game.throws
        allGamesPanel.newGame()
        handleThrow(newThrows, newThrowIndex, games, newGamesIndex)
    }

    private fun addPresent(texture: Texture, startingX: Int, index: Int) {
        val xDest = (MegaManagers.randomManager.random.nextInt(10) * 30) + startingX
        val yDest = (MegaManagers.randomManager.random.nextInt(5) * 30) - 50
        val time = listOf(0.8f, 0.9f, 1f, 1.1f, 1.2f).random() + (index * 0.5f)
        val present = BaseActor(texture, 0f, Globals.WORLD_HEIGHT / 2f + 100f)
        present.addAction(Actions.moveTo(xDest.toFloat(), yDest.toFloat(), time, Interpolation.fastSlow))
        crew.addMember(present)
        presents.add(present)

    }

    private fun part2() {
        val sum = getInput().sumOf { line ->
            val gameId = line.split(":").first().split(" ").last().toInt()
            val input = line.split(":").last().drop(1)
            val games = input.split("; ")
                .flatMap { it.split(", ") }
                .groupBy { it.split(" ").last() }
                .map {
                    it.key to it.value.map { it.split(" ").first().toInt() }.max()
                }
            var power = 1
            games.forEach {
                power *= it.second
            }
            power
        }
        println(sum)
    }

    private fun part1() {
        val testGameMap = mapOf(
            "red" to 12,
            "green" to 13,
            "blue" to 14
        )

        val sum = getInput().map { line ->
            val gameId = line.split(":").first().split(" ").last().toInt()
            val games = line.split(":").last().drop(1).split("; ")
            Game(gameId, games)
        }.filterNot { game ->
            game.games.any {
                it.split(", ").any {
                    val amount = it.split(" ").first().toInt()
                    val color = it.split(" ").last()
                    amount > testGameMap.getValue(color)
                }
            }
        }.sumOf { it.id }
        println(sum)
    }

    override fun isDone(): Boolean {
        return isfinished
    }

    companion object {
        @Asset
        private val backgroundTexture = AssetDescriptor(Assets.Backgrounds.day2, Texture::class.java)

        @Asset
        private val blueGiftTexture = AssetDescriptor(Assets.gift_blue, Texture::class.java)

        @Asset
        private val redGiftTexture = AssetDescriptor(Assets.gift_red, Texture::class.java)

        @Asset
        private val greenGiftTexture = AssetDescriptor(Assets.gift_green, Texture::class.java)
    }
}
