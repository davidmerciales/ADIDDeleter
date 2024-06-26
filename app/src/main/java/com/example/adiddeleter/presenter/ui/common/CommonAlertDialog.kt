package com.example.adiddeleter.presenter.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adiddeleter.domain.utils.clickableNoRipple
import org.w3c.dom.Text

@Composable
fun CommonAlertDialog(
    onDismissDialog: () -> Unit,
    modifier: Modifier = Modifier,
    title: @Composable (() -> Unit)? = null,
    buttons: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    BoxWithConstraints(modifier = modifier) {
        val h = this.maxHeight
        val w = this.maxWidth

        AlertDialog(
            modifier = modifier,
            onDismissRequest = { onDismissDialog() },
            containerColor = Color.White,
            confirmButton = {
                buttons?.let {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        it()
                    }
                }
            },
            shape = RoundedCornerShape(20.dp),
            text = {
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    title?.let { it() }

                    Spacer(modifier = Modifier.height(h.times(0.04f)))
                    content()

                    Spacer(modifier = Modifier.height(h.times(0.1f)))
                }
            }
        )
    }
}

@Composable
fun BasicAlertDialog(
    modifier: Modifier,
    title: String = "",
    message: String = "",
    positiveButtonText: String = "",
    negativeButtonText: String = "",
    positiveClick: () -> Unit = {},
    negativeClick: () -> Unit = {}
) {
    BoxWithConstraints(modifier = modifier) {
        val h = this.maxHeight
        val w = this.maxWidth

        CommonAlertDialog(
            onDismissDialog = { negativeClick() },
            modifier = Modifier
                .height(w)
                .width(h),
            title = {
                Text(
                    text = title,
                    style = TextStyle().copy(
                        fontSize = w.times(0.12f).value.sp,
                        letterSpacing = .5.sp
                    )
                )
            },
            buttons = {
                Text(
                    modifier = Modifier
                        .clickableNoRipple {
                            negativeClick()
                        },
                    text = negativeButtonText,
                    style = TextStyle().copy(
                        fontSize = w.times(0.085f).value.sp,
                    )
                )

                Spacer(modifier = Modifier.width(w.times(.1f)))

                Text(
                    modifier = Modifier
                        .clickableNoRipple {
                            positiveClick()
                        },
                    text = positiveButtonText,
                    style = TextStyle().copy(
                        fontSize = w.times(0.085f).value.sp,
                    )
                )
            }
        ) {
            Text(
                text = message,
                style = TextStyle().copy(
                    color = Color.Black,
                    fontSize = w.times(0.076f).value.sp,
                    fontWeight = FontWeight.W400,
                    letterSpacing = .5.sp
                )
            )
        }
    }

}