package actions

import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.Actor
import gaia.managers.MegaManagers
import gaia.managers.events.EventInstance
import gaia.managers.events.EventListener

class OnEventAction(val eventId: String) : Action(), EventListener<EventInstance> {

    private var eventTriggered = false

    override fun onEvent(event: EventInstance) {
        eventTriggered = true
    }

    override fun reset() {
        super.reset()
        eventTriggered = false
    }

    override fun setActor(actor: Actor?) {
        super.setActor(actor)
        if (actor != null) {
            MegaManagers.eventManager.subscribeTo(eventId, this)
        } else {
            MegaManagers.eventManager.unsubscribe(eventId, this)
        }
    }

    override fun act(delta: Float): Boolean {
        return eventTriggered
    }
}
