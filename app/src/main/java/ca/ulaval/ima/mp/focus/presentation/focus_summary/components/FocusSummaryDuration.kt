package ca.ulaval.ima.mp.focus.presentation.focus_summary.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.ulaval.ima.mp.R
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray900
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray950

@Composable
fun FocusSummaryDuration(
    duration: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = stringResource(R.string.you_just_finished_a),
            fontSize = 17.sp,
            fontWeight = FontWeight.Normal,
            color = Gray900
        )
        Text(
            text = duration,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Gray950
        )
        Text(
            text = stringResource(R.string.focus_session),
            fontSize = 17.sp,
            fontWeight = FontWeight.Normal,
            color = Gray900
        )
    }
}
