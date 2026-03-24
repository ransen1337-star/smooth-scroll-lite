<div align="center">

# 🖱️ Smooth Scroll Lite

**为 IntelliJ IDEA 带来丝滑滚动体验的轻量插件**

[![Version](https://img.shields.io/badge/version-1.0.0-blue?style=flat-square)](https://github.com/ransen1337-star/smooth-scroll-lite/releases)
[![License](https://img.shields.io/badge/license-MIT-green?style=flat-square)](LICENSE)
[![Platform](https://img.shields.io/badge/platform-IntelliJ%20IDEA%202025.2+-orange?style=flat-square)](https://www.jetbrains.com/idea/)
[![Kotlin](https://img.shields.io/badge/kotlin-2.1.20-purple?style=flat-square&logo=kotlin)](https://kotlinlang.org/)

</div>

---

## ✨ 功能特性

| 功能 | 说明 |
|------|------|
| 🎯 丝滑动画滚动 | 指数缓动算法，告别跳跃式滚动 |
| ⚡ 满帧渲染 | 独立渲染线程，突破 Swing Timer 15ms 限制 |
| ↔️ 水平滚动 | `Shift + 滚轮` 支持水平方向滚动 |
| 📊 FPS 显示 | 底部状态栏实时显示当前滚动帧率 |
| 🎛️ 快捷设置 | 顶部工具栏一键弹出设置面板 |

---

## 📦 安装

**方式一：从源码构建**

```bash
./gradlew buildPlugin
```

生成的 `.zip` 在 `build/distributions/` 目录下。

**方式二：直接安装 zip**

1. 下载 [Releases](https://github.com/ransen1337-star/smooth-scroll-lite/releases) 页面的最新 `.zip`
2. 打开 IDEA → `Settings → Plugins → ⚙️ → Install Plugin from Disk`
3. 选择 `.zip` 文件，重启 IDE

---

## ⚙️ 设置

点击顶部工具栏齿轮图标，或进入 `Settings → Tools → Smooth Scroll Lite`。

| 选项 | 说明 |
|------|------|
| 启用丝滑滚动 | 总开关 |
| 底部栏显示 FPS | 滚动时在状态栏显示实时帧率 |
| 滚动速度 | 每次滚轮触发的滚动量（1 ~ 50） |
| 缓动强度 | 值越小惯性越强越丝滑，值越大响应越快 |

---

## 🔧 兼容性

- IntelliJ IDEA 2025.2+（build 252+）
- JDK 21+

---

## 📄 License

[MIT](LICENSE) © Ransen1337 - Star
