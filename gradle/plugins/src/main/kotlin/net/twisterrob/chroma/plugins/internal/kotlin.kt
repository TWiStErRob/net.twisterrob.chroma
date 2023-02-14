package net.twisterrob.chroma.plugins.internal

import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByName
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension

val Project.kotlin: KotlinProjectExtension
    get() = this.extensions.getByName<KotlinProjectExtension>("kotlin")
