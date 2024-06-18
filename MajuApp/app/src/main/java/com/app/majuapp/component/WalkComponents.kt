package com.app.majuapp.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.app.majuapp.ui.theme.GoldenPoppy
import com.app.majuapp.ui.theme.MajuAppTheme
import com.app.majuapp.ui.theme.OuterSpace
import com.app.majuapp.ui.theme.SonicSilver
import com.app.majuapp.ui.theme.TaupeGray
import com.app.majuapp.ui.theme.White
import com.app.majuapp.ui.theme.brightGray
import com.app.majuapp.ui.theme.defaultPadding
import com.app.majuapp.ui.theme.dialogButtonRoundedCorner
import com.app.majuapp.ui.theme.dialogCornerPadding
import com.app.majuapp.ui.theme.dialogDefaultPadding
import com.app.majuapp.ui.theme.roundedCornerPadding
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalkScreenChooseStartDialog(
    title: String,
    content: String,
    onClickDismiss: () -> Unit,
    onClickConfirm: () -> Unit,
) {

    var showAnimatedDialog = remember { mutableStateOf(false) }
    var graphicVisible = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { graphicVisible.value = true }

    var showDialog = remember { mutableStateOf(true) }
    var animateIn = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { animateIn.value = true }

    val seoul = LatLng(37.5744, 126.9771)
    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(seoul, 16f)
    }

    AnimatedVisibility(
        visible = graphicVisible.value, enter = expandVertically(
            animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
            expandFrom = Alignment.CenterVertically,
        )
    ) {
        Dialog(
            onDismissRequest = { onClickDismiss() },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = false,
            ),
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.shadow(8.dp, shape = RoundedCornerShape(16.dp))
                    .background(Color.Black.copy(alpha = .9f))
                    .clip(RoundedCornerShape(dialogCornerPadding)).background(White).padding(
                        start = dialogDefaultPadding,
                        end = dialogDefaultPadding,
                        top = dialogDefaultPadding,
                        bottom = 16.dp
                    ),
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        modifier = Modifier,
                        text = title,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    Text(
                        text = content,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraLight,
                        color = SonicSilver,
                        modifier = Modifier.align(
                            Alignment.CenterHorizontally
                        ),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    Box(
                        modifier = Modifier.clip(RoundedCornerShape(roundedCornerPadding))
                            .fillMaxWidth().height(260.dp).border(
                                2.dp, brightGray, shape = RoundedCornerShape(roundedCornerPadding)
                            ).padding(defaultPadding),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.TopCenter),
                            text = "보라매공원 산책로",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.ExtraLight,
                            color = SonicSilver,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(18.dp))
                        Box(
                            modifier = Modifier.clip(RoundedCornerShape(roundedCornerPadding))
                                .height(200.dp).background(
                                    OuterSpace, shape = RoundedCornerShape(roundedCornerPadding)
                                ),
                            contentAlignment = Alignment.BottomCenter,
                        ) {
                            GoogleMap(
                                modifier = Modifier.fillMaxSize(),
                                cameraPositionState = cameraPositionState
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(18.dp))
                    Row(
                        modifier = Modifier, horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        WalkComponentButton("돌아가기", TaupeGray, onClickDismiss, Modifier.weight(1f))
                        WalkComponentButton(
                            "선택하기", GoldenPoppy, onClickConfirm, Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        DisposableEffect(Unit) {
            onDispose {
                showAnimatedDialog.value = false
            }
        }
    }
} // End of WalkScreenChooseStartDialogue()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartialBottomSheet(sheetState: SheetState, onDismissRequest: () -> Unit) {

    ModalBottomSheet(modifier = Modifier.fillMaxHeight(),
        sheetState = sheetState,
        onDismissRequest = { onDismissRequest }) {
        Text(
            "Swipe up to open sheet. Swipe down to dismiss.", modifier = Modifier.padding(16.dp)
        )
    }
} // End of PartialBottomSheet()


@Composable
private fun WalkComponentButton(
    buttonText: String, buttonColor: Color, onClickConfirm: () -> Unit, modifier: Modifier
) {
    Button(shape = RoundedCornerShape(dialogButtonRoundedCorner),
        modifier = modifier.fillMaxWidth().wrapContentHeight(),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
        ),
        onClick = { onClickConfirm() }) {
        Text(buttonText, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
} // End of WalkComponentButton()

@Preview
@Composable
private fun WalkScreenChooseStartDialogPreveiw() {
    MajuAppTheme {
        WalkScreenChooseStartDialog("출발지 선택", "지도 위에 현재 위치\n 선택해주세요!", {}, {})
    }
} // End of WalkScreenChooseStartDialogPreview()
