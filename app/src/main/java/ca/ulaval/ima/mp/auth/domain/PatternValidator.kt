package ca.ulaval.ima.mp.auth.domain

interface PatternValidator {
    fun matches(value: String): Boolean
}
