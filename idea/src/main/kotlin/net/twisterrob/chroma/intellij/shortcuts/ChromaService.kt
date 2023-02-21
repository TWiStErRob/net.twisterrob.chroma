package net.twisterrob.chroma.intellij.shortcuts

import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.diagnostic.logger
import com.intellij.openapi.keymap.KeymapManager
import com.intellij.util.xmlb.XmlSerializerUtil
import kotlinx.coroutines.launch
import net.twisterrob.chroma.intellij.shortcuts.utils.RestartableScope
import net.twisterrob.chroma.razer.ChromaController
import java.awt.KeyboardFocusManager
import java.awt.event.KeyEvent

private val LOG = logger<ChromaService>()

@State(name = "ChromaShortcuts", storages = [Storage("chroma-shortcuts.xml")])
class ChromaService : PersistentStateComponent<ChromaSettings>, Disposable {

	private val configuration = ChromaSettings()
	private val scope = RestartableScope()
	private val controller = ChromaController(scope)
	private val messenger = ChromaKeyMessenger(scope, controller)

	@Volatile
	private var started: Boolean = false

	init {
		// Note: there's no remove, so this will leak ChromaService if the plugin is unloaded.
		// https://stackoverflow.com/a/4443491/253468
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher { dispatchEvent(it); false }
	}

	override fun getState(): ChromaSettings = configuration

	override fun loadState(state: ChromaSettings) {
		XmlSerializerUtil.copyBean(state, configuration)
	}

	var isEnabled: Boolean by configuration::isEnabled
	
	fun ensureStarted() {
		if (!isEnabled) return
		if (!started) {
			scope.start()
			scope.launch {
				controller.start()
				controller.none()
			}
		}
		started = true
	}

	private fun dispatchEvent(e: KeyEvent) {
		if (isEnabled && started) {
			val keymap = KeymapManager.getInstance().activeKeymap
			messenger.onKeyEvent(keymap, e)
		} else {
			LOG.debug("dispatchKeyEvent ignored (isEnabled=$isEnabled, started=$started)")
		}
	}

	fun ensureStopped() {
		if (started) {
			controller.stop()
			scope.stop()
		}
		started = false
	}

	override fun dispose() {
		started = false
		controller.close()
		scope.dispose()
	}

	companion object {

		fun getInstance(): ChromaService =
			ApplicationManager.getApplication().getService(ChromaService::class.java)
	}
}
