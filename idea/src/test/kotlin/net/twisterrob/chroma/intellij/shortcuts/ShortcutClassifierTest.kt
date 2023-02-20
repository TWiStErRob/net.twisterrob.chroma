package net.twisterrob.chroma.intellij.shortcuts

import com.intellij.openapi.actionSystem.KeyboardShortcut
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.awt.event.KeyEvent

class ShortcutClassifierTest {

	@MethodSource("paramsClassify")
	@ParameterizedTest fun test(shortcut: String, modifiers: String, expected: ShortcutClass) {
		val sut = ShortcutClassifier()
		val modifierMask = modifiers.split("|").map {
			when (it) {
				"NONE" -> 0
				"SHIFT" -> KeyEvent.SHIFT_DOWN_MASK
				"CONTROL" -> KeyEvent.CTRL_DOWN_MASK
				"ALT" -> KeyEvent.ALT_DOWN_MASK
				"META" -> KeyEvent.META_DOWN_MASK
				else -> error("Unknown modifier: ${it}")
			}
		}.reduce(Int::or)

		val result = sut.classify(KeyboardShortcut.fromString(shortcut), modifierMask)

		assertEquals(expected, result)
	}

	companion object {

		@JvmStatic
		fun paramsClassify(): List<Arguments> = listOf<Arguments>(
			Arguments.of("X", "NONE", ShortcutClass.MATCHING_IMMEDIATE),
			Arguments.of("X", "SHIFT", ShortcutClass.NOT_MATCHING),
			Arguments.of("X", "CONTROL", ShortcutClass.NOT_MATCHING),
			Arguments.of("X", "ALT", ShortcutClass.NOT_MATCHING),
			Arguments.of("X", "META", ShortcutClass.NOT_MATCHING),
			Arguments.of("shift X", "NONE", ShortcutClass.MATCHING_MODIFIER),
			Arguments.of("shift X", "SHIFT", ShortcutClass.MATCHING_IMMEDIATE),
			Arguments.of("shift X", "CONTROL", ShortcutClass.NOT_MATCHING),
			Arguments.of("shift X", "ALT", ShortcutClass.NOT_MATCHING),
			Arguments.of("shift X", "META", ShortcutClass.NOT_MATCHING),
			Arguments.of("shift X", "META|CONTROL", ShortcutClass.NOT_MATCHING),
			Arguments.of("shift X", "META|ALT", ShortcutClass.NOT_MATCHING),
			Arguments.of("shift X", "SHIFT|META", ShortcutClass.NOT_MATCHING),
			Arguments.of("shift X", "SHIFT|CONTROL", ShortcutClass.NOT_MATCHING),
			Arguments.of("shift X", "SHIFT|ALT", ShortcutClass.NOT_MATCHING),
			Arguments.of("shift X", "SHIFT|ALT|CONTROL", ShortcutClass.NOT_MATCHING),
			Arguments.of("shift X", "SHIFT|ALT|META", ShortcutClass.NOT_MATCHING),
			Arguments.of("shift X", "CONTROL|ALT|META", ShortcutClass.NOT_MATCHING),
			Arguments.of("alt shift X", "NONE", ShortcutClass.MATCHING_MODIFIER),
			Arguments.of("alt shift X", "SHIFT", ShortcutClass.MATCHING_MODIFIER),
			Arguments.of("alt shift X", "ALT", ShortcutClass.MATCHING_MODIFIER),
			Arguments.of("alt shift X", "CONTROL", ShortcutClass.NOT_MATCHING),
			Arguments.of("alt shift X", "META", ShortcutClass.NOT_MATCHING),
			Arguments.of("alt shift X", "META|CONTROL", ShortcutClass.NOT_MATCHING),
			Arguments.of("alt shift X", "META|ALT", ShortcutClass.NOT_MATCHING),
			Arguments.of("alt shift X", "META|SHIFT", ShortcutClass.NOT_MATCHING),
			Arguments.of("alt shift X", "CONTROL|SHIFT", ShortcutClass.NOT_MATCHING),
			Arguments.of("alt shift X", "CONTROL|ALT", ShortcutClass.NOT_MATCHING),
			Arguments.of("alt shift X", "SHIFT|ALT", ShortcutClass.MATCHING_IMMEDIATE),
			Arguments.of("alt shift X", "SHIFT|ALT|CONTROL", ShortcutClass.NOT_MATCHING),
			Arguments.of("alt shift X", "SHIFT|ALT|META", ShortcutClass.NOT_MATCHING),
			Arguments.of("alt shift X", "SHIFT|ALT|CONTROL|META", ShortcutClass.NOT_MATCHING),
			Arguments.of("ctrl alt shift X", "NONE", ShortcutClass.MATCHING_MODIFIER),
			Arguments.of("ctrl alt shift X", "META", ShortcutClass.NOT_MATCHING),
			Arguments.of("ctrl alt shift X", "CONTROL", ShortcutClass.MATCHING_MODIFIER),
			Arguments.of("ctrl alt shift X", "ALT", ShortcutClass.MATCHING_MODIFIER),
			Arguments.of("ctrl alt shift X", "SHIFT", ShortcutClass.MATCHING_MODIFIER),
			Arguments.of("ctrl alt shift X", "ALT|SHIFT", ShortcutClass.MATCHING_MODIFIER),
			Arguments.of("ctrl alt shift X", "SHIFT|CONTROL", ShortcutClass.MATCHING_MODIFIER),
			Arguments.of("ctrl alt shift X", "ALT|CONTROL", ShortcutClass.MATCHING_MODIFIER),
			Arguments.of("ctrl alt shift X", "ALT|SHIFT|CONTROL", ShortcutClass.MATCHING_IMMEDIATE),
			Arguments.of("ctrl alt shift X", "META|CONTROL", ShortcutClass.NOT_MATCHING),
			Arguments.of("ctrl alt shift X", "META|ALT", ShortcutClass.NOT_MATCHING),
			Arguments.of("ctrl alt shift X", "META|SHIFT", ShortcutClass.NOT_MATCHING),
			Arguments.of("ctrl alt shift X", "SHIFT|ALT|CONTROL|META", ShortcutClass.NOT_MATCHING),
		)
	}
}
