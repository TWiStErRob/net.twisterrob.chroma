package net.twisterrob.chroma.intellij.shortcuts

import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.keymap.KeymapManager
import com.intellij.util.xmlb.XmlSerializerUtil
import java.awt.KeyboardFocusManager
import java.awt.event.KeyEvent

private val LOG = logger<ChromaService>()

@State(name = "ChromaShortcuts", storages = [Storage("chroma-shortcuts.xml")])
class ChromaService : PersistentStateComponent<ChromaSettings>, Disposable {

	private val configuration = ChromaSettings()

	private val messenger = ChromaKeyMessenger()

	@Volatile
	private var started: Boolean = false

	init {
		LOG.debug("init(${this})", Throwable("STACK TRACE"))
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher { e ->
			// https://stackoverflow.com/a/4443491/253468
			LOG.debug("dispatchKeyEvent(${e.displayInfo})")
			if (isEnabled && started) {
				dispatchEvent(e)
			} else {
				LOG.trace("dispatchKeyEvent ignored (isEnabled=$isEnabled, started=$started)")
			}
			false
		}
	}

	override fun getState(): ChromaSettings = configuration

	override fun loadState(state: ChromaSettings) {
		LOG.debug("loadState($state)")
		XmlSerializerUtil.copyBean(state, configuration)
	}

	var isEnabled: Boolean
		get() {
			LOG.debug("isEnabled (${configuration.isEnabled})")
			return configuration.isEnabled
		}
		set(value) {
			LOG.debug("isEnabled = ${value}")
			configuration.isEnabled = value
		}

	fun ensureStarted() {
		started = true
	}

	private fun dispatchEvent(e: KeyEvent) {
		val keymap = KeymapManager.getInstance().activeKeymap
		messenger.onKeyEvent(keymap, e)
	}

	fun ensureStopped() {
		started = false
	}

	override fun dispose() {
		LOG.debug("${this} dispose()")
	}

	companion object {

		fun getInstance(): ChromaService =
			ApplicationManager.getApplication().getService(ChromaService::class.java)
	}
}
