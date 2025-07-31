package ca.ulaval.ima.mp.core.presentation.designsystem.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.ulaval.ima.mp.R
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray800
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray900
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray950
import ca.ulaval.ima.mp.core.presentation.designsystem.TempoTheme

@Composable
fun TempoScoreGauge(
    score: Int,
    modifier: Modifier = Modifier,
    maxScore: Int = 100
) {
    val sweepAngle = (score / maxScore.toFloat()) * 180f

    Box(
        modifier = modifier
            .size(150.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .size(150.dp)
        ) {
            drawArc(
                color = Gray800,
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = false,
                style = Stroke(
                    width = 8.dp.toPx(),
                    cap = StrokeCap.Round
                )
            )
            drawArc(
                color = Gray950,
                startAngle = 180f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(
                    width = 8.dp.toPx(),
                    cap = StrokeCap.Round
                )
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.display_score, score),
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = Gray950
            )
            Text(
                text = stringResource(R.string.focus_score),
                fontSize = 17.sp,
                fontWeight = FontWeight.Normal,
                color = Gray900
            )
        }
    }
}

@Preview
@Composable
private fun TempoScoreGaugePreview() {
    TempoTheme {
        TempoScoreGauge(
            score = 89,
            modifier = Modifier.fillMaxSize()
        )
    }
}
