package com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.addEditProtocol.comp

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloseFullscreen
import androidx.compose.material.icons.filled.FlashlightOff
import androidx.compose.material.icons.filled.FlashlightOn
import androidx.compose.material.icons.filled.FlipCameraAndroid
import androidx.compose.material.icons.filled.FlipCameraIos
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.addEditProtocol.AddEditEvent
import qrscanner.CameraLens
import qrscanner.OverlayShape
import qrscanner.QrScanner

@Composable
fun QrScannerView(
    onEvent: (AddEditEvent) -> Unit
) {
    var cameraLens by remember { mutableStateOf(CameraLens.Back) }
    var flashLightOn by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.6f))
            .clickable { onEvent(AddEditEvent.ChangeStatusOfPopUpOfQrCodeScanner(false)) }
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
        ) {
            IconButton(
                onClick = {
                    onEvent(AddEditEvent.ChangeStatusOfPopUpOfQrCodeScanner(false))
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(300.dp)
                    .clip(shape = RoundedCornerShape(size = 14.dp))
                    .clipToBounds()
                    .border(2.dp, Color.Gray, RoundedCornerShape(size = 14.dp))
                ,
                contentAlignment = Alignment.Center
            ) {
                QrScanner(
                    onFailure = { onEvent(AddEditEvent.ChangeStatusOfPopUpOfQrCodeScanner(false)) },
                    imagePickerHandler = {  },
                    onCompletion = {
                        onEvent(AddEditEvent.SetIdOfProtocol(it))
                        onEvent(AddEditEvent.ChangeStatusOfPopUpOfQrCodeScanner(false))
                    },
                    openImagePicker = false,
                    cameraLens = cameraLens,
                    flashlightOn = flashLightOn,
                    overlayColor = Color.Transparent,
                    overlayBorderColor = Color.Transparent,
                    modifier = Modifier
                        .clipToBounds()
                        .clip(shape = RoundedCornerShape(size = 14.dp)),
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row (
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.background,
                    )
            ) {
                IconButton(
                    onClick = {
                        cameraLens = if (cameraLens == CameraLens.Back) CameraLens.Front else CameraLens.Back
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.FlipCameraIos,
                        contentDescription = null
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                IconButton(
                    onClick = {
                        flashLightOn = !flashLightOn
                    }
                ) {
                    AnimatedContent(flashLightOn) {
                        when (it) {
                            true -> {
                                Icon(
                                    imageVector = Icons.Filled.FlashlightOn,
                                    contentDescription = null
                                )
                            }
                            else -> {
                                Icon(
                                    imageVector = Icons.Filled.FlashlightOff,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}