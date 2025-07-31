package ca.ulaval.ima.mp.focus.data.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import ca.ulaval.ima.mp.R
import ca.ulaval.ima.mp.focus.domain.motion.MotionState
import androidx.core.graphics.toColorInt
import androidx.media.app.NotificationCompat.MediaStyle

object NotificationBuilder {
    private const val CHANNEL_ID = "motion_channel"

    fun build(
        context: Context,
        state: MotionState,
        onPause: PendingIntent,
        onResume: PendingIntent,
        onClick: PendingIntent
    ): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Motion detector",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                lightColor = "#EDEEF0".toColorInt()
            }
            val manager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.tempo_logo)
            .setContentTitle(context.getString(R.string.motion_detector))
            .setContentText(context.getString(R.string.in_progress))
            .setOnlyAlertOnce(true)
            .setOngoing(true)
            .setColor("#18191B".toColorInt())
            .setContentIntent(onClick)

        val actions = mutableListOf<NotificationCompat.Action>()

        when (state) {
            MotionState.ACTIVE -> {
                actions.add(
                    NotificationCompat.Action(
                        R.drawable.ic_pause,
                        context.getString(R.string.pause),
                        onPause
                    )
                )
            }

            MotionState.PAUSED -> {
                builder.setContentText(context.getString(R.string.taking_a_break))
                actions.add(
                    NotificationCompat.Action(
                        R.drawable.ic_play,
                        context.getString(R.string.resume),
                        onResume
                    )
                )
            }

            else -> Unit
        }

        actions.forEach { action ->
            builder.addAction(action)
        }

        if (actions.isNotEmpty()) {
            builder.setStyle(
                MediaStyle()
                    .setShowActionsInCompactView(*actions.indices.toList().toIntArray())
            )
        }

        return builder.build()
    }
}
