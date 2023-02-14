package net.twisterrob.chroma.plugins.internal

import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByName
import org.jetbrains.intellij.IntelliJPluginExtension

val Project.intellij: IntelliJPluginExtension
    get() = this.extensions.getByName<IntelliJPluginExtension>("intellij")
