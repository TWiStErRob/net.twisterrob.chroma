package net.twisterrob.chroma.plugins.internal

import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.getByName
import org.jetbrains.intellij.platform.gradle.Constants
import org.jetbrains.intellij.platform.gradle.extensions.IntelliJPlatformDependenciesExtension
import org.jetbrains.intellij.platform.gradle.extensions.IntelliJPlatformExtension

internal val Project.intellijPlatform: IntelliJPlatformExtension
	get() = this.extensions.getByName<IntelliJPlatformExtension>(Constants.Extensions.INTELLIJ_PLATFORM)

internal val DependencyHandler.intellijPlatform: IntelliJPlatformDependenciesExtension
	get() = this.extensions.getByName<IntelliJPlatformDependenciesExtension>(Constants.Extensions.INTELLIJ_PLATFORM)
