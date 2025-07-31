package ca.ulaval.ima.mp.auth.presentation.register.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.ulaval.ima.mp.R
import ca.ulaval.ima.mp.auth.domain.PasswordValidationState
import ca.ulaval.ima.mp.auth.domain.UserDataValidator
import ca.ulaval.ima.mp.core.presentation.designsystem.TempoTheme

@Composable
fun RegisterPasswordRequirements(
    passwordValidationState: PasswordValidationState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        RegisterRequirement(
            text = stringResource(R.string.at_least_d_characters, UserDataValidator.MIN_PASSWORD_LENGTH),
            isValid = passwordValidationState.hasMinLength
        )
        RegisterRequirement(
            text = stringResource(R.string.at_least_one_number),
            isValid = passwordValidationState.hasNumber
        )
        RegisterRequirement(
            text = stringResource(R.string.contains_lowercase_character),
            isValid = passwordValidationState.hasLowerCaseCharacter
        )
        RegisterRequirement(
            text = stringResource(R.string.contains_uppercase_character),
            isValid = passwordValidationState.hasUpperCaseCharacter
        )
    }
}

@Preview
@Composable
private fun RegisterPasswordRequirementsPreview() {
    TempoTheme {
        RegisterPasswordRequirements(
            passwordValidationState = PasswordValidationState()
        )
    }
}
