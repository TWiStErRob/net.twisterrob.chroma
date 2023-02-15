package net.twisterrob.chroma.intellij.shortcuts

import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.editor.EditorFactory
import com.intellij.openapi.editor.ex.EditorEventMulticasterEx
import com.intellij.openapi.editor.ex.FocusChangeListener
import com.intellij.util.xmlb.XmlSerializerUtil

private val LOG = logger<ChromaService>()

@State(name = "ChromaShortcuts", storages = [Storage("chroma-shortcuts.xml")])
class ChromaService : PersistentStateComponent<ChromaSettings>, Disposable {

	init {
		LOG.debug("init(${this})", Throwable("STACK TRACE"))
	}

	private val configuration = ChromaSettings()

	override fun getState(): ChromaSettings = configuration

	override fun loadState(state: ChromaSettings) {
		LOG.debug("loadState($state)")
		XmlSerializerUtil.copyBean(state, configuration)
	}

	fun isEnabled(): Boolean = configuration.isEnabled

	fun setEnabled(state: Boolean) {
		LOG.debug("setEnabled($state)")
		configuration.isEnabled = state
	}

	private val focusListener = object : FocusChangeListener {
		override fun focusGained(editor: Editor) {
			LOG.debug("Focus gained: ${editor.project?.name} - ${editor.headerComponent?.name}")
		}

		override fun focusLost(editor: Editor) {
			LOG.debug("Focus lost: ${editor.project?.name} - ${editor.headerComponent?.name}")
		}
	}

	fun ensureStarted() {
		//val multicaster = ToolWindowManager.getInstance(project)
		val multicaster = EditorFactory.getInstance().eventMulticaster
		if (multicaster is EditorEventMulticasterEx) {
			multicaster.addFocusChangeListener(focusListener, this)
		}
	}

	fun ensureStopped() {
	}

	override fun dispose() {
		LOG.debug("${this} dispose()")
	}

	companion object {

		fun getInstance(): ChromaService =
			ApplicationManager.getApplication().getService(ChromaService::class.java)
	}
}
