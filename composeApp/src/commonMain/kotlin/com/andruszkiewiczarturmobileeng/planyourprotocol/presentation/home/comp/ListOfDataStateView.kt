package com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.comp

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.ProtocolRealizationType

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ListOfDataStateView(
    currentState: ProtocolRealizationType,
    onClick: (ProtocolRealizationType) -> Unit
) {
    FlowRow {
        ProtocolRealizationType.entries.forEach {
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