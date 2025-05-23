import org.gradle.api.provider.Provider

plugins {
	`kotlin-dsl`
}

repositories {
	gradlePluginPortal()
}

dependencies {
	api(plugin(libs.plugins.kotlin))
	api(plugin(libs.plugins.intellij))

	// TODEL https://github.com/gradle/gradle/issues/15383
	implementation(files(libs::class.java.superclass.protectionDomain.codeSource.location))
}

gradlePlugin {
	plugins {
		create("buildLib") {
			id = "net.twisterrob.chroma.plugins.library"
			implementationClass = "net.twisterrob.chroma.plugins.LibraryPlugin"
		}
		create("buildIdea") {
			id = "net.twisterrob.chroma.plugins.idea"
			implementationClass = "net.twisterrob.chroma.plugins.IdeaPlugin"
		}
	}
}

fun DependencyHandlerScope.plugin(plugin: Provider<PluginDependency>): Provider<String> =
	plugin.map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" }
