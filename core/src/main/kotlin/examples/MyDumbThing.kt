package examples

import base.BaseActor
import com.badlogic.gdx.graphics.Texture
import gaia.base.Crew
import gaia.ui.utils.addForeverAction
import screens.base.EditorEntity

@EditorEntity
class MyDumbThing: BaseActor(Texture("assets/libgdx.png")) {

    override fun start(crew: Crew) {
        super.start(crew)
        println("STAWFWEF")
        addForeverAction {
            x += 1
        }
    }
}
