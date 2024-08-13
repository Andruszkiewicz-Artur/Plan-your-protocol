package com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.comp

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.DataInfoRealizationDate

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ListOfDataStateView(
    currentState: DataInfoRealizationDate,
    onClick: (DataInfoRealizationDate) -> Unit
) {
    FlowRow {
        DataInfoRealizationDate.entries.forEach {
            RadioButtonWithTextView(
                selected = it == currentState,
                text = it.name,
                onClick = {
                    onClick(it)
                }
            )
        }
    }
}