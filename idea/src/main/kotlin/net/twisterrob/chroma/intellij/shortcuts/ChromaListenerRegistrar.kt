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
import com.intellij.openapi.diagnostic.logger

private val LOG = logger<ChromaListenerRegistrar>()

class ChromaListenerRegistrar : AppLifecycleListener, DynamicPluginListener, AnActionListener {

	override fun appFrameCreated(commandLineArgs: MutableList<String>) {
		LOG.debug("appFrameCreated(${commandLineArgs.joinToString()})")
//		ChromaService.getInstance().ensureStarted()
	}

	override fun appWillBeClosed(isRestart: Boolean) {
		LOG.debug("appWillBeClosed(${isRestart})")
//		ChromaService.getInstance().ensureStopped()
	}

	override fun pluginLoaded(pluginDescriptor: IdeaPluginDescriptor) {
		LOG.debug("pluginLoaded(${pluginDescriptor.pluginId})")
		if (pluginDescriptor.pluginId.idString == "net.twisterrob.chroma.intellij.shortcuts") {
//			ChromaService.getInstance().ensureStarted()
		}
	}
	
	override fun pluginUnloaded(pluginDescriptor: IdeaPluginDescriptor, isUpdate: Boolean) {
		LOG.debug("pluginUnloaded(${pluginDescriptor.pluginId}, ${isUpdate})")
		if (pluginDescriptor.pluginId.idString == "net.twisterrob.chroma.intellij.shortcuts") {
//			ChromaService.getInstance().ensureStopped()
		}
	}

	override fun beforeActionPerformed(action: AnAction, event: AnActionEvent) {
		LOG.debug("beforeActionPerformed(${action.javaClass.simpleName}, ${event.presentation.text})")
	}

	override fun afterActionPerformed(action: AnAction, event: AnActionEvent, result: AnActionResult) {
		LOG.debug("afterActionPerformed(${action.javaClass.simpleName}, ${event.presentation.text}, ${result})")
	}

	override fun beforeShortcutTriggered(shortcut: Shortcut, actions: MutableList<AnAction>, dataContext: DataContext) {
		LOG.debug("beforeShortcutTriggered(${shortcut}, ${actions.joinToString { it.javaClass.simpleName }}, ${dataContext})")
	}

	override fun beforeEditorTyping(c: Char, dataContext: DataContext) {
		LOG.debug("beforeEditorTyping(${c}, ${dataContext})")
	}

	override fun afterEditorTyping(c: Char, dataContext: DataContext) {
		LOG.debug("afterEditorTyping(${c}, ${dataContext})")
	}
}
