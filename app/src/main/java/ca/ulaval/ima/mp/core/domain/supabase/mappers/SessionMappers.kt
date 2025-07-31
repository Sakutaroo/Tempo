package ca.ulaval.ima.mp.core.domain.supabase.mappers

import ca.ulaval.ima.mp.core.domain.supabase.models.SessionInsertRequest
import ca.ulaval.ima.mp.core.domain.supabase.models.SessionUpdateRequest
import ca.ulaval.ima.mp.core.domain.supabase.models.SessionResponse
import ca.ulaval.ima.mp.focus.domain.Session
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun Long.toDurationSeconds(): Duration = this.toDuration(DurationUnit.SECONDS)

fun Duration.toLongSeconds(): Long = this.toLong(DurationUnit.SECONDS)

fun Session.toSessionInsertRequest(): SessionInsertRequest {
    return SessionInsertRequest(
        durationInSeconds = durationInSeconds.toLongSeconds(),
        description = description,
        focusScore = focusScore
    )
}

fun Session.toSessionUpdateRequest(): SessionUpdateRequest {
    return SessionUpdateRequest(
        id = id.toString(),
        durationInSeconds = durationInSeconds.toLongSeconds(),
        description = description,
        focusScore = focusScore
    )
}

fun SessionResponse.toSession(): Session {
    return Session(
        id = id,
        durationInSeconds = durationInSeconds.toDurationSeconds(),
        description = description,
        focusScore = focusScore
    )
}
