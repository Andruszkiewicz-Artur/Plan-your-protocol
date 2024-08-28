package com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.comp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model.ProtocolModule
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.ProtocolRealizationType
import com.andruszkiewiczarturmobileeng.planyourprotocol.unit.convertMillisToDate
import com.andruszkiewiczarturmobileeng.planyourprotocol.unit.convertToTime

@Composable
fun DataInfoItem(
    dataInfo: ProtocolModule,
    onClickSelect: (Boolean) -> Unit,
    onClickEdit: () -> Unit,
    onClickDelete: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth(0.7f)
        ) {
            Checkbox(
                checked = dataInfo.isSelected,
                onCheckedChange = {
                    onClickSelect(it)
                }
            )

            Text(
                text = "${dataInfo.idDocument} - " + when(dataInfo.state) {
                    ProtocolRealizationType.Today -> dataInfo.time?.convertToTime()
                    ProtocolRealizationType.CAD -> "CAD ${dataInfo.date?.convertMillisToDate()} - ${dataInfo.resone}"
                    else -> dataInfo.state.name
                },
                modifier = Modifier
                    .padding(vertical = 4.dp)
            )
        }

        Spacer(
            modifier = Modifier
                .width(8.dp)
        )

        Row {
            IconButton(
                onClick = { onClickEdit() }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = null
                )
            }

            IconButton(
                onClick = { onClickDelete() }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Remove,
                    contentDescription = null
                )
            }
        }
    }
}