package com.github.psurkov.cognitivecomplexity.services

import com.intellij.openapi.project.Project
import com.github.psurkov.cognitivecomplexity.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
