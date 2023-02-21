package net.twisterrob.chroma.intellij.shortcuts.classification

import net.twisterrob.chroma.razer.RZKEY
import java.awt.event.KeyEvent

fun Int.toRZKey(keyLocation: Int = KeyEvent.KEY_LOCATION_UNKNOWN): RZKEY =
	when (this) {
		KeyEvent.VK_0 -> RZKEY.RZKEY_0
		KeyEvent.VK_1 -> RZKEY.RZKEY_1
		KeyEvent.VK_2 -> RZKEY.RZKEY_2
		KeyEvent.VK_3 -> RZKEY.RZKEY_3
		KeyEvent.VK_4 -> RZKEY.RZKEY_4
		KeyEvent.VK_5 -> RZKEY.RZKEY_5
		KeyEvent.VK_6 -> RZKEY.RZKEY_6
		KeyEvent.VK_7 -> RZKEY.RZKEY_7
		KeyEvent.VK_8 -> RZKEY.RZKEY_8
		KeyEvent.VK_9 -> RZKEY.RZKEY_9

		KeyEvent.VK_A -> RZKEY.RZKEY_A
		KeyEvent.VK_B -> RZKEY.RZKEY_B
		KeyEvent.VK_C -> RZKEY.RZKEY_C
		KeyEvent.VK_D -> RZKEY.RZKEY_D
		KeyEvent.VK_E -> RZKEY.RZKEY_E
		KeyEvent.VK_F -> RZKEY.RZKEY_F
		KeyEvent.VK_G -> RZKEY.RZKEY_G
		KeyEvent.VK_H -> RZKEY.RZKEY_H
		KeyEvent.VK_I -> RZKEY.RZKEY_I
		KeyEvent.VK_J -> RZKEY.RZKEY_J
		KeyEvent.VK_K -> RZKEY.RZKEY_K
		KeyEvent.VK_L -> RZKEY.RZKEY_L
		KeyEvent.VK_M -> RZKEY.RZKEY_M
		KeyEvent.VK_N -> RZKEY.RZKEY_N
		KeyEvent.VK_O -> RZKEY.RZKEY_O
		KeyEvent.VK_P -> RZKEY.RZKEY_P
		KeyEvent.VK_Q -> RZKEY.RZKEY_Q
		KeyEvent.VK_R -> RZKEY.RZKEY_R
		KeyEvent.VK_S -> RZKEY.RZKEY_S
		KeyEvent.VK_T -> RZKEY.RZKEY_T
		KeyEvent.VK_U -> RZKEY.RZKEY_U
		KeyEvent.VK_V -> RZKEY.RZKEY_V
		KeyEvent.VK_W -> RZKEY.RZKEY_W
		KeyEvent.VK_X -> RZKEY.RZKEY_X
		KeyEvent.VK_Y -> RZKEY.RZKEY_Y
		KeyEvent.VK_Z -> RZKEY.RZKEY_Z

		KeyEvent.VK_BACK_QUOTE -> RZKEY.RZKEY_OEM_1
		KeyEvent.VK_MINUS -> RZKEY.RZKEY_OEM_2
		KeyEvent.VK_EQUALS -> RZKEY.RZKEY_OEM_3
		KeyEvent.VK_OPEN_BRACKET -> RZKEY.RZKEY_OEM_4
		KeyEvent.VK_CLOSE_BRACKET -> RZKEY.RZKEY_OEM_5
		// In US Layout these are indistinguishable, they all emit BACK_SLASH.
		//KeyEvent.VK_BACK_SLASH -> RZKEY.RZKEY_OEM_6 // Officially, but OEM_6 is under the top left corner of Enter.
		KeyEvent.VK_BACK_SLASH -> RZKEY.RZKEY_EUR_1 // left of enter
		//KeyEvent.VK_BACK_SLASH -> RZKEY.RZKEY_EUR_2 // right of left shift
		KeyEvent.VK_SEMICOLON -> RZKEY.RZKEY_OEM_7
		KeyEvent.VK_QUOTE -> RZKEY.RZKEY_OEM_8
		KeyEvent.VK_COMMA -> RZKEY.RZKEY_OEM_9
		KeyEvent.VK_PERIOD -> RZKEY.RZKEY_OEM_10
		KeyEvent.VK_SLASH -> RZKEY.RZKEY_OEM_11

		KeyEvent.VK_NUM_LOCK -> RZKEY.RZKEY_NUMLOCK
		KeyEvent.VK_NUMPAD0 -> RZKEY.RZKEY_NUMPAD0
		KeyEvent.VK_NUMPAD1 -> RZKEY.RZKEY_NUMPAD1
		KeyEvent.VK_NUMPAD2 -> RZKEY.RZKEY_NUMPAD2
		KeyEvent.VK_NUMPAD3 -> RZKEY.RZKEY_NUMPAD3
		KeyEvent.VK_NUMPAD4 -> RZKEY.RZKEY_NUMPAD4
		KeyEvent.VK_NUMPAD5 -> RZKEY.RZKEY_NUMPAD5
		KeyEvent.VK_NUMPAD6 -> RZKEY.RZKEY_NUMPAD6
		KeyEvent.VK_NUMPAD7 -> RZKEY.RZKEY_NUMPAD7
		KeyEvent.VK_NUMPAD8 -> RZKEY.RZKEY_NUMPAD8
		KeyEvent.VK_NUMPAD9 -> RZKEY.RZKEY_NUMPAD9
		KeyEvent.VK_CLEAR -> RZKEY.RZKEY_NUMPAD5
		KeyEvent.VK_KP_UP -> RZKEY.RZKEY_NUMPAD8
		KeyEvent.VK_KP_DOWN -> RZKEY.RZKEY_NUMPAD2
		KeyEvent.VK_KP_LEFT -> RZKEY.RZKEY_NUMPAD4
		KeyEvent.VK_KP_RIGHT -> RZKEY.RZKEY_NUMPAD6
		KeyEvent.VK_DIVIDE -> RZKEY.RZKEY_NUMPAD_DIVIDE
		KeyEvent.VK_MULTIPLY -> RZKEY.RZKEY_NUMPAD_MULTIPLY
		KeyEvent.VK_SUBTRACT -> RZKEY.RZKEY_NUMPAD_SUBTRACT
		KeyEvent.VK_ADD -> RZKEY.RZKEY_NUMPAD_ADD
		//KeyEvent.VK_ENTER -> RZKEY.RZKEY_NUMPAD_ENTER
		KeyEvent.VK_DECIMAL -> RZKEY.RZKEY_NUMPAD_DECIMAL
		// https://stackoverflow.com/a/68703403/253468
		KeyEvent.VK_SEPARATOR -> RZKEY.RZKEY_NUMPAD_ADD

		KeyEvent.VK_ESCAPE -> RZKEY.RZKEY_ESC
		KeyEvent.VK_F1 -> RZKEY.RZKEY_F1
		KeyEvent.VK_F2 -> RZKEY.RZKEY_F2
		KeyEvent.VK_F3 -> RZKEY.RZKEY_F3
		KeyEvent.VK_F4 -> RZKEY.RZKEY_F4
		KeyEvent.VK_F5 -> RZKEY.RZKEY_F5
		KeyEvent.VK_F6 -> RZKEY.RZKEY_F6
		KeyEvent.VK_F7 -> RZKEY.RZKEY_F7
		KeyEvent.VK_F8 -> RZKEY.RZKEY_F8
		KeyEvent.VK_F9 -> RZKEY.RZKEY_F9
		KeyEvent.VK_F10 -> RZKEY.RZKEY_F10
		KeyEvent.VK_F11 -> RZKEY.RZKEY_F11
		KeyEvent.VK_F12 -> RZKEY.RZKEY_F12

		KeyEvent.VK_BACK_SPACE -> RZKEY.RZKEY_BACKSPACE
		KeyEvent.VK_TAB -> RZKEY.RZKEY_TAB
		KeyEvent.VK_CAPS_LOCK -> RZKEY.RZKEY_CAPSLOCK
		KeyEvent.VK_ENTER -> when (keyLocation) {
			KeyEvent.KEY_LOCATION_NUMPAD -> RZKEY.RZKEY_NUMPAD_ENTER
			KeyEvent.KEY_LOCATION_STANDARD -> RZKEY.RZKEY_ENTER
			else -> RZKEY.RZKEY_INVALID
		}

		KeyEvent.VK_SPACE -> RZKEY.RZKEY_SPACE

		KeyEvent.VK_SHIFT -> when (keyLocation) {
			KeyEvent.KEY_LOCATION_LEFT -> RZKEY.RZKEY_LSHIFT
			KeyEvent.KEY_LOCATION_RIGHT -> RZKEY.RZKEY_RSHIFT
			else -> RZKEY.RZKEY_INVALID
		}

		KeyEvent.VK_CONTROL -> when (keyLocation) {
			KeyEvent.KEY_LOCATION_LEFT -> RZKEY.RZKEY_LCTRL
			KeyEvent.KEY_LOCATION_RIGHT -> RZKEY.RZKEY_RCTRL
			else -> RZKEY.RZKEY_INVALID
		}

		KeyEvent.VK_ALT -> when (keyLocation) {
			KeyEvent.KEY_LOCATION_LEFT -> RZKEY.RZKEY_LALT
			KeyEvent.KEY_LOCATION_RIGHT -> RZKEY.RZKEY_RALT
			else -> RZKEY.RZKEY_INVALID
		}

		KeyEvent.VK_WINDOWS -> when (keyLocation) {
			KeyEvent.KEY_LOCATION_LEFT -> RZKEY.RZKEY_LWIN
			KeyEvent.KEY_LOCATION_RIGHT -> RZKEY.RZKEY_FN
			else -> RZKEY.RZKEY_INVALID
		}

		KeyEvent.VK_CONTEXT_MENU -> RZKEY.RZKEY_RMENU
		KeyEvent.VK_PRINTSCREEN -> RZKEY.RZKEY_PRINTSCREEN
		KeyEvent.VK_SCROLL_LOCK -> RZKEY.RZKEY_SCROLL
		KeyEvent.VK_PAUSE -> RZKEY.RZKEY_PAUSE

		KeyEvent.VK_INSERT -> when (keyLocation) {
			KeyEvent.KEY_LOCATION_NUMPAD -> RZKEY.RZKEY_NUMPAD0
			else -> RZKEY.RZKEY_INSERT
		}

		KeyEvent.VK_DELETE -> when (keyLocation) {
			KeyEvent.KEY_LOCATION_NUMPAD -> RZKEY.RZKEY_NUMPAD_DECIMAL
			else -> RZKEY.RZKEY_DELETE
		}

		KeyEvent.VK_HOME -> when (keyLocation) {
			KeyEvent.KEY_LOCATION_NUMPAD -> RZKEY.RZKEY_NUMPAD7
			else -> RZKEY.RZKEY_HOME
		}

		KeyEvent.VK_END -> when (keyLocation) {
			KeyEvent.KEY_LOCATION_NUMPAD -> RZKEY.RZKEY_NUMPAD1
			else -> RZKEY.RZKEY_END
		}

		KeyEvent.VK_PAGE_UP -> when (keyLocation) {
			KeyEvent.KEY_LOCATION_NUMPAD -> RZKEY.RZKEY_NUMPAD9
			else -> RZKEY.RZKEY_PAGEUP
		}

		KeyEvent.VK_PAGE_DOWN -> when (keyLocation) {
			KeyEvent.KEY_LOCATION_NUMPAD -> RZKEY.RZKEY_NUMPAD3
			else -> RZKEY.RZKEY_PAGEDOWN
		}

		KeyEvent.VK_UP -> when (keyLocation) {
			KeyEvent.KEY_LOCATION_NUMPAD -> RZKEY.RZKEY_NUMPAD8
			else -> RZKEY.RZKEY_UP
		}

		KeyEvent.VK_DOWN -> when (keyLocation) {
			KeyEvent.KEY_LOCATION_NUMPAD -> RZKEY.RZKEY_NUMPAD2
			else -> RZKEY.RZKEY_DOWN
		}

		KeyEvent.VK_LEFT -> when (keyLocation) {
			KeyEvent.KEY_LOCATION_NUMPAD -> RZKEY.RZKEY_NUMPAD4
			else -> RZKEY.RZKEY_LEFT
		}

		KeyEvent.VK_RIGHT -> when (keyLocation) {
			KeyEvent.KEY_LOCATION_NUMPAD -> RZKEY.RZKEY_NUMPAD6
			else -> RZKEY.RZKEY_RIGHT
		}

		else -> RZKEY.RZKEY_INVALID
	}
