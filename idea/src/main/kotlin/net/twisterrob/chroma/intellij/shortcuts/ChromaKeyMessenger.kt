package net.twisterrob.chroma.intellij.shortcuts

import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.keymap.Keymap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import net.twisterrob.chroma.intellij.shortcuts.classification.ClassifiedShortcuts
import net.twisterrob.chroma.intellij.shortcuts.classification.ShortcutClassifier
import net.twisterrob.chroma.intellij.shortcuts.classification.toRZKey
import net.twisterrob.chroma.razer.ChromaColor
import net.twisterrob.chroma.razer.ChromaController
import net.twisterrob.chroma.razer.ChromaEffect
import net.twisterrob.chroma.razer.RZKEY
import java.awt.event.KeyEvent

private val LOG = logger<ChromaKeyMessenger>()

class ChromaKeyMessenger(
	private val scope: CoroutineScope,
	private val controller: ChromaController,
	private val classifier: ShortcutClassifier = ShortcutClassifier(),
) {

	fun onKeyEvent(keymap: Keymap, e: KeyEvent) {
		LOG.trace("onKeyEvent(${keymap.name}, ${e.paramString()}")
		val highlight = e.keyCode.toRZKey(e.keyLocation)
		val modifiers = e.modifiersEx
		scope.launch {
			val classified = classifier.classify(keymap, modifiers)
			controller.customKey(colors = render(classified, highlight))
		}
	}

	private fun render(classified: ClassifiedShortcuts, highlight: RZKEY): ChromaEffect =
		ChromaEffect().apply {
			classified.depressed.forEach { set(it, ChromaColor.YELLOW) }
			classified.shortcutTerminator.forEach { set(it, ChromaColor.GREEN) }
			classified.additionalModifiers.forEach { set(it, ChromaColor.CYAN) }
			set(highlight, ChromaColor.RED)
		}
}
