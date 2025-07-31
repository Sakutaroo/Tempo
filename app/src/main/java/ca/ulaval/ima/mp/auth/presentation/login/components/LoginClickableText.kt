package ca.ulaval.ima.mp.auth.presentation.login.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import ca.ulaval.ima.mp.R
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray900
import ca.ulaval.ima.mp.core.presentation.designsystem.Gray950

@Composable
fun LoginClickableText(
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle()
            ) {
                append(stringResource(R.string.don_t_have_an_account) + " ")
            }
            withLink(
                link = LinkAnnotation.Clickable(
                    tag = stringResource(R.string.register),
                    linkInteractionListener = {
                        onRegisterClick()
                    }
                )
            ){
                withStyle(
                    style = SpanStyle(
                        color = Gray950,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append(stringResource(R.string.register))
                }
            }
        },
        modifier = modifier
            .fillMaxWidth(),
        fontWeight = FontWeight.Light,
        fontSize = 13.sp,
        color = Gray900,
        textAlign = TextAlign.Center
    )
}
