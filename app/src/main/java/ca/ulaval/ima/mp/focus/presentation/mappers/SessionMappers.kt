package ca.ulaval.ima.mp.focus.presentation.mappers

import ca.ulaval.ima.mp.focus.domain.Session
import ca.ulaval.ima.mp.focus.domain.mappers.toDurationUi
import ca.ulaval.ima.mp.focus.presentation.models.SessionUi
import kotlin.time.Duration.Companion.seconds

fun Session.toSessionUi(): SessionUi {
    return SessionUi(
        id = id,
        duration = durationInSeconds.toDurationUi(),
        description = description ?: "Work session",
        focusScore = focusScore.toString()
    )
}

fun SessionUi.toSession(): Session {
    val delimiters = listOf("h", "m", "s")
    val durations = mutableListOf<Int>(0, 0, 0)
    var lastIndex = 0

    delimiters.forEachIndexed { index, del ->
        val idx = duration.indexOf(del)
        if (idx == -1) {
            durations[index] = 0
        } else {
            durations[index] = duration.substring(lastIndex, idx).toInt()
            lastIndex = idx + 1
        }
    }

    val durationInSeconds = durations.reduce { acc, item -> acc + item }.seconds

    return Session (
        id = id,
        description = description,
        durationInSeconds = durationInSeconds,
        focusScore = focusScore.toInt()
    )
}
