package ca.ulaval.ima.mp.auth.presentation.register.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import ca.ulaval.ima.mp.R
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray950

@Composable
fun RegisterTitle(
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(R.string.register),
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        textAlign = TextAlign.Center,
        color = Gray950,
        modifier = modifier
            .fillMaxWidth()
    )
}
