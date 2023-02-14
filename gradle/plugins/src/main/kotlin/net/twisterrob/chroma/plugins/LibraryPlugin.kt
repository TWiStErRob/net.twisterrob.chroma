package net.twisterrob.chroma.plugins

import net.twisterrob.chroma.plugins.internal.KotlinPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class LibraryPlugin : Plugin<Project> {

	override fun apply(target: Project) {
		target.plugins.apply(KotlinPlugin::class)
	}
}
