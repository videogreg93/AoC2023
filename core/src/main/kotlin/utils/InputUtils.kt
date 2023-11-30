package gaia.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.input.GestureDetector
import com.badlogic.gdx.input.GestureDetector.GestureListener

fun InputProcessor.addAsInput() {
    val inputProcessor = Gdx.input.inputProcessor
    val multiplexer = if (inputProcessor is InputMultiplexer) inputProcessor else InputMultiplexer(inputProcessor)
    multiplexer.addProcessor(this)
    Gdx.input.inputProcessor = multiplexer
}

fun GestureListener.addAsGestureListener() {
    val inputProcessor = Gdx.input.inputProcessor
    val multiplexer = if (inputProcessor is InputMultiplexer) inputProcessor else InputMultiplexer(inputProcessor)
    multiplexer.addProcessor(GestureDetector(20f, 0.4f, 0.3f, 999f, this))
    Gdx.input.inputProcessor = multiplexer
}

fun InputProcessor.removeAsInput() {
    (Gdx.input.inputProcessor as? InputMultiplexer)?.removeProcessor(this)
        ?: ktx.log.error { "Could not remove this input processor. Are you sure you added it before?" }
}
