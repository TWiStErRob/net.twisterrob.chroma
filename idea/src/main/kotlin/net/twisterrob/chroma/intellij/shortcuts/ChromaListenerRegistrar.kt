package net.twisterrob.chroma.intellij.shortcuts

import com.intellij.ide.AppLifecycleListener
import com.intellij.ide.plugins.DynamicPluginListener
import com.intellij.ide.plugins.IdeaPluginDescriptor

class ChromaListenerRegistrar : AppLifecycleListener, DynamicPluginListener {

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
			ChromaService.getInstance().ensureStopped()
		}
	}
}
