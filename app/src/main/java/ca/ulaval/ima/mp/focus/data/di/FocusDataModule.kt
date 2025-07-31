package ca.ulaval.ima.mp.focus.data.di

import ca.ulaval.ima.mp.focus.data.motion.MotionDetectionManagerImpl
import ca.ulaval.ima.mp.focus.domain.motion.MotionDetectionManager
import ca.ulaval.ima.mp.focus.domain.motion.MotionTracker
import org.koin.dsl.module

val focusDataModule = module {
    single<MotionDetectionManager> {
        MotionDetectionManagerImpl(get())
    }

    single {
        MotionTracker(get(), get())
    }
}
