package net.twisterrob.chroma.razer.rest.effect

import net.twisterrob.chroma.razer.rest.ChromaRequest
import net.twisterrob.chroma.razer.rest.ChromaResponse

class UseCreatedEffectRequest(
	val id: String,
) : ChromaRequest()

@Suppress("RemoveEmptyPrimaryConstructor", "EmptyDefaultConstructor")
class UseCreatedEffectResponse(
	// Nothing.
) : ChromaResponse(0, null)
