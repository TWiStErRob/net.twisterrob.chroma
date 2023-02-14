package net.twisterrob.chroma.razer.rest.keyboard

import net.twisterrob.chroma.razer.rest.ChromaResponse

// `class InfoRequest` doesn't exist, because this is a GET request.

class InfoResponse(
	val core: String,
	val device: String,
	val version: String,
) : ChromaResponse(0, null)
