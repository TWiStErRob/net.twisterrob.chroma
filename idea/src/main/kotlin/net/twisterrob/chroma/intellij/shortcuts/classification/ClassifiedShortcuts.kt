package net.twisterrob.chroma.intellij.shortcuts.classification

import net.twisterrob.chroma.razer.RZKEY

class ClassifiedShortcuts(
	val depressed: Set<RZKEY>,
	val shortcutTerminator: Set<RZKEY>,
	val additionalModifiers: Set<RZKEY>,
) {

	init {
		val all = depressed + shortcutTerminator + additionalModifiers
		require(all.size == depressed.size + shortcutTerminator.size + additionalModifiers.size) {
			"Duplicate keys in ${depressed} + ${shortcutTerminator} + ${additionalModifiers}"
		}
	}
}
