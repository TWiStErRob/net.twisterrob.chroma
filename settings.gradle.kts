import net.twisterrob.gradle.doNotNagAbout
import net.twisterrob.gradle.settings.enableFeaturePreviewQuietly

// TODEL https://github.com/gradle/gradle/issues/18971
rootProject.name = "net-twisterrob-chroma"

pluginManagement {
	includeBuild("gradle/plugins")
}

plugins {
	id("net.twisterrob.gradle.plugin.settings") version "0.15.1"
	id("org.gradle.toolchains.foojay-resolver-convention") version("0.6.0")
}

dependencyResolutionManagement {
	// TODO enable this once https://github.com/JetBrains/gradle-intellij-plugin/issues/1312 is resolved
	// For now, repositories are in gradle/plugins.
	//repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
}

enableFeaturePreviewQuietly("TYPESAFE_PROJECT_ACCESSORS", "Type-safe project accessors is an incubating feature.")

include(":idea")
include(":razer")

val gradleVersion: String = GradleVersion.current().version

// TODEL Gradle sync in IDEA 2022.3.1: https://youtrack.jetbrains.com/issue/IDEA-306975
@Suppress("MaxLineLength")
doNotNagAbout(
	"The AbstractArchiveTask.archivePath property has been deprecated. " +
			"This is scheduled to be removed in Gradle 9.0. " +
			"Please use the archiveFile property instead. " +
			"See https://docs.gradle.org/${gradleVersion}/dsl/org.gradle.api.tasks.bundling.AbstractArchiveTask.html#org.gradle.api.tasks.bundling.AbstractArchiveTask:archivePath for more details.",
	"at org.jetbrains.plugins.gradle.tooling.builder.ExternalProjectBuilderImpl\$_getSourceSets_closure"
)

// TODEL Gradle sync in IDEA 2022.3.1: https://youtrack.jetbrains.com/issue/IDEA-306975
@Suppress("MaxLineLength")
doNotNagAbout(
	"The AbstractArchiveTask.archivePath property has been deprecated. " +
			"This is scheduled to be removed in Gradle 9.0. " +
			"Please use the archiveFile property instead. " +
			"See https://docs.gradle.org/${gradleVersion}/dsl/org.gradle.api.tasks.bundling.AbstractArchiveTask.html#org.gradle.api.tasks.bundling.AbstractArchiveTask:archivePath for more details.",
	"at org.jetbrains.plugins.gradle.tooling.util.SourceSetCachedFinder.createArtifactsMap"
)

// TODEL Gradle 8.2 sync in IDEA 2023.1 https://youtrack.jetbrains.com/issue/IDEA-320266.
@Suppress("MaxLineLength", "StringLiteralDuplication")
if ((System.getProperty("idea.version") ?: "") < "2023.2") {
	// There are ton of warnings, ignoring them all by their class names in one suppression.
	doNotNagAbout(
		Regex(
			"^" +
					"(" +
					Regex.escape("The Project.getConvention() method has been deprecated. ") +
					"|" +
					Regex.escape("The org.gradle.api.plugins.Convention type has been deprecated. ") +
					"|" +
					Regex.escape("The org.gradle.api.plugins.JavaPluginConvention type has been deprecated. ") +
					")" +
					Regex.escape("This is scheduled to be removed in Gradle 9.0. ") +
					Regex.escape("Consult the upgrading guide for further information: ") +
					Regex.escape("https://docs.gradle.org/${gradleVersion}/userguide/upgrading_version_8.html#") +
					".*" +
					"(" +
					Regex.escape("at org.jetbrains.kotlin.idea.gradleTooling.KotlinTasksPropertyUtilsKt.") +
					"|" +
					Regex.escape("at org.jetbrains.plugins.gradle.tooling.util.JavaPluginUtil.") +
					"|" +
					Regex.escape("at org.jetbrains.plugins.gradle.tooling.builder.ExternalProjectBuilderImpl.") +
					"|" +
					Regex.escape("at org.jetbrains.plugins.gradle.tooling.builder.ProjectExtensionsDataBuilderImpl.") +
					")" +
					".*$"
		)
	)
} else {
	logger.warn("IDEA version changed, please review hack.")
}
