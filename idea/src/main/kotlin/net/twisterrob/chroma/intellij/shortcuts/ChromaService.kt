package net.twisterrob.chroma.intellij.shortcuts

import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil

@State(name = "ChromaShortcuts", storages = [Storage("chroma-shortcuts.xml")])
class ChromaService : PersistentStateComponent<ChromaSettings>, Disposable {

	private val configuration = ChromaSettings()

	override fun getState(): ChromaSettings = configuration

	override fun loadState(state: ChromaSettings) {
		XmlSerializerUtil.copyBean(state, configuration)
	}

	fun isEnabled(): Boolean = configuration.isEnabled

	fun setEnabled(state: Boolean) {
		configuration.isEnabled = state
	}

	fun ensureStarted() {
		TODO("not implemented")
	}

	fun ensureStopped() {
		TODO("not implemented")
	}

	override fun dispose() {
		TODO("not implemented")
	}

	companion object {

		fun getInstance(): ChromaService =
			ApplicationManager.getApplication().getService(ChromaService::class.java)
	}
}
