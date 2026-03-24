package com.yourname.scroll.smoothscrolllite

import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.Service
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.editor.event.EditorFactoryEvent
import com.intellij.openapi.editor.event.EditorFactoryListener
import java.awt.Component
import java.util.concurrent.ConcurrentHashMap
import javax.swing.JScrollPane
import javax.swing.SwingUtilities

@Service(Service.Level.APP)
class SmoothScrollPlugin : Disposable {

    private val handlers = ConcurrentHashMap<Editor, SmoothScrollHandler>()

    init {
        val editorFactory = EditorFactory.getInstance()
        editorFactory.allEditors.forEach { attach(it) }
        editorFactory.addEditorFactoryListener(object : EditorFactoryListener {
            override fun editorCreated(event: EditorFactoryEvent) = attach(event.editor)
            override fun editorReleased(event: EditorFactoryEvent) = detach(event.editor)
        }, this)
    }

    private fun attach(editor: Editor) {
        if (handlers.containsKey(editor)) return
        SwingUtilities.invokeLater {
            val scrollPane = findScrollPane(editor.component) ?: return@invokeLater
            handlers[editor] = SmoothScrollHandler(scrollPane)
        }
    }

    private fun detach(editor: Editor) {
        handlers.remove(editor)?.detach()
    }

    private fun findScrollPane(component: Component): JScrollPane? {
        var c: Component? = component
        while (c != null) {
            if (c is JScrollPane) return c
            c = c.parent
        }
        return findScrollPaneDown(component)
    }

    private fun findScrollPaneDown(component: Component): JScrollPane? {
        if (component is JScrollPane) return component
        if (component is java.awt.Container) {
            for (child in component.components) {
                val result = findScrollPaneDown(child)
                if (result != null) return result
            }
        }
        return null
    }

    override fun dispose() {
        handlers.values.forEach { it.detach() }
        handlers.clear()
    }

    companion object {
        fun getInstance(): SmoothScrollPlugin =
            ApplicationManager.getApplication().getService(SmoothScrollPlugin::class.java)
    }
}
