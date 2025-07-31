package ca.ulaval.ima.mp.focus.data.motion

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import ca.ulaval.ima.mp.focus.data.notification.NotificationBuilder
import ca.ulaval.ima.mp.focus.domain.motion.MotionDetectionManager
import org.koin.android.ext.android.inject
import androidx.core.net.toUri

class MotionDetectionService : Service() {
    private val motionManager by inject<MotionDetectionManager>()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> motionManager.start()
            ACTION_PAUSE -> motionManager.pause()
            ACTION_RESUME -> motionManager.resume()
            ACTION_STOP -> cancelNotification()
        }
        val state = motionManager.getState()
        val notification = NotificationBuilder.build(
            context = this,
            state = state,
            onPause = createIntent(ACTION_PAUSE),
            onResume = createIntent(ACTION_RESUME),
            onClick = createDeepLinkIntent(this)
        )
        startForeground(1, notification)

        return START_NOT_STICKY
    }

    private fun createIntent(action: String) = PendingIntent.getService(
        this,
        action.hashCode(),
        Intent(this, MotionDetectionService::class.java).apply {
            this.action = action
        },
        PendingIntent.FLAG_IMMUTABLE
    )

    fun cancelNotification() {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(1)
        stopSelf()
    }

    override fun onBind(p0: Intent?): IBinder? = null

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
        const val ACTION_PAUSE = "ACTION_PAUSE"
        const val ACTION_RESUME = "ACTION_RESUME"

        fun createDeepLinkIntent(context: Context): PendingIntent {
            val intent = Intent(Intent.ACTION_VIEW, "tempo://active_focus".toUri()).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            return PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }
    }
}
