package com.yourname.scroll.smoothscrolllite

import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManager
import com.intellij.openapi.wm.StatusBar
import com.intellij.openapi.wm.StatusBarWidget
import com.intellij.openapi.wm.StatusBarWidgetFactory
import com.intellij.openapi.wm.WindowManager
import com.intellij.util.Consumer
import java.awt.event.MouseEvent

const val WIDGET_ID = "SmoothScrollLite.FPS"

class FpsStatusBarWidget(private val project: Project) :
    StatusBarWidget, StatusBarWidget.TextPresentation {

    private var statusBar: StatusBar? = null
    @Volatile private var fps: Int = 0
    @Volatile private var scrolling: Boolean = false

    override fun ID() = WIDGET_ID
    override fun getPresentation() = this
    override fun getAlignment() = 0f
    override fun getClickConsumer(): Consumer<MouseEvent>? = null
    override fun getTooltipText() = "Smooth Scroll Lite — 当前滚动帧率"

    override fun getText(): String {
        if (!SmoothScrollSettings.getInstance().showFps) return ""
        return if (scrolling) "Scroll $fps FPS" else "Scroll --"
    }

    override fun install(statusBar: StatusBar) { this.statusBar = statusBar }
    override fun dispose() { statusBar = null }

    fun updateFps(currentFps: Int, isScrolling: Boolean) {
        fps = currentFps
        scrolling = isScrolling
        statusBar?.updateWidget(WIDGET_ID)
    }

    companion object {
        fun getAll(): List<FpsStatusBarWidget> =
            ProjectManager.getInstance().openProjects
                .filter { !it.isDisposed }
                .mapNotNull {
                    WindowManager.getInstance().getStatusBar(it)
                        ?.getWidget(WIDGET_ID) as? FpsStatusBarWidget
                }
    }
}

class FpsStatusBarWidgetFactory : StatusBarWidgetFactory {
    override fun getId() = WIDGET_ID
    override fun getDisplayName() = "Smooth Scroll FPS"
    override fun isAvailable(project: Project) = true
    override fun createWidget(project: Project) = FpsStatusBarWidget(project)
    override fun disposeWidget(widget: StatusBarWidget) = widget.dispose()
    override fun canBeEnabledOn(statusBar: StatusBar) = true
}
