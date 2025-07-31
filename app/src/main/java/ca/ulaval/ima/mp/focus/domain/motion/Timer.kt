package ca.ulaval.ima.mp.focus.domain.motion

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

object Timer {
    fun timeAndEmit(interval: Long = 1000L): Flow<Duration> = flow {
        while (true) {
            delay(interval)
            emit(interval.milliseconds)
        }
    }
}
