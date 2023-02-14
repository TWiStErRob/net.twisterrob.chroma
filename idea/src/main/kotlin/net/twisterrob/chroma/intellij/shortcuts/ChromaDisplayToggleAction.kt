package net.twisterrob.chroma.intellij.shortcuts

import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.ToggleAction
import com.intellij.openapi.project.DumbAware

class ChromaDisplayToggleAction : ToggleAction("Display Chroma Shortcuts on Keyboard"), DumbAware {

	override fun isSelected(e: AnActionEvent): Boolean =
		ChromaService.getInstance().isEnabled()

	override fun setSelected(e: AnActionEvent, state: Boolean) {
		ChromaService.getInstance().setEnabled(state)
	}
}
