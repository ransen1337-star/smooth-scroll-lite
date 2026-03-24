package com.yourname.scroll.smoothscrolllite

import com.intellij.openapi.options.Configurable
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.*
import javax.swing.border.TitledBorder

class SmoothScrollConfigurable : Configurable {

    private var enabledCheckbox: JCheckBox? = null
    private var showFpsCheckbox: JCheckBox? = null
    private var speedSlider: JSlider? = null
    private var easingSlider: JSlider? = null

    override fun getDisplayName() = "Smooth Scroll Lite"

    override fun createComponent(): JComponent {
        val s = SmoothScrollSettings.getInstance()
        val panel = JPanel(GridBagLayout())

        fun gbc(x: Int, y: Int, width: Int = 1, weightX: Double = 0.0) =
            GridBagConstraints().apply {
                gridx = x; gridy = y; gridwidth = width; weightx = weightX
                fill = GridBagConstraints.HORIZONTAL
                insets = Insets(6, 8, 6, 8)
            }

        enabledCheckbox = JCheckBox("启用丝滑滚动", s.enabled)
        panel.add(enabledCheckbox!!, gbc(0, 0, width = 2, weightX = 1.0))

        showFpsCheckbox = JCheckBox("底部栏显示滚动 FPS", s.showFps)
        panel.add(showFpsCheckbox!!, gbc(0, 1, width = 2, weightX = 1.0))

        panel.add(JLabel("滚动速度:"), gbc(0, 2))
        speedSlider = JSlider(1, 50, s.scrollSpeed).apply {
            majorTickSpacing = 10; minorTickSpacing = 5
            paintTicks = true; paintLabels = true
            toolTipText = "每次滚轮触发的滚动量倍数"
        }
        panel.add(speedSlider!!, gbc(1, 2, weightX = 1.0))

        panel.add(JLabel("缓动强度:"), gbc(0, 3))
        easingSlider = JSlider(5, 50, (s.easingFactor * 100).toInt()).apply {
            majorTickSpacing = 15; minorTickSpacing = 5
            paintTicks = true; paintLabels = true
            toolTipText = "值越小越丝滑（惯性越强），值越大响应越快"
        }
        panel.add(easingSlider!!, gbc(1, 3, weightX = 1.0))

        panel.add(
            JLabel("<html><small>缓动强度：左侧 = 更丝滑（惯性大），右侧 = 更灵敏（响应快）</small></html>"),
            gbc(0, 4, width = 2, weightX = 1.0)
        )

        panel.add(JPanel(), GridBagConstraints().apply {
            gridy = 5; weightx = 1.0; weighty = 1.0
            fill = GridBagConstraints.BOTH
        })

        val wrapper = JPanel(java.awt.BorderLayout())
        wrapper.border = TitledBorder("Smooth Scroll Lite 设置")
        wrapper.add(panel, java.awt.BorderLayout.CENTER)
        return wrapper
    }

    override fun isModified(): Boolean {
        val s = SmoothScrollSettings.getInstance()
        return enabledCheckbox?.isSelected != s.enabled ||
                showFpsCheckbox?.isSelected != s.showFps ||
                speedSlider?.value != s.scrollSpeed ||
                easingSlider?.value != (s.easingFactor * 100).toInt()
    }

    override fun apply() {
        val s = SmoothScrollSettings.getInstance()
        s.enabled = enabledCheckbox?.isSelected ?: true
        s.showFps = showFpsCheckbox?.isSelected ?: false
        s.scrollSpeed = speedSlider?.value ?: 8
        s.easingFactor = (easingSlider?.value ?: 15) / 100.0
    }

    override fun reset() {
        val s = SmoothScrollSettings.getInstance()
        enabledCheckbox?.isSelected = s.enabled
        showFpsCheckbox?.isSelected = s.showFps
        speedSlider?.value = s.scrollSpeed
        easingSlider?.value = (s.easingFactor * 100).toInt()
    }
}
