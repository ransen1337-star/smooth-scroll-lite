package com.yourname.scroll.smoothscrolllite

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.*

class SmoothScrollAction : AnAction("Smooth Scroll", "配置丝滑滚动设置", AllIcons.General.Settings) {

    override fun actionPerformed(e: AnActionEvent) {
        val popup = JBPopupFactory.getInstance()
            .createComponentPopupBuilder(buildPanel(), null)
            .setTitle("Smooth Scroll Lite")
            .setMovable(true)
            .setResizable(false)
            .setRequestFocus(true)
            .createPopup()

        val component = e.inputEvent?.component
        if (component != null) popup.showUnderneathOf(component)
        else popup.showInFocusCenter()
    }

    private fun buildPanel(): JPanel {
        val s = SmoothScrollSettings.getInstance()
        val panel = JBPanel<JBPanel<*>>(GridBagLayout())
        panel.preferredSize = Dimension(300, 220)

        fun gbc(x: Int, y: Int, width: Int = 1, weightX: Double = 0.0, topInset: Int = 6) =
            GridBagConstraints().apply {
                gridx = x; gridy = y; gridwidth = width
                weightx = weightX
                fill = GridBagConstraints.HORIZONTAL
                insets = Insets(topInset, 10, 2, 10)
            }

        val enabledBox = JCheckBox("启用丝滑滚动", s.enabled)
        panel.add(enabledBox, gbc(0, 0, width = 2, weightX = 1.0))

        val showFpsBox = JCheckBox("底部栏显示 FPS", s.showFps)
        panel.add(showFpsBox, gbc(0, 1, width = 2, weightX = 1.0))

        panel.add(JSeparator(), GridBagConstraints().apply {
            gridx = 0; gridy = 2; gridwidth = 2; weightx = 1.0
            fill = GridBagConstraints.HORIZONTAL
            insets = Insets(4, 10, 4, 10)
        })

        val speedLabel = JBLabel("${s.scrollSpeed}")
        val speedSlider = JSlider(1, 50, s.scrollSpeed).apply {
            paintTicks = false; paintLabels = false
            addChangeListener { speedLabel.text = "$value" }
        }
        panel.add(JBLabel("滚动速度"), gbc(0, 3))
        panel.add(speedSlider, gbc(1, 3, weightX = 1.0))
        panel.add(JBLabel(""), gbc(0, 4, topInset = 0))
        panel.add(speedLabel, gbc(1, 4, weightX = 1.0, topInset = 0))

        val easingLabel = JBLabel("${(s.easingFactor * 100).toInt()}")
        val easingSlider = JSlider(5, 50, (s.easingFactor * 100).toInt()).apply {
            paintTicks = false; paintLabels = false
            addChangeListener { easingLabel.text = "$value" }
        }
        panel.add(JBLabel("缓动强度"), gbc(0, 5))
        panel.add(easingSlider, gbc(1, 5, weightX = 1.0))
        panel.add(JBLabel(""), gbc(0, 6, topInset = 0))
        panel.add(easingLabel, gbc(1, 6, weightX = 1.0, topInset = 0))

        enabledBox.addActionListener { s.enabled = enabledBox.isSelected }
        showFpsBox.addActionListener { s.showFps = showFpsBox.isSelected }
        speedSlider.addChangeListener { if (!speedSlider.valueIsAdjusting) s.scrollSpeed = speedSlider.value }
        easingSlider.addChangeListener { if (!easingSlider.valueIsAdjusting) s.easingFactor = easingSlider.value / 100.0 }

        return panel
    }
}
