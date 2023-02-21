package net.twisterrob.chroma.intellij.shortcuts.classification

import com.intellij.openapi.actionSystem.KeyboardShortcut
import com.intellij.openapi.actionSystem.Shortcut
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.keymap.Keymap
import net.twisterrob.chroma.intellij.shortcuts.classification.ClassifiedShortcuts
import net.twisterrob.chroma.razer.RZKEY
import java.awt.event.InputEvent
import java.awt.event.KeyEvent

private val LOG = logger<ShortcutClassifier>()

class ShortcutClassifier {

	fun classify(keymap: Keymap, modifiers: Int): ClassifiedShortcuts {
		if (false) {
			LOG.trace(keymap.actionIds.joinToString(separator = "\n") { action ->
				"${action}:\n${
					keymap.getShortcuts(action).joinToString(separator = "\n") { sc ->
						"\t${sc} -> ${classify(sc, modifiers)}"
					}
				}"
			})
		}

		val allShortcuts = keymap
			.actionIds
			.flatMap { keymap.getShortcuts(it).toList() }
			.filter { it.isKeyboard }
			.map { it as KeyboardShortcut }

		val availableKeys = allShortcuts
			.filter { classify(it, modifiers) == ShortcutClass.MATCHING_IMMEDIATE }
			.map { it.firstKeyStroke.keyCode.toRZKey() }
			.toSet()

		val otherModifierKeys = allShortcuts
			.filter { classify(it, modifiers) == ShortcutClass.MATCHING_MODIFIER }
			.map { it.firstKeyStroke.modifiers }
			.fold(0, Int::or)
			.and(modifiers.inv())

		return ClassifiedShortcuts(mapModifierKeys(modifiers), availableKeys, mapModifierKeys(otherModifierKeys))
	}

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

	fun mapModifierKeys(modifiers: Int): Set<RZKEY> =
		buildSet {
			if (modifiers and InputEvent.META_DOWN_MASK != 0) {
				add(RZKEY.RZKEY_LWIN)
			}
			if (modifiers and InputEvent.CTRL_DOWN_MASK != 0) {
				add(RZKEY.RZKEY_LCTRL)
				add(RZKEY.RZKEY_RCTRL)
			}
			if (modifiers and InputEvent.ALT_DOWN_MASK != 0) {
				add(RZKEY.RZKEY_LALT)
				add(RZKEY.RZKEY_RALT)
			}
			if (modifiers and InputEvent.SHIFT_DOWN_MASK != 0) {
				add(RZKEY.RZKEY_LSHIFT)
				add(RZKEY.RZKEY_RSHIFT)
			}
			if (modifiers and InputEvent.ALT_GRAPH_DOWN_MASK != 0) {
				add(RZKEY.RZKEY_LALT)
				add(RZKEY.RZKEY_RALT)
			}
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
