package net.twisterrob.chroma

import kotlinx.coroutines.delay
import net.twisterrob.chroma.razer.ChromaColor
import net.twisterrob.chroma.razer.ChromaController
import net.twisterrob.chroma.razer.ChromaEffect
import net.twisterrob.chroma.razer.RZKEY
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.SwingUtilities
import kotlin.time.Duration.Companion.milliseconds

suspend fun main() {
	ChromaController().use { controller ->
		controller.start()
		var direction: Pair<Int, Int>?
		System.setProperty("java.awt.headless", "false")
		SwingUtilities.invokeLater {
			JFrame("Snake").apply {
				defaultCloseOperation = JFrame.EXIT_ON_CLOSE
				add(
					JLabel(
						"""
							Press WASD or arrow keys to move, Q to exit. 
							Keep the window focused, display on the keyboard.
						""".trimIndent()
					)
				)
				pack()
				setLocationRelativeTo(null)
				isVisible = true
				forceFocus()
				addKeyListener(object : KeyAdapter() {
					override fun keyPressed(e: KeyEvent) {
						when (e.keyCode) {
							KeyEvent.VK_LEFT, KeyEvent.VK_A -> direction = Pair(0, -1)
							KeyEvent.VK_RIGHT, KeyEvent.VK_D -> direction = Pair(0, 1)
							KeyEvent.VK_UP, KeyEvent.VK_W -> direction = Pair(-1, 0)
							KeyEvent.VK_DOWN, KeyEvent.VK_S -> direction = Pair(1, 0)

							KeyEvent.VK_ESCAPE, KeyEvent.VK_Q -> {
								direction = null
								dispose()
							}
						}
					}
				})
			}
		}
		while (true) {
			controller.none()
			val snake = ArrayDeque(listOf(Pair(0, 0), Pair(0, 1), Pair(0, 2), Pair(0, 3), Pair(0, 4)))
			direction = Pair(0, 1)
			var apple = Pair(3, 8)
			var speed = 500
			var last = System.currentTimeMillis() - speed
			@Suppress("LoopWithTooManyJumpStatements")
			while (snake.toSet().size == snake.size) {
				if (System.currentTimeMillis() - last < speed) continue
				@Suppress("UNUSED_VALUE") // False positive, `last` is used in next cycle.
				last = System.currentTimeMillis()
				print(".")
				controller.customKey(ChromaEffect().apply {
					snake.forEachIndexed { index, part ->
						val comp = ((index.toDouble() / (snake.size - 1).toDouble()) * 0xdf + 0x20).toInt()
						val c = (comp shl 16) or (comp shl 8) or (comp shl 0)
						this[part.first, part.second] = ChromaColor(c)
					}
					this[apple.first, apple.second] = ChromaColor.RED
				})
				val dir = direction ?: return
				val step = Pair(snake.last().first + dir.first, snake.last().second + dir.second)
				val next = Pair((step.first + 6) % 6, (step.second + 22) % 22)
				if (next == apple) {
					do {
						apple = Pair((Math.random() * 6).toInt(), (Math.random() * 22).toInt())
					} while (snake.contains(apple) || RZKEY.from(apple.first, apple.second)
							.ignore() == RZKEY.RZKEY_INVALID
					)
				} else {
					snake.removeFirst()
				}
				snake.addLast(next)
				speed = (500 - snake.size * 30).coerceAtLeast(200)
			}
			for (i in 0..8) {
				controller.static(ChromaColor.RED)
				delay(100.milliseconds)
				controller.none()
				delay(40.milliseconds)
			}
		}
	}
}

private fun JFrame.forceFocus() {
	toFront()
	isAlwaysOnTop = true
	requestFocus()
	isAlwaysOnTop = false
}

private fun RZKEY.ignore(): RZKEY =
	if ("_JPN_" in this.name || "_KOR_" in this.name || "_MACRO" in this.name)
		RZKEY.RZKEY_INVALID
	else
		this
