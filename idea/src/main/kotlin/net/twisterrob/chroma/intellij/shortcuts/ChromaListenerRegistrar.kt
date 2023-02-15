package net.twisterrob.chroma.intellij.shortcuts

import com.intellij.ide.AppLifecycleListener
import com.intellij.ide.plugins.DynamicPluginListener
import com.intellij.ide.plugins.IdeaPluginDescriptor
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.AnActionResult
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.actionSystem.Shortcut
import com.intellij.openapi.actionSystem.ex.AnActionListener

class ChromaListenerRegistrar : AppLifecycleListener, DynamicPluginListener, AnActionListener {

	override fun appFrameCreated(commandLineArgs: MutableList<String>) {
		ChromaService.getInstance().ensureStarted()
	}

	override fun appWillBeClosed(isRestart: Boolean) {
		ChromaService.getInstance().ensureStopped()
	}

	override fun pluginLoaded(pluginDescriptor: IdeaPluginDescriptor) {
		if (pluginDescriptor.pluginId.idString == "net.twisterrob.chroma.intellij.shortcuts") {
			ChromaService.getInstance().ensureStarted()
		}
	}

	override fun pluginUnloaded(pluginDescriptor: IdeaPluginDescriptor, isUpdate: Boolean) {
		if (pluginDescriptor.pluginId.idString == "net.twisterrob.chroma.intellij.shortcuts") {
//			ChromaService.getInstance().ensureStopped()
		}
	}

	override fun beforeActionPerformed(action: AnAction, event: AnActionEvent) {
	}

	override fun afterActionPerformed(action: AnAction, event: AnActionEvent, result: AnActionResult) {
	}

	override fun beforeShortcutTriggered(shortcut: Shortcut, actions: MutableList<AnAction>, dataContext: DataContext) {
	}

	override fun beforeEditorTyping(c: Char, dataContext: DataContext) {
	}

	override fun afterEditorTyping(c: Char, dataContext: DataContext) {
	}
}
