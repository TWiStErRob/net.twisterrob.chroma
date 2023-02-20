package net.twisterrob.chroma.intellij.shortcuts

import net.twisterrob.chroma.razer.RZKEY
import java.awt.event.KeyEvent

fun Int.toRZKey(): RZKEY =
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

		KeyEvent.VK_ESCAPE -> RZKEY.RZKEY_ESC
		KeyEvent.VK_BACK_SPACE -> RZKEY.RZKEY_BACKSPACE
		KeyEvent.VK_TAB -> RZKEY.RZKEY_TAB
		KeyEvent.VK_ENTER -> RZKEY.RZKEY_ENTER
		KeyEvent.VK_SPACE -> RZKEY.RZKEY_SPACE

		KeyEvent.VK_INSERT -> RZKEY.RZKEY_INSERT
		KeyEvent.VK_DELETE -> RZKEY.RZKEY_DELETE
		KeyEvent.VK_HOME -> RZKEY.RZKEY_HOME
		KeyEvent.VK_END -> RZKEY.RZKEY_END
		KeyEvent.VK_PAGE_UP -> RZKEY.RZKEY_PAGEUP
		KeyEvent.VK_PAGE_DOWN -> RZKEY.RZKEY_PAGEDOWN

		else -> RZKEY.RZKEY_INVALID
	}
