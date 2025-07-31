package ca.ulaval.ima.mp.focus.presentation.models

import ca.ulaval.ima.mp.focus.domain.mappers.toDurationUi
import kotlin.time.Duration

data class SessionUi(
    val id: String? = null,
    val duration: String = Duration.ZERO.toDurationUi(),
    val description: String = "",
    val focusScore: String = "0"
)
