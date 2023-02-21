package net.twisterrob.chroma.intellij.shortcuts

import com.intellij.ide.AppLifecycleListener
import com.intellij.openapi.diagnostic.logger

private val LOG = logger<ChromaListenerRegistrar>()

class ChromaListenerRegistrar : AppLifecycleListener {

	override fun appFrameCreated(commandLineArgs: MutableList<String>) {
		LOG.trace("appFrameCreated(${commandLineArgs.joinToString()})")
		ChromaService.getInstance().ensureStarted()
	}

	override fun appWillBeClosed(isRestart: Boolean) {
		LOG.trace("appWillBeClosed(${isRestart})")
		ChromaService.getInstance().ensureStopped()
	}
}
