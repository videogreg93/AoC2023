package com.odencave

import gaia.managers.input.ActionListener
import ui.BasicScreen

class DefaultStartingScreen : BasicScreen("Default Starting Screen") {

    override fun onAction(action: ActionListener.InputAction): Boolean {
        println(action)
        return true
    }
}
