package ca.ulaval.ima.mp.focus.presentation.profile.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ca.ulaval.ima.mp.R
import ca.ulaval.ima.mp.core.presentation.designsystem.RedContainer
import ca.ulaval.ima.mp.core.presentation.designsystem.RedContent
import ca.ulaval.ima.mp.core.presentation.designsystem.TempoTheme
import ca.ulaval.ima.mp.core.presentation.designsystem.components.TempoActionButton

@Composable
fun ProfileLogout(
    logoutLoading: Boolean,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        TempoActionButton(
            text = stringResource(R.string.logout),
            isLoading = logoutLoading,
            onClick = {
                onLogoutClick()
            },
            containerColor = RedContainer,
            contentColor = RedContent
        )
    }
}

@Preview
@Composable
private fun ProfileLogoutPreview() {
    TempoTheme { 
        ProfileLogout(
            logoutLoading = false,
            onLogoutClick = {}
        )
    }
}
