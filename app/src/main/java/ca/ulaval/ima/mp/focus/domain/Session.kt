package ca.ulaval.ima.mp.focus.domain

import kotlinx.serialization.Serializable
import kotlin.time.Duration

@Serializable
data class Session(
    val id: String?,
    val durationInSeconds: Duration,
    val description: String?,
    val focusScore: Int = 0
)
