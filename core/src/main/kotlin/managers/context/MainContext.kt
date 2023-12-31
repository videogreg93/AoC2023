package gaia.managers.context

import Globals.WORLD_HEIGHT
import Globals.WORLD_WIDTH
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Json
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.kotcrab.vis.ui.VisUI
import ktx.inject.Context
import ktx.inject.register
import org.reflections.Reflections
import org.reflections.scanners.FieldAnnotationsScanner
import org.reflections.scanners.SubTypesScanner
import org.reflections.scanners.TypeAnnotationsScanner
import kotlin.random.Random

object MainContext {
    val context = Context()

    fun register() {
        VisUI.load()
        context.register {
            bindSingleton(SpriteBatch())
            bindSingleton(Random(Random.nextInt()))
            bindSingleton(
                Reflections(
                    "",
                    FieldAnnotationsScanner(),
                    TypeAnnotationsScanner(),
                    SubTypesScanner()
                )
            )
            bindSingleton {
                Stage(FitViewport(WORLD_WIDTH, WORLD_HEIGHT), context.inject<SpriteBatch>())
            }
//            bindSingleton { Executor() as CommandExecutor }
            /*bindSingleton {
                GUIConsole(
                    VisUI.getSkin(),
                    false,
                    if (BuildConfig.isDebug) {
                        Input.Keys.EQUALS
                    } else {
                        -11
                    },
                    VisWindow::class.java,
                    VisTable::class.java,
                    "default-pane",
                    TextField::class.java,
                    VisTextButton::class.java,
                    VisLabel::class.java,
                    VisScrollPane::class.java
                ).apply {
                    setCommandExecutor(Executor())
                    setNoHoverColor(Color.GREEN)
                    setNoHoverAlpha(0.5f)
                    setHoverColor(Color.GREEN.copy(alpha = 0.9f))
                    setSizePercent(50f, 50f)
                    setPosition((Globals.WORLD_WIDTH / 2).toInt(), (Globals.WORLD_HEIGHT / 1.5).toInt())
                    enableSubmitButton(true)
                    resetInputProcessing()
                    // You could decide to have the logging inside of this console.
//                    Gdx.app.applicationLogger = this
                } as Console
            }*/
            bindSingleton {
                ShapeRenderer()
            }
            bindSingleton {
                Json()
            }
        }
    }

    inline fun <reified T : Any> inject(): T {
        return context.inject()
    }
}
