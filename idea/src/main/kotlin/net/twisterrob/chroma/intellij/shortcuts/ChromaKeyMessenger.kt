package net.twisterrob.chroma.intellij.shortcuts

import com.intellij.openapi.actionSystem.KeyboardShortcut
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.keymap.Keymap
import java.awt.event.KeyEvent

private val LOG = logger<ChromaKeyMessenger>()

class ChromaKeyMessenger {

	fun onKeyEvent(keymap: Keymap, e: KeyEvent) {
		LOG.debug("onKeyEvent(${keymap.name}, ${e.displayInfo}")
		LOG.trace("actions: ${keymap.actionIds.toList()}")

		val allShortcuts = keymap
			.actionIds
			.flatMap { keymap.getShortcuts(it).toList() }

		LOG.trace("all shortcuts: $allShortcuts")

		val shortcuts = allShortcuts
			.filter { it.isKeyboard }
			.map { it as KeyboardShortcut }
			.filter { (it.firstKeyStroke.modifiers and e.modifiersEx) != 0 }

		LOG.trace("shortcuts: $shortcuts")
	}
}
