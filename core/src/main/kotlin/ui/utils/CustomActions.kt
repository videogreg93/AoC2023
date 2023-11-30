package gaia.ui.utils

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import base.BaseActor

fun BaseActor.addForeverAction(action: (() -> Unit)) {
    addAction(Actions.forever(Actions.run(action)))
}

