<div align="center">

<img src="src/main/resources/META-INF/pluginIcon.svg" width="80" alt="Smooth Scroll Lite" />

# Smooth Scroll Lite

**为 IntelliJ IDEA 带来丝滑滚动体验的轻量插件**

[![Version](https://img.shields.io/badge/version-1.0.0-6C63FF?style=for-the-badge)](https://github.com/ransen1337-star/smooth-scroll-lite/releases)
[![License](https://img.shields.io/badge/license-MIT-00C896?style=for-the-badge)](LICENSE)
[![Platform](https://img.shields.io/badge/IDEA-2025.2+-FE6B00?style=for-the-badge&logo=intellijidea&logoColor=white)](https://www.jetbrains.com/idea/)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.1.20-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![Build](https://img.shields.io/badge/build-passing-4CAF50?style=for-the-badge)](#)

<br/>

> 告别跳跃式滚动，用指数缓动算法让每一次滚轮都顺滑如丝。

</div>

---

## ✨ 功能特性

```
🎯  丝滑动画   →  指数缓动算法，滚动过渡自然流畅
⚡  满帧渲染   →  独立渲染线程，突破 Swing Timer 15ms 限制
↔️  水平滚动   →  Shift + 滚轮，轻松横向浏览宽代码
📊  FPS 显示   →  状态栏实时帧率，掌握渲染性能
🎛️  快捷设置   →  工具栏一键弹出，随时调整参数
```

---

## 📦 安装

**从源码构建**

```bash
./gradlew buildPlugin
# 产物在 build/distributions/*.zip
```

**手动安装 zip**

```
Settings → Plugins → ⚙️ → Install Plugin from Disk → 选择 .zip → 重启
```

或直接从 [Releases](https://github.com/ransen1337-star/smooth-scroll-lite/releases) 下载最新版本。

---

## ⚙️ 配置项

`Settings → Tools → Smooth Scroll Lite` 或点击顶部工具栏齿轮图标。

| 选项 | 默认值 | 说明 |
|------|:------:|------|
| 启用丝滑滚动 | ✅ | 插件总开关 |
| 显示 FPS | ✅ | 状态栏实时帧率 |
| 滚动速度 | `10` | 每次滚轮触发的滚动量（1 ~ 50） |
| 缓动强度 | `0.15` | 值越小惯性越强，值越大响应越快 |

---

## 🔧 兼容性

| 项目 | 要求 |
|------|------|
| IntelliJ IDEA | 2025.2+（build 252+） |
| JDK | 21+ |

---

## 📄 License

[MIT](LICENSE) © Ransen1337 - Star
