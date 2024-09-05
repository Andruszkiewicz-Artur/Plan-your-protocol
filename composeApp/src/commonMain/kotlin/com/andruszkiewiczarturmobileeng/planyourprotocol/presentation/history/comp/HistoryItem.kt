package com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.history.comp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model.ProtocolCountByDayModel

@Composable
fun HistoryItem(
    protocolCountByDayModel: ProtocolCountByDayModel,
    isLast: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = protocolCountByDayModel.date,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.secondary
            )

            Text(
                text = protocolCountByDayModel.count.toString(),
                style = MaterialTheme.typography.titleMedium
            )
        }

        if (!isLast) HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
    }
}