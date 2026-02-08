import net.twisterrob.gradle.settings.enableFeaturePreviewQuietly
import org.jetbrains.intellij.platform.gradle.extensions.intellijPlatform

// TODEL https://github.com/gradle/gradle/issues/18971
rootProject.name = "net-twisterrob-chroma"

pluginManagement {
	includeBuild("gradle/plugins")
}

plugins {
	id("net.twisterrob.gradle.plugin.nagging") version "0.19"
	id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
	id("org.jetbrains.intellij.platform.settings") version "2.11.0"
}

dependencyResolutionManagement {
	repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
	repositories {
		mavenCentral()
		intellijPlatform {
			defaultRepositories()
		}
	}
}

enableFeaturePreviewQuietly("TYPESAFE_PROJECT_ACCESSORS", "Type-safe project accessors")

include(":idea")
include(":razer")
