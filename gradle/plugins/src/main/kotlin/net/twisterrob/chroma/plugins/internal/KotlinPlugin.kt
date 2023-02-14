package net.twisterrob.chroma.plugins.internal

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.withType

class KotlinPlugin : Plugin<Project> {

	override fun apply(target: Project) {
		target.plugins.apply(target.libs.plugins.kotlin.get().pluginId)

		target.kotlin.apply {
			jvmToolchain(target.libs.versions.java.get().toInt()) // TODO Kotlin versions
		}

		target.tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
			compilerOptions.allWarningsAsErrors.set(true)
			compilerOptions.verbose.set(true)
		}
	}
}
