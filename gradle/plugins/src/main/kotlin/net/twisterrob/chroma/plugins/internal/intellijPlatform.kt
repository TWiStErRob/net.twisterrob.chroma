package net.twisterrob.chroma.plugins.internal

import org.jetbrains.intellij.platform.gradle.extensions.IntelliJPlatformExtension

import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByName
import org.jetbrains.intellij.platform.gradle.Constants

internal val Project.intellijPlatform: IntelliJPlatformExtension
    get() = this.extensions.getByName<IntelliJPlatformExtension>(Constants.Extensions.INTELLIJ_PLATFORM)
