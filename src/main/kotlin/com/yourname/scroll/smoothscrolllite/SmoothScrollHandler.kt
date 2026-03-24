package com.yourname.scroll.smoothscrolllite

import java.awt.Point
import java.awt.Toolkit
import java.awt.event.MouseWheelEvent
import java.awt.event.MouseWheelListener
import javax.swing.JScrollPane
import javax.swing.JViewport
import javax.swing.SwingUtilities

class SmoothScrollHandler(private val scrollPane: JScrollPane) : MouseWheelListener {

    private var targetY = 0.0
    private var targetX = 0.0
    private var currentY = 0.0
    private var currentX = 0.0

    @Volatile private var running = false
    private var renderThread: Thread? = null

    private var frameCount = 0
    private var fpsWindowStart = System.nanoTime()

    private val originalListeners = scrollPane.mouseWheelListeners.toList()

    init {
        originalListeners.forEach { scrollPane.removeMouseWheelListener(it) }
        scrollPane.addMouseWheelListener(this)
        syncPosition()
    }

    private fun syncPosition() {
        val vp = scrollPane.viewport ?: return
        currentY = vp.viewPosition.y.toDouble()
        currentX = vp.viewPosition.x.toDouble()
        targetY = currentY
        targetX = currentX
    }

    override fun mouseWheelMoved(e: MouseWheelEvent) {
        val settings = SmoothScrollSettings.getInstance()
        if (!settings.enabled) {
            originalListeners.forEach { it.mouseWheelMoved(e) }
            return
        }

        val vp: JViewport = scrollPane.viewport ?: return
        val viewSize = vp.viewSize
        val viewportSize = vp.extentSize
        val unitIncrement = scrollPane.verticalScrollBar?.unitIncrement ?: 16
        val delta = e.preciseWheelRotation * unitIncrement * settings.scrollSpeed

        if (e.isShiftDown) {
            val maxX = (viewSize.width - viewportSize.width).coerceAtLeast(0)
            targetX = (targetX + delta).coerceIn(0.0, maxX.toDouble())
        } else {
            val maxY = (viewSize.height - viewportSize.height).coerceAtLeast(0)
            targetY = (targetY + delta).coerceIn(0.0, maxY.toDouble())
        }

        startRenderLoop()
    }

    private fun startRenderLoop() {
        if (running) return
        running = true
        frameCount = 0
        fpsWindowStart = System.nanoTime()

        renderThread = Thread({
            var lastTime = System.nanoTime()

            while (running) {
                val now = System.nanoTime()
                val deltaMs = (now - lastTime) / 1_000_000.0
                lastTime = now

                val settings = SmoothScrollSettings.getInstance()
                val alpha = 1.0 - Math.pow(1.0 - settings.easingFactor.coerceIn(0.01, 0.5), deltaMs / 16.0)

                currentX += (targetX - currentX) * alpha
                currentY += (targetY - currentY) * alpha

                val dx = Math.abs(currentX - targetX)
                val dy = Math.abs(currentY - targetY)
                val finalX = if (dx < 0.5) { currentX = targetX; targetX.toInt() } else currentX.toInt()
                val finalY = if (dy < 0.5) { currentY = targetY; targetY.toInt() } else currentY.toInt()
                val done = dx < 0.5 && dy < 0.5

                frameCount++
                val elapsed = (now - fpsWindowStart) / 1_000_000_000.0
                if (elapsed >= 0.5 && settings.showFps) {
                    val fps = (frameCount / elapsed).toInt()
                    frameCount = 0
                    fpsWindowStart = now
                    FpsStatusBarWidget.getAll().forEach { it.updateFps(fps, true) }
                }

                try {
                    SwingUtilities.invokeAndWait {
                        val vp = scrollPane.viewport ?: return@invokeAndWait
                        vp.viewPosition = Point(finalX, finalY)
                        vp.paintImmediately(0, 0, vp.width, vp.height)
                        Toolkit.getDefaultToolkit().sync()
                    }
                } catch (_: Exception) {}

                if (done) {
                    running = false
                    if (SmoothScrollSettings.getInstance().showFps) {
                        FpsStatusBarWidget.getAll().forEach { it.updateFps(0, false) }
                    }
                    break
                }

                Thread.sleep(0, 500_000)
            }
        }, "SmoothScroll-RenderThread").also {
            it.isDaemon = true
            it.priority = Thread.MAX_PRIORITY
            it.start()
        }
    }

    fun detach() {
        running = false
        renderThread?.interrupt()
        renderThread = null
        scrollPane.removeMouseWheelListener(this)
        originalListeners.forEach { scrollPane.addMouseWheelListener(it) }
    }
}
