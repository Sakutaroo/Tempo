package ca.ulaval.ima.mp.focus.data.motion

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import ca.ulaval.ima.mp.focus.domain.motion.MotionDetectionManager
import ca.ulaval.ima.mp.focus.domain.motion.MotionState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.abs

class MotionDetectionManagerImpl(
    private val context: Context
) : MotionDetectionManager, SensorEventListener {
    private var sensorManager: SensorManager? = null
    private var sensor: Sensor? = null
    private var lastShakeTime = 0L

    private val _motionState = MutableStateFlow(MotionState.STOPPED)
    override val motionState: StateFlow<MotionState> = _motionState.asStateFlow()

    private val _motionEvents = MutableSharedFlow<Unit>(
        extraBufferCapacity = 1
    )
    override val motionEvents = _motionEvents.asSharedFlow()

    override fun start() {
        if (_motionState.value != MotionState.STOPPED) return

        _motionState.value = MotionState.ACTIVE
        context.startServiceWithAction(MotionDetectionService.ACTION_START)
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager?.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)
        sensorManager?.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI)
    }

    override fun pause() {
        if (_motionState.value != MotionState.ACTIVE) return

        _motionState.value = MotionState.PAUSED
        sensorManager?.unregisterListener(this)
        context.startServiceWithAction(MotionDetectionService.ACTION_PAUSE)
    }

    override fun resume() {
        if (_motionState.value != MotionState.PAUSED) return

        _motionState.value = MotionState.ACTIVE
        sensor?.let { sensor ->
            sensorManager?.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI)
        }
        context.startServiceWithAction(MotionDetectionService.ACTION_RESUME)
    }

    override fun stop() {
        _motionState.value = MotionState.STOPPED
        sensorManager?.unregisterListener(this)
        context.startServiceWithAction(MotionDetectionService.ACTION_STOP)
    }

    override fun isActive(): Boolean = _motionState.value == MotionState.ACTIVE

    override fun getState(): MotionState = _motionState.value

    override fun onSensorChanged(p0: SensorEvent?) {
        if (_motionState.value != MotionState.ACTIVE) return

        p0?.values?.let {
            if (it.any { abs(it) > 5f } && System.currentTimeMillis() - lastShakeTime > 500) {
                lastShakeTime = System.currentTimeMillis()
                _motionEvents.tryEmit(Unit)
            }
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    companion object {
        fun Context.startServiceWithAction(action: String) {
            val intent = Intent(this, MotionDetectionService::class.java).apply {
                this.action = action
            }
            startService(intent)
        }
    }
}
