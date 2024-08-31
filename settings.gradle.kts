import net.twisterrob.gradle.doNotNagAbout
import net.twisterrob.gradle.settings.enableFeaturePreviewQuietly

// TODEL https://github.com/gradle/gradle/issues/18971
rootProject.name = "net-twisterrob-chroma"

pluginManagement {
	includeBuild("gradle/plugins")
}

plugins {
	id("net.twisterrob.gradle.plugin.nagging") version "0.17"
	id("org.gradle.toolchains.foojay-resolver-convention") version("0.8.0")
}

dependencyResolutionManagement {
	// TODO enable this once https://github.com/JetBrains/gradle-intellij-plugin/issues/1312 is resolved
	// For now, repositories are in gradle/plugins.
	//repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
}

enableFeaturePreviewQuietly("TYPESAFE_PROJECT_ACCESSORS", "Type-safe project accessors")

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
if ((System.getProperty("idea.version") ?: "") < "2023.3") {
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

// TODEL Gradle 8.2 sync in IDEA 2023.1 https://youtrack.jetbrains.com/issue/IDEA-320307.
@Suppress("MaxLineLength", "StringLiteralDuplication")
if ((System.getProperty("idea.version") ?: "") < "2024.1") {
	doNotNagAbout(
		"The BuildIdentifier.getName() method has been deprecated. " +
				"This is scheduled to be removed in Gradle 9.0. " +
				"Use getBuildPath() to get a unique identifier for the build. " +
				"Consult the upgrading guide for further information: " +
				"https://docs.gradle.org/${gradleVersion}/userguide/upgrading_version_8.html#build_identifier_name_and_current_deprecation",
		// There are 4 stack traces coming to this line, ignore them all at once.
		"at org.jetbrains.plugins.gradle.tooling.util.resolve.DependencyResolverImpl.resolveDependencies(DependencyResolverImpl.java:266)"
	)
} else {
	logger.warn("IDEA version changed, please review hack.")
}

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
				"https://docs.gradle.org/8.9/userguide/upgrading_version_8.html#unix_file_permissions_deprecated",
		"at com.intellij.gradle.toolingExtension.impl.model.resourceFilterModel.GradleResourceFilterModelBuilder.getFilters(GradleResourceFilterModelBuilder.groovy:46)"
	)
} else {
	logger.warn("IDEA version changed, please review hack.")
}
