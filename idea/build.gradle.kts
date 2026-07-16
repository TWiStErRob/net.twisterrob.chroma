import org.gradle.process.CommandLineArgumentProvider
import org.jetbrains.intellij.platform.gradle.IntelliJPlatformType
import org.jetbrains.intellij.platform.gradle.tasks.RunIdeTask
import org.jetbrains.intellij.platform.gradle.tasks.VerifyPluginTask.FailureLevel

plugins {
	id("net.twisterrob.chroma.plugins.idea")
}

dependencies {
	implementation(projects.razer) {
		// java.lang.LinkageError: loader constraint violation when loading the Razer Chroma client.
		exclude(group = "org.slf4j", module = "slf4j-api")
		// https://plugins.jetbrains.com/docs/intellij/using-kotlin.html#coroutinesLibraries
		// Plugins must use the bundled coroutine library from the target IDE.
		exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-core")
	}

	testImplementation(libs.test.jupiter)
	testRuntimeOnly(libs.test.jupiter.launcher)
}

tasks.test {
	useJUnitPlatform()
}

tasks.named<RunIdeTask>("runIde") {
	val testProject = file("testProject")
	argumentProviders.add(object : CommandLineArgumentProvider {
		override fun asArguments() = listOf(testProject.absolutePath)
	})
}

intellijPlatform {
	pluginVerification {
		failureLevel = FailureLevel.ALL
		ides {
			create(IntelliJPlatformType.IntellijIdeaCommunity, "2022.3.1") // Android Studio: Giraffe
			create(IntelliJPlatformType.IntellijIdeaCommunity, "2024.3.1") // Android Studio: Meerkat
		}
	}
}
