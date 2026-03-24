package com.yourname.scroll.smoothscrolllite

import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity

class SmoothScrollStartupActivity : ProjectActivity {
    override suspend fun execute(project: Project) {
        SmoothScrollPlugin.getInstance()
    }
}
