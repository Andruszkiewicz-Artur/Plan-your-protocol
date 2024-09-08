package com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.addEditProtocol.comp

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Keyboard
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.andruszkiewiczarturmobileeng.planyourprotocol.core.compose.AlertDialogDefault
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.addEditProtocol.TimerType
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopUpOfTimer(
    onDismiss: () -> Unit,
    onSave: (Int?) -> Unit
) {
    val currentDataTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    var timerType by mutableStateOf(TimerType.Picker)

    val timePickerState = rememberTimePickerState(
        initialHour = currentDataTime.hour,
        initialMinute = currentDataTime.minute,
        is24Hour = true
    )

    AlertDialogDefault(
        onDismissRequest = onDismiss,
        content = {
            AnimatedContent(timerType) {
                when(it) {
                    TimerType.Picker -> {
                        TimePicker(
                            timePickerState
                        )
                    }
                    TimerType.Input -> {
                        TimeInput(
                            timePickerState
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                AnimatedContent(timerType) {
                    when (it) {
                        TimerType.Picker -> {
                            IconButton(
                                onClick = {
                                    timerType = TimerType.Input
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Keyboard,
                                    contentDescription = null
                                )
                            }
                        }
                        TimerType.Input -> {
                            IconButton(
                                onClick = {
                                    timerType = TimerType.Picker
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Schedule,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }

                Row {
                    TextButton(

                        onClick = onDismiss
                    ) {
                        Text(text = "Cancel")
                    }

                    Spacer(Modifier.width(8.dp))

                    TextButton(
                        onClick = {
                            onSave(timePickerState.hour * 60 + timePickerState.minute)
                        }
                    ) {
                        Text(text = "OK")
                    }
                }
            }
        }
    )
}