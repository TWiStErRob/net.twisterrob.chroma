plugins {
	`kotlin-dsl`
}

repositories {
	gradlePluginPortal()
}

dependencies {
	api(libs.kotlin.gradle)
	api(libs.intellij.gradle)

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
