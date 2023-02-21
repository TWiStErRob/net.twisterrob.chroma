package net.twisterrob.chroma.intellij.shortcuts.debug

import com.intellij.openapi.actionSystem.AnActionResult
import java.awt.event.KeyEvent

val KeyEvent.displayInfo: String
	get() {
		//You should only rely on the key char if the event is a key typed event.
		val keyString: String = when (this.id) {
			KeyEvent.KEY_TYPED -> "key character = '${this.keyChar}'"
			else -> "key code = ${this.keyCode} (${KeyEvent.getKeyText(this.keyCode)})"
		}
		val tmpString = KeyEvent.getModifiersExText(this.modifiersEx)
		val modString = "extended modifiers = ${this.modifiersEx}" + if (tmpString.isNotEmpty()) {
			" ($tmpString)"
		} else {
			" (no extended modifiers)"
		}
		val actionString = "action key? " + if (isActionKey) {
			"YES"
		} else {
			"NO"
		}
		val locationString = "key location: " + when (keyLocation) {
			KeyEvent.KEY_LOCATION_STANDARD -> "standard"
			KeyEvent.KEY_LOCATION_LEFT -> "left"
			KeyEvent.KEY_LOCATION_RIGHT -> "right"
			KeyEvent.KEY_LOCATION_NUMPAD -> "numpad"
			else -> "unknown" // (location == KeyEvent.KEY_LOCATION_UNKNOWN)
		}
		return "$keyString $modString $actionString $locationString"
	}

@Suppress("UnstableApiUsage")
val AnActionResult.displayInfo: String
	get() =
		when (this) {
			AnActionResult.PERFORMED -> "PERFORMED"
			AnActionResult.IGNORED -> "IGNORED"
			else -> if (this.failureCause != null) {
				"ERRORED(${this.failureCause})"
			} else {
				"UNKNOWN"
			}
		}
