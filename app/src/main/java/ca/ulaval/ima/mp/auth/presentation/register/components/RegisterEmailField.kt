package ca.ulaval.ima.mp.auth.presentation.register.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ca.ulaval.ima.mp.R
import ca.ulaval.ima.mp.core.presentation.designsystem.components.TempoTextField

@Composable
fun RegisterEmailField(
    emailState: TextFieldState,
    isEmailValid: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        TempoTextField(
            state = emailState,
            hint = stringResource(R.string.tempo_mail),
            title = stringResource(R.string.email),
            keyboardType = KeyboardType.Email
        )
        RegisterRequirement(
            text = stringResource(R.string.must_be_a_valid_email),
            isValid = isEmailValid
        )
    }
}
