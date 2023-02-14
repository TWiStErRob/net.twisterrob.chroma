plugins {
	`kotlin-dsl`
}

repositories {
	mavenCentral()
}

dependencies {
	api(libs.kotlin.gradle)

	// TODEL https://github.com/gradle/gradle/issues/15383
	implementation(files(libs::class.java.protectionDomain.codeSource.location))
}

gradlePlugin {
	plugins {
		create("buildLib") {
			id = "net.twisterrob.chroma.plugins.library"
			implementationClass = "net.twisterrob.chroma.plugins.LibraryPlugin"
		}
	}
}
