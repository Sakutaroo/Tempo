package ca.ulaval.ima.mp.auth.data

import android.util.Patterns
import ca.ulaval.ima.mp.auth.domain.PatternValidator

object EmailPatternValidator: PatternValidator {
    override fun matches(value: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(value).matches()
    }
}
