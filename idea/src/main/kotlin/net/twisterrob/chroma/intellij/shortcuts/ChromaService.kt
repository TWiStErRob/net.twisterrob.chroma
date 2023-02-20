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
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.WindowManager
import com.intellij.util.xmlb.XmlSerializerUtil
import java.awt.KeyboardFocusManager
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent

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

	var isEnabled: Boolean by configuration::isEnabled

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

	fun projectOpened(project: Project) {
		val manager = KeyboardFocusManager.getCurrentKeyboardFocusManager()
		manager.addKeyEventDispatcher { e ->
			LOG.debug("addKeyEventDispatcher(${e.displayInfo})")
			false
		}
		manager.addKeyEventPostProcessor { e ->
			LOG.debug("addKeyEventPostProcessor(${e.displayInfo})")
			false
		}
		if (ApplicationManager.getApplication().isHeadlessEnvironment) {
			LOG.warn("Headless environment, skipping projectOpened($project)")
			return
		}
		val frame = WindowManager.getInstance().getFrame(project)!!
		frame.addKeyListener(object : KeyAdapter() {
			override fun keyPressed(e: KeyEvent) {
				LOG.debug("Key pressed: ${e.displayInfo}")
			}

			override fun keyReleased(e: KeyEvent) {
				LOG.debug("Key released: ${e.displayInfo}")
			}
		})
	}

	companion object {

		fun getInstance(): ChromaService =
			ApplicationManager.getApplication().getService(ChromaService::class.java)
	}
}

private val KeyEvent.displayInfo: String
	get() {
		//You should only rely on the key char if the event is a key typed event.
		val keyString: String = when (this.id) {
			KeyEvent.KEY_TYPED -> "key character = '${this.keyChar}'"
			else -> "key code = ${this.keyCode} (${KeyEvent.getKeyText(this.keyCode)})"
		}
		val tmpString = KeyEvent.getModifiersExText(this.modifiersEx)
		val modString = "extended modifiers = ${this.modifiersEx}" + if (tmpString.isNotEmpty()) {
			" ($tmpString)"
		} else {
			" (no extended modifiers)"
		}
		val actionString = "action key? " + if (isActionKey) {
			"YES"
		} else {
			"NO"
		}
		val locationString = "key location: " + when (keyLocation) {
			KeyEvent.KEY_LOCATION_STANDARD -> "standard"
			KeyEvent.KEY_LOCATION_LEFT -> "left"
			KeyEvent.KEY_LOCATION_RIGHT -> "right"
			KeyEvent.KEY_LOCATION_NUMPAD -> "numpad"
			else -> "unknown" // (location == KeyEvent.KEY_LOCATION_UNKNOWN)
		}
		return "$keyString $modString $actionString $locationString"
	}
