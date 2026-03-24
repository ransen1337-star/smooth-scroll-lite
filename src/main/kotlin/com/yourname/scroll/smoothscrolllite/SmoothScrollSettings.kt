package com.yourname.scroll.smoothscrolllite

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

@Service(Service.Level.APP)
@State(name = "SmoothScrollLiteSettings", storages = [Storage("SmoothScrollLite.xml")])
class SmoothScrollSettings : PersistentStateComponent<SmoothScrollSettings.State> {

    data class State(
        var enabled: Boolean = true,
        var scrollSpeed: Int = 8,
        var easingFactor: Double = 0.15,
        var showFps: Boolean = false
    )

    private var myState = State()

    override fun getState(): State = myState
    override fun loadState(state: State) { myState = state }

    var enabled: Boolean
        get() = myState.enabled
        set(value) { myState.enabled = value }

    var scrollSpeed: Int
        get() = myState.scrollSpeed
        set(value) { myState.scrollSpeed = value }

    var easingFactor: Double
        get() = myState.easingFactor
        set(value) { myState.easingFactor = value }

    var showFps: Boolean
        get() = myState.showFps
        set(value) { myState.showFps = value }

    companion object {
        fun getInstance(): SmoothScrollSettings =
            ApplicationManager.getApplication().getService(SmoothScrollSettings::class.java)
    }
}
