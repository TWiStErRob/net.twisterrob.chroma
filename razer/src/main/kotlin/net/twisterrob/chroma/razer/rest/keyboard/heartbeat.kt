package net.twisterrob.chroma.razer.rest.keyboard

import net.twisterrob.chroma.razer.rest.ChromaRequest
import net.twisterrob.chroma.razer.rest.ChromaResponse

/**
 * https://assets.razerzone.com/dev_portal/REST/html/index.html#keeping_the_connection_alive
 * https://assets.razerzone.com/dev_portal/REST/html/md__r_e_s_t_external_11_heartbeat.html
 */
@Suppress("RemoveEmptyPrimaryConstructor", "EmptyDefaultConstructor")
class HeartbeatRequest(
	// Nothing.
) : ChromaRequest()

class HeartbeatResponse(
	val tick: Int,
) : ChromaResponse(0, null)
