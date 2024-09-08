package com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.addEditProtocol.comp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model.HistoricalProtocolModel
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.ProtocolRealizationType
import com.andruszkiewiczarturmobileeng.planyourprotocol.util.convertMillisToDate
import com.andruszkiewiczarturmobileeng.planyourprotocol.util.convertMillisToDateWithStringMonth
import com.andruszkiewiczarturmobileeng.planyourprotocol.util.convertToTime

@Composable
fun HistoricalItem(
    protocol: HistoricalProtocolModel,
    isLast: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "" + when(protocol.state) {
                    ProtocolRealizationType.Today -> protocol.time?.convertToTime()
                    ProtocolRealizationType.CAD -> "CAD ${protocol.date?.convertMillisToDate()} - ${protocol.reason}"
                    else -> protocol.state.name
                },
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .padding(vertical = 4.dp)
            )

            Text(
                text = protocol.editingDate?.convertMillisToDateWithStringMonth(true) ?: "",
                style = MaterialTheme.typography.labelMedium,
            )
        }

        if (!isLast) HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
    }
}