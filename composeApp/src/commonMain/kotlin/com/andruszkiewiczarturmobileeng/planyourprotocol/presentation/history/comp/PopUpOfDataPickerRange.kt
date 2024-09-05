package com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.history.comp

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model.RangeOfDataModel
import com.andruszkiewiczarturmobileeng.planyourprotocol.unit.convertMillisToDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PopUpOfDataPickerRange(
    range: RangeOfDataModel,
    isPresented: Boolean,
    onSave: (RangeOfDataModel) -> Unit,
    onDismiss: () -> Unit
) {
    val state = rememberDateRangePickerState()
    state.setSelection(range.min, range.last)

    AnimatedVisibility(
        visible = isPresented,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background.copy(alpha = 0.3f))
                .clickable { onDismiss() }
                .padding(top = 48.dp)
        ) {
            AnimatedContent(state.displayMode) {
                DateRangePicker(
                    state = state,
                    modifier = Modifier,
                    title = {
                        Text(
                            text = "Select date range",
                            modifier = Modifier.padding(16.dp))
                    },
                    headline = {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp)
                                .padding(vertical = 16.dp)
                        ) {

                            Text(
                                text = """${state.selectedStartDateMillis?.convertMillisToDate() ?: "Start Date"} - ${state.selectedEndDateMillis?.convertMillisToDate() ?: "End Date"}"""
                            )

                            Row {
                                IconButton(
                                    onClick = {
                                        onSave(RangeOfDataModel(state.selectedStartDateMillis, state.selectedEndDateMillis))
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Done,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(30.dp)
                                    )
                                }
                            }
                        }
                    },
                    showModeToggle = true,
                )
            }
        }
    }
}