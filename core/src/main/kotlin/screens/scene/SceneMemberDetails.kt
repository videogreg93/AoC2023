@file:Suppress("UNCHECKED_CAST")

package screens.scene

import com.badlogic.gdx.Input
import com.kotcrab.vis.ui.widget.VisLabel
import com.kotcrab.vis.ui.widget.VisTable
import com.kotcrab.vis.ui.widget.VisTextField
import com.kotcrab.vis.ui.widget.VisWindow
import base.BaseActor
import ktx.actors.onKeyDown
import ktx.actors.onKeyboardFocus
import ktx.actors.setKeyboardFocus
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KVisibility
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.javaType

@ExperimentalStdlibApi
class SceneMemberDetails(private val actor: BaseActor) : VisWindow(actor.toString()) {
    private val root = VisTable()
    private val positionValueLabel = VisLabel()
    private val zIndexValueLabel = VisLabel()

    private val entityFields = getEntityFields()
    private val fieldMap = HashMap<KMutableProperty1<BaseActor, *>, EntityFieldLabel>()

    init {
        addCloseButton()

        root.add(VisLabel("Position"))
        root.add(positionValueLabel).row()
        root.add(VisLabel("draw index"))
        root.add(zIndexValueLabel).row()

        actor.getEntityFields().forEach { field ->
            root.add(VisLabel(field.name))
            val valueLabel = EntityFieldLabel(actor, field as KMutableProperty1<BaseActor, *>)
            root.add(valueLabel).row()
            fieldMap[field] = valueLabel
        }


        add(root)
        setSize(300f, 1080f)
        x = Globals.WORLD_WIDTH

    }

    override fun act(delta: Float) {
        super.act(delta)
        positionValueLabel.setText("X: ${actor.x} Y: ${actor.y}")
        zIndexValueLabel.setText(actor.drawIndex)

        fieldMap.values.forEach {
            it.update()
        }

    }

    private fun getEntityFields(): List<KMutableProperty1<BaseActor, *>> {
        return BaseActor::class.declaredMemberProperties.filter { it.visibility == KVisibility.PUBLIC }
            .filterIsInstance<KMutableProperty1<BaseActor, *>>()
    }

    // TODO this stuff is still pretty basic
    internal class EntityFieldLabel(val entity: BaseActor, val field: KMutableProperty1<BaseActor, *>) :
        VisTextField() {
        private var shouldUpdate = true

        init {
            onKeyboardFocus { hasFocus ->
                updateMemberProperty(hasFocus)
            }
            onKeyDown {
                when (it) {
                    Input.Keys.ENTER -> {
                        updateMemberProperty(false)
                        setKeyboardFocus(false)
                    }
                }
            }
        }

        private fun updateMemberProperty(hasFocus: Boolean) {
            shouldUpdate = !hasFocus
            if (!hasFocus) {
                when (field.returnType.javaType) {
                    String::class.java -> field.setter.call(entity, text)
                    Int::class.java -> field.setter.call(entity, text.toInt())
                    Float::class.java -> field.setter.call(entity, text.toFloat())
                    Boolean::class.java -> field.setter.call(entity, text?.toBoolean())
                }
            }
        }

        fun update() {
            if (shouldUpdate) {
                setText(field.get(entity).toString())
            }
        }
    }


}
