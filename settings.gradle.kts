import net.twisterrob.gradle.settings.enableFeaturePreviewQuietly

// TODEL https://github.com/gradle/gradle/issues/18971
rootProject.name = "net-twisterrob-chroma"

pluginManagement {
	includeBuild("gradle/plugins")
	resolutionStrategy {
		eachPlugin {
			when (requested.id.id) {
				"net.twisterrob.settings" -> {
					useModule("net.twisterrob.gradle:twister-convention-settings:${requested.version}")
				}
			}
		}
	}
}

plugins {
	id("net.twisterrob.settings") version "0.15"
}

dependencyResolutionManagement {
	// TODO enable this once https://github.com/JetBrains/gradle-intellij-plugin/issues/1312 is resolved
	// For now, repositories are in gradle/plugins.
	//repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
}

enableFeaturePreviewQuietly("TYPESAFE_PROJECT_ACCESSORS", "Type-safe project accessors is an incubating feature.")

include(":idea")
include(":razer")
