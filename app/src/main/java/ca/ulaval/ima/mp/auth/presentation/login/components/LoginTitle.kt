package ca.ulaval.ima.mp.auth.presentation.login.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.ulaval.ima.mp.R
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray50
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray950
import ca.ulaval.ima.mp.core.presentation.designsystem.TempoTheme

@Composable
fun LoginTitle(
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(R.string.log_in),
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        textAlign = TextAlign.Center,
        color = Gray950,
        modifier = modifier
            .fillMaxWidth()
    )
}

@Preview
@Composable
private fun LoginTitlePreview() {
    TempoTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Gray50)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            LoginTitle()
        }
    }
}
