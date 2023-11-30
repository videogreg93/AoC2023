package ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.ui.Skin

object Skins {
    val default by lazy { Skin(Gdx.files.internal("skins/uiskin.json")) }
}
