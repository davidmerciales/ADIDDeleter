package com.example.adiddeleter.domain.utils

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun Modifier.clickableNoRipple(isKeyBoard : Boolean = false, click: (() -> Unit)? = null) : Modifier {
    val context = LocalContext.current
    return clickable(
        interactionSource = MutableInteractionSource(),
        indication = null,
        onClick = {
            click?.invoke()
        }
    )
}