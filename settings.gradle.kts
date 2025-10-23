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
	id("org.jetbrains.intellij.platform.settings") version "2.10.2"
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

// TODEL Gradle 9.1 vs intellij plugin 2.9.0 https://github.com/JetBrains/intellij-platform-gradle-plugin/issues/2024
@Suppress("detekt.MaxLineLength")
doNotNagAbout(
	Regex(
		"Declaring dependencies using multi-string notation has been deprecated. ".escape() +
				"This will fail with an error in Gradle 10. ".escape() +
				"Please use single-string notation instead: ".escape() +
				"\"${"idea:ideaIC:".escape()}\\d+\\.\\d+\\.\\d+${"@tar.gz\". ".escape()}" +
				"Consult the upgrading guide for further information: ".escape() +
				"https://docs.gradle.org/${gradleVersion}/userguide/upgrading_version_9.html#dependency_multi_string_notation".escape() +
				".*",
	),
	//"at org.jetbrains.intellij.platform.gradle.extensions.IntelliJPlatformDependenciesHelper.createIntelliJPlatformInstaller(IntelliJPlatformDependenciesHelper.kt:895)",
)

// TODEL Gradle 9.1 vs intellij plugin 2.9.0 https://github.com/JetBrains/intellij-platform-gradle-plugin/issues/2024
@Suppress("detekt.MaxLineLength")
doNotNagAbout(
		"Declaring dependencies using multi-string notation has been deprecated. " +
				"This will fail with an error in Gradle 10. " +
				"Please use single-string notation instead: " +
				"\"com.jetbrains.intellij.java:java-compiler-ant-tasks\". " +
				"Consult the upgrading guide for further information: " +
				"https://docs.gradle.org/${gradleVersion}/userguide/upgrading_version_9.html#dependency_multi_string_notation",
	//"at org.jetbrains.intellij.platform.gradle.extensions.IntelliJPlatformDependenciesHelper.createDependency(IntelliJPlatformDependenciesHelper.kt:1355)",
)

// TODEL Gradle 9.1 vs intellij plugin 2.9.0 https://github.com/JetBrains/intellij-platform-gradle-plugin/issues/2024
@Suppress("detekt.MaxLineLength")
doNotNagAbout(
	Regex(
		"Declaring dependencies using multi-string notation has been deprecated. ".escape() +
				"This will fail with an error in Gradle 10. ".escape() +
				"Please use single-string notation instead: ".escape() +
				"\"${"bundledModule:intellij-platform-test-runtime:".escape()}IC-\\d+\\.\\d+\\.\\d+${"\". ".escape()}" +
				"Consult the upgrading guide for further information: ".escape() +
				"https://docs.gradle.org/${gradleVersion}/userguide/upgrading_version_9.html#dependency_multi_string_notation".escape() +
				".*",
	),
	//"at org.jetbrains.intellij.platform.gradle.extensions.IntelliJPlatformDependenciesHelper.createIntelliJPlatformTestRuntime$IntelliJPlatformGradlePlugin(IntelliJPlatformDependenciesHelper.kt:1337)",
)

private fun String.escape(): String = Regex.escape(this)
