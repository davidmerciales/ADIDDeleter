package com.example.adiddeleter.presenter.ui.main

import android.util.Log
import androidx.annotation.RawRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.adiddeleter.R
import com.example.adiddeleter.domain.utils.clickableNoRipple
import com.example.adiddeleter.presenter.ui.common.BasicAlertDialog
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
){
    val scope = rememberCoroutineScope()
    val itemList = listOf(
        "9900",
        "9901",
        "9906"
    )

    var isShowAlertDialog by remember {
        mutableStateOf(false)
    }

    Box(modifier = Modifier
        .fillMaxSize()){

        if (viewModel.state.isLoading){
            Box(modifier = Modifier
                .fillMaxSize()
                .zIndex(1f)
                .background(Color.Black.copy(alpha = .7f))
                .align(Alignment.Center)
                .clickableNoRipple { }){

                Column(
                    modifier = Modifier
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AnimatedPreloader(
                        modifier = Modifier
                            .size(200.dp),
                        R.raw.animation_preloader
                    )

                    Text(text = "Deleting...",
                        color = Color.White,
                        fontSize = 20.sp)
                }
            }
        }

        if (viewModel.state.isSuccess){
            Box(modifier = Modifier
                .fillMaxSize()
                .zIndex(1f)
                .background(Color.Black.copy(alpha = .7f))
                .align(Alignment.Center)
                .clickableNoRipple { }){

                Column(
                    modifier = Modifier
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AnimatedPreloader(
                        modifier = Modifier
                            .size(200.dp),
                        R.raw.animation_success
                    )

                    Text(text = viewModel.state.message,
                        color = Color.White,
                        fontSize = 20.sp)
                }
            }

            scope.launch {
                delay(2000)
                viewModel.state.isSuccess = false
            }
        }

        if (viewModel.state.isFailed){
            Log.d("MainSCreen", viewModel.state.message)
            Box(modifier = Modifier
                .fillMaxSize()
                .zIndex(1f)
                .background(Color.Black.copy(alpha = .7f))
                .align(Alignment.Center)
                .clickableNoRipple { }){

                Column(
                    modifier = Modifier
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AnimatedPreloader(
                        modifier = Modifier
                            .size(200.dp),
                        R.raw.animation_failed
                    )

                    Text(text = viewModel.state.message,
                        color = Color.White,
                        fontSize = 20.sp)
                }
            }

            scope.launch {
                delay(2000)
                viewModel.state.isFailed = false
            }
        }

        if (isShowAlertDialog) {
            BoxWithConstraints(modifier = Modifier
                .fillMaxSize()
                .zIndex(1f)
                .background(Color.Black.copy(alpha = .7f))
                .align(Alignment.Center)
                .clickableNoRipple { }) {
                val h = this.maxHeight
                val w = this.maxWidth

                BasicAlertDialog(
                    modifier = Modifier
                        .zIndex(1f)
                        .width(w.times(.46f))
                        .height(h.times(0.42f)),
                    title = "Delete",
                    message = "Are you sure you want to delete this id: ${viewModel.state.adid}?",
                    positiveButtonText = "OK",
                    negativeButtonText = "CANCEL",
                    positiveClick = {
                        viewModel.setEvent(MainContract.MainEvent.OnDeleteADID(viewModel.state.adid))
                        isShowAlertDialog = false
                    },
                    negativeClick = {
                        isShowAlertDialog = false
                    }
                )
            }
        }

        Column(Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {

            DynamicSelectTextField(
                modifier = Modifier
                    .fillMaxWidth(0.8f),
                selectedValue = itemList[viewModel.state.selectedIndex],
                options = itemList,
                label = "Select Variant",
                onValueChangedEvent = { viewModel.state.selectedIndex = it })

           Spacer(modifier = Modifier.height(80.dp))

            TextField(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .border(1.dp, Color.Gray, RoundedCornerShape(5.dp)),
                placeholder = {
                    Text(text = "Enter ADID")
                },
                value = viewModel.state.adid,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = {
                    viewModel.state.adid = it
                },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.Gray.copy(alpha = 0.2f),
                    focusedIndicatorColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(5.dp),
                textStyle = TextStyle(
                    fontSize = 13.sp
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            DeleteButton(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(55.dp)
                    .background(Color(0xFFcd352c), RoundedCornerShape(10.dp))
                    .clickable {
                        isShowAlertDialog = true
                    }
            )
        }
    }
}

@Composable
fun DeleteButton(
    modifier: Modifier
){
    Box(modifier = modifier,
        contentAlignment = Alignment.Center){
        Text(
            text = "Delete",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.W800
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicSelectTextField(
    selectedValue: String,
    options: List<String>,
    label: String,
    onValueChangedEvent: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedValue,
            onValueChange = {},
            label = { Text(text = label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = OutlinedTextFieldDefaults.colors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEachIndexed() {index, option->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        expanded = false
                        onValueChangedEvent(index)
                    }
                )
            }
        }
    }
}

@Composable
fun AnimatedPreloader(modifier: Modifier = Modifier, model: Int) {
    val preloaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            model
        )
    )

    val preloaderProgress by animateLottieCompositionAsState(
        preloaderLottieComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )


    LottieAnimation(
        composition = preloaderLottieComposition,
        progress = preloaderProgress,
        modifier = modifier
    )
}