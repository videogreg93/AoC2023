package gaia.managers.fonts

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.odencave.assets.Assets
import gaia.managers.MegaManagers

class FontManager : MegaManagers.Manager {
    lateinit var defaultFont: BitmapFont
    lateinit var largeFont: BitmapFont
    lateinit var smallerFont: BitmapFont
//    lateinit var titleFont: BitmapFont
//    lateinit var pressStartFont: BitmapFont

    override fun init() {
        val generator = FreeTypeFontGenerator(Gdx.files.internal(Assets.Fonts.FOT_NewRodin_Pro_EB))
        val params = FreeTypeFontGenerator.FreeTypeFontParameter().apply {
            size = 18
            color = Color.WHITE
        }
        defaultFont = generator.generateFont(params)
        smallerFont = generator.generateFont(
            FreeTypeFontGenerator.FreeTypeFontParameter().apply {
                size = 12
                color = Color.WHITE
            }
        )
        defaultFont.data.markupEnabled = true
        params.size = 24
        largeFont = generator.generateFont(params)
        largeFont.data.markupEnabled = true

        generator.dispose()
    }
}
