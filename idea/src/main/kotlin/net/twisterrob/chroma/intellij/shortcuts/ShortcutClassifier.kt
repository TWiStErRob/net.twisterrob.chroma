package net.twisterrob.chroma.intellij.shortcuts

import com.intellij.openapi.actionSystem.KeyboardShortcut
import com.intellij.openapi.actionSystem.Shortcut
import java.awt.event.KeyEvent

class ShortcutClassifier {

	/**
	 * @param modifiers see [KeyEvent.getModifiersEx]
	 */
	fun classify(shortcut: Shortcut, modifiers: Int): ShortcutClass =
		when (shortcut) {
			is KeyboardShortcut -> {
				val filteredShortcutModifiers = shortcut.firstKeyStroke.modifiers.filterDeprecatedMasks()
				val filteredModifiers = modifiers.filterDeprecatedMasks()
				// | shortcut   | keyboard       | diff           | result
				// | ---------- | -------------- | -------------- | -------
				// | ctrl+shift | shift          | ctrl           | MODIFIER
				// | ctrl+shift | ctrl+shift     | 0              | IMMEDIATE
				// | ctrl+shift | ctrl+shift+alt | alt            | NOT_MATCHING (too many)
				// | ctrl+shift | ctrl+alt       | shift+alt      | NOT_MATCHING (overlapping)
				// | ctrl+shift | alt            | ctrl+shift+alt | NOT_MATCHING (different)
				val diff = filteredShortcutModifiers xor filteredModifiers
				if (diff == 0) {
					ShortcutClass.MATCHING_IMMEDIATE
				} else if ((diff or filteredModifiers) == filteredShortcutModifiers) {
					ShortcutClass.MATCHING_MODIFIER
				} else {
					ShortcutClass.NOT_MATCHING
				}
			}

			else -> ShortcutClass.NOT_MATCHING
		}

	companion object {

		@Suppress("DEPRECATION")
		private val DEPRECATED_MASKS =
			KeyEvent.SHIFT_MASK or KeyEvent.ALT_MASK or KeyEvent.CTRL_MASK or KeyEvent.META_MASK

		private fun Int.filterDeprecatedMasks(): Int =
			this and DEPRECATED_MASKS.inv()
	}
}

enum class ShortcutClass {
	NOT_MATCHING,
	MATCHING_IMMEDIATE,
	MATCHING_MODIFIER,
}
