package net.twisterrob.chroma.plugins

import net.twisterrob.chroma.plugins.internal.KotlinPlugin
import net.twisterrob.chroma.plugins.internal.intellijPlatform
import net.twisterrob.chroma.plugins.internal.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.named
import org.jetbrains.intellij.platform.gradle.tasks.RunIdeTask

class IdeaPlugin : Plugin<Project> {

	override fun apply(target: Project) {
		target.plugins.apply(KotlinPlugin::class)
		target.plugins.apply(target.libs.plugins.intellij.get().pluginId)
		target.intellijPlatform.apply {
			pluginConfiguration {
				version = target.libs.versions.idea
				name = "Show Shortcuts with Razer Chroma"
			}
		}
		target.tasks.named<RunIdeTask>("runIde").configure {
			systemProperty(
				"java.util.logging.config.file",
				target.file("src/main/runtime/logging.properties")
			)
		}
	}
}
