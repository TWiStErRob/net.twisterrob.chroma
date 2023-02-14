package net.twisterrob.chroma

import kotlinx.coroutines.delay
import net.twisterrob.chroma.razer.ChromaColor
import net.twisterrob.chroma.razer.ChromaController
import net.twisterrob.chroma.razer.RZKEY
import net.twisterrob.chroma.razer.rest.keyboard.KeyboardRequest
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

suspend fun main() {
	ChromaController().use { controller ->
		controller.start()
		for (i in 0..100) {
//			print("on")
			controller.customKey(KeyboardRequest.CustomEffectParams().apply {
				val key = RZKEY.values().filter { it != RZKEY.RZKEY_INVALID }.random()
				val color = (Math.random() * 0xffffff).toInt()
				print(" $key (${key.row}, ${key.column}) = ${color} (${color.toString(16).padStart(6, '0')})")
				this.color[key.row][key.column] = color
			})
			//		controller.customKey(net.twisterrob.chroma.razer.rest.ChromaSDK.EffectRequest.CustomEffectParams().apply {
			//			val key = net.twisterrob.chroma.razer.RZKEY.RZKEY_O
			//			val color = 0x000000 + i * 16
			//			print(" $key (${key.row}, ${key.column}) = ${color} (${color.toString(16).padStart(6, '0')})")
			//			this.color[key.row][key.column] = color
			//		})
			println(" - ")
			delay(10.milliseconds)

			//		print("off")
			//		controller.none()
			//		delay(50.milliseconds)
			//		println("!")
		}
		println("color")
		controller.static(ChromaColor.RED)
		println("wait")
		delay(1.seconds)
	}
}
