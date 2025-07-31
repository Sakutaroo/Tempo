package ca.ulaval.ima.mp.core.domain.supabase.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SessionInsertRequest(
    @SerialName("duration") val durationInSeconds: Long,
    val description: String?,
    @SerialName("focus_score") val focusScore: Int
)

@Serializable
data class SessionUpdateRequest(
    val id: String,
    @SerialName("duration") val durationInSeconds: Long,
    val description: String?,
    @SerialName("focus_score") val focusScore: Int
)
