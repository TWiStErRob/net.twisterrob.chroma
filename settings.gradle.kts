import net.twisterrob.gradle.doNotNagAbout
import net.twisterrob.gradle.settings.enableFeaturePreviewQuietly
import org.jetbrains.intellij.platform.gradle.extensions.intellijPlatform

// TODEL https://github.com/gradle/gradle/issues/18971
rootProject.name = "net-twisterrob-chroma"

pluginManagement {
	includeBuild("gradle/plugins")
}

plugins {
	id("net.twisterrob.gradle.plugin.nagging") version "0.18"
	id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
	id("org.jetbrains.intellij.platform.settings") version "2.5.0"
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

val gradleVersion: String = GradleVersion.current().version

// TODEL Gradle 8.8 sync in IDEA 2024.1.4 https://youtrack.jetbrains.com/issue/IDEA-353787.
@Suppress("MaxLineLength", "StringLiteralDuplication")
if ((System.getProperty("idea.version") ?: "") < "2024.2") {
	doNotNagAbout(
		"The CopyProcessingSpec.getFileMode() method has been deprecated. " +
				"This is scheduled to be removed in Gradle 9.0. " +
				"Please use the getFilePermissions() method instead. " +
				"Consult the upgrading guide for further information: " +
				"https://docs.gradle.org/${gradleVersion}/userguide/upgrading_version_8.html#unix_file_permissions_deprecated",
		"at com.intellij.gradle.toolingExtension.impl.model.resourceFilterModel.GradleResourceFilterModelBuilder.getFilters(GradleResourceFilterModelBuilder.groovy:46)"
	)
	doNotNagAbout(
		"The CopyProcessingSpec.getDirMode() method has been deprecated. " +
				"This is scheduled to be removed in Gradle 9.0. " +
				"Please use the getDirPermissions() method instead. " +
				"Consult the upgrading guide for further information: " +
				"https://docs.gradle.org/${gradleVersion}/userguide/upgrading_version_8.html#unix_file_permissions_deprecated",
		"at com.intellij.gradle.toolingExtension.impl.model.resourceFilterModel.GradleResourceFilterModelBuilder.getFilters(GradleResourceFilterModelBuilder.groovy:46)"
	)
} else {
	logger.warn("IDEA version changed, please review hack.")
}
