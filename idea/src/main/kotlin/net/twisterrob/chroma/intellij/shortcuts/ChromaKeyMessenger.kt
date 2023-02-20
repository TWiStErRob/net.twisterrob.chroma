package net.twisterrob.chroma.intellij.shortcuts

import com.intellij.openapi.actionSystem.KeyboardShortcut
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.keymap.Keymap
import kotlinx.coroutines.runBlocking
import net.twisterrob.chroma.razer.ChromaColor
import net.twisterrob.chroma.razer.ChromaController
import net.twisterrob.chroma.razer.ChromaEffect
import net.twisterrob.chroma.razer.RZKEY
import java.awt.event.InputEvent
import java.awt.event.KeyEvent

private val LOG = logger<ChromaKeyMessenger>()

class ChromaKeyMessenger(
	private val controller: ChromaController,
	private val classifier: ShortcutClassifier = ShortcutClassifier(),
) {

	fun onKeyEvent(keymap: Keymap, e: KeyEvent) {
		LOG.debug("onKeyEvent(${keymap.name}, ${e.displayInfo}")
		val modifiers = e.modifiersEx

		if (false) {
			LOG.trace(keymap.actionIds.joinToString(separator = "\n") { action ->
				"${action}:\n${
					keymap.getShortcuts(action).joinToString(separator = "\n") { sc ->
						"\t${sc} -> ${classifier.classify(sc, modifiers)}"
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
			.filter { classifier.classify(it, modifiers) == ShortcutClass.MATCHING_IMMEDIATE }
			.map { it.firstKeyStroke.keyCode.toRZKey() }

		val otherModifierKeys = allShortcuts
			.filter { classifier.classify(it, modifiers) == ShortcutClass.MATCHING_MODIFIER }
			.map { it.firstKeyStroke.modifiers }
			.fold(0, Int::or)
			.and(modifiers.inv())
		
		display(getModifierKeys(modifiers), availableKeys, getModifierKeys(otherModifierKeys))
	}

	private fun getModifierKeys(modifiers: Int): Set<RZKEY> =
		if (modifiers == 0)
			emptySet()
		else
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

	private fun display(pressed: Set<RZKEY>, availableKeys: List<RZKEY>, modifierKeys: Set<RZKEY>) {
		runBlocking {
			controller.customKey(
				colors = ChromaEffect().apply {
					pressed.forEach { set(it, ChromaColor.YELLOW) }
					availableKeys.forEach { set(it, ChromaColor.GREEN) }
					modifierKeys.forEach { set(it, ChromaColor.CYAN) }
				}
			)
		}
	}
}
