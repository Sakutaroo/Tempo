package ca.ulaval.ima.mp.focus.domain.mappers

import kotlin.math.ceil
import kotlin.time.Duration

fun Duration.toDurationUi(): String {
    val (hours, minutes, seconds) = toComponents { hours, minutes, seconds, _ -> intArrayOf(hours.toInt(), minutes, seconds) }

    if (hours > 0) {
        return "%02dh%02dm".format(hours, minutes)
    }

    return "%02dm%02ds".format(minutes, seconds)
}

fun Float.toDurationUi(): String {
    val totalInMinutes = ceil(this).toInt()
    val hours = totalInMinutes / 60
    val minutes = totalInMinutes % 60

    return "%02d:%02d".format(hours, minutes)
}
