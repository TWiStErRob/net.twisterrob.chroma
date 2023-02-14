package net.twisterrob.chroma.razer.rest.keyboard

import net.twisterrob.chroma.razer.rest.ChromaRequest
import net.twisterrob.chroma.razer.rest.ChromaResponse

/**
 * https://assets.razerzone.com/dev_portal/REST/html/md__r_e_s_t_external_01_init.html
 */
class InitRequest(
	val title: String,
	val description: String,
	val author: InitAuthor,
	@Suppress("PropertyName", "ConstructorParameterNaming")
	val device_supported: List<InitDevice>,
	val category: String = "application"
) : ChromaRequest() {

	class InitAuthor(
		val name: String,
		val contact: String,
	)

	@Suppress("EnumNaming", "EnumEntryName")
	enum class InitDevice {

		keyboard,
		mouse,
		headset,
		mousepad,
		keypad,
		chromalink
	}
}

class InitResponse(
	val sessionId: Int,
	val uri: String,
) : ChromaResponse(0, null)
