package ca.ulaval.ima.mp.core.domain.supabase.models

import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SessionResponse(
    val id: String,
    @SerialName("duration") val durationInSeconds: Long,
    val description: String?,
    @SerialName("created_at") val createdAt: Instant,
    @SerialName("focus_score") val focusScore: Int
)
