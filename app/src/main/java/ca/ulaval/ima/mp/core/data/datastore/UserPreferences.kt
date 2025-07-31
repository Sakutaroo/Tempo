package ca.ulaval.ima.mp.core.data.datastore

import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(
    val accessToken: String?= null,
    val refreshToken: String? = null
)
