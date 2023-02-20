package net.twisterrob.chroma.intellij.shortcuts

import com.intellij.openapi.actionSystem.KeyboardShortcut
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.keymap.Keymap
import kotlinx.coroutines.runBlocking
import net.twisterrob.chroma.razer.ChromaColor
import net.twisterrob.chroma.razer.ChromaController
import net.twisterrob.chroma.razer.ChromaEffect
import java.awt.event.KeyEvent

private val LOG = logger<ChromaKeyMessenger>()

class ChromaKeyMessenger(
	private val controller: ChromaController
) {

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
			.filter { it.firstKeyStroke.modifiers == e.modifiersEx }

		LOG.trace("shortcuts: $shortcuts")

		runBlocking {
			controller.customKey(
				colors = ChromaEffect().apply {
					shortcuts.forEach {
						val key = it.firstKeyStroke.keyCode.toRZKey()
						set(key, ChromaColor.GREEN)
					}
				}
			)
		}
	}
}
