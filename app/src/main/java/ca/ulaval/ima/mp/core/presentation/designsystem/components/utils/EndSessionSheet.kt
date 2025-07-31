package ca.ulaval.ima.mp.core.presentation.designsystem.components.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.ulaval.ima.mp.R
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray200
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray500
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray900
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray950
import ca.ulaval.ima.mp.core.presentation.designsystem.components.TempoActionButton

@Composable
fun EndSessionSheet(
    onEndClick: () -> Unit,
    onDismissRequest: () -> Unit,
    endSessionLoading: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clickable {
                    onDismissRequest()
                }
                .background(
                    color = Gray500,
                    shape = RoundedCornerShape(999.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = null,
                modifier = Modifier
                    .size(16.dp),
                tint = Gray950
            )
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(34.dp)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.HelpOutline,
            contentDescription = null,
            modifier = Modifier
                .size(96.dp),
            tint = Gray900
        )
        Text(
            text = stringResource(R.string.sure_to_end_the_session),
            color = Gray950,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
        TempoActionButton(
            text = stringResource(R.string.end_session_here),
            isLoading = endSessionLoading,
            onClick = onEndClick,
            containerColor = Gray200,
            contentColor = Gray950,
            borderColor = Gray950,
            enabled = !endSessionLoading
        )
    }
}
