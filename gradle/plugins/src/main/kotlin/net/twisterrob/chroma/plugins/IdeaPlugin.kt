package net.twisterrob.chroma.plugins

import net.twisterrob.chroma.plugins.internal.KotlinPlugin
import net.twisterrob.chroma.plugins.internal.intellij
import net.twisterrob.chroma.plugins.internal.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class IdeaPlugin : Plugin<Project> {

	override fun apply(target: Project) {
		target.plugins.apply(KotlinPlugin::class)
		target.plugins.apply(target.libs.plugins.intellij.get().pluginId)
		target.intellij.apply {
			version.set(target.libs.versions.idea)
			pluginName.set("Show Shortcuts with Razer Chroma")
			updateSinceUntilBuild.set(false)
		}
	}
}
