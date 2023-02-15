package net.twisterrob.chroma.plugins

import net.twisterrob.chroma.plugins.internal.KotlinPlugin
import net.twisterrob.chroma.plugins.internal.intellij
import net.twisterrob.chroma.plugins.internal.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.jetbrains.intellij.tasks.RunIdeTask
import org.jetbrains.kotlin.gradle.utils.named

class IdeaPlugin : Plugin<Project> {

	override fun apply(target: Project) {
		target.plugins.apply(KotlinPlugin::class)
		target.plugins.apply(target.libs.plugins.intellij.get().pluginId)
		target.intellij.apply {
			version.set(target.libs.versions.idea)
			pluginName.set("Show Shortcuts with Razer Chroma")
			updateSinceUntilBuild.set(false)
		}
		// > Task :idea:jarSearchableOptions
		// [gradle-intellij-plugin :idea idea:idea:jarSearchableOptions] No searchable options found.
		// If plugin is not supposed to provide custom settings exposed in UI, disable building searchable options to decrease the build time.
		// See: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin-faq.html#how-to-disable-building-searchable-options
		target.tasks.named("buildSearchableOptions").configure {
			enabled = false
		}
		target.tasks.named("jarSearchableOptions").configure {
			enabled = false
		}
		target.tasks.named<RunIdeTask>("runIde").configure {
			systemProperty(
				"java.util.logging.config.file",
				target.file("src/main/runtime/logging.properties")
			)
		}
	}
}
