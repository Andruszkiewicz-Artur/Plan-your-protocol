package com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.history.comp

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.history.HistoryEvent
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.history.HistoryViewModel
import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.unit.enums.PresentedTypeDate
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryPresentation(
    vm: HistoryViewModel = koinViewModel()
) {
    val state = vm.state.collectAsState().value

    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(
                    title = {
                        Text(text = "History")
                    }
                )

                SecondaryTabRow(selectedTabIndex = state.presentedTypeDate.ordinal) {
                    PresentedTypeDate.entries.forEachIndexed { index, presentedTypeDate ->
                        Tab(
                            selected = state.presentedTypeDate.ordinal == index,
                            onClick = { vm.onEvent(HistoryEvent.ChangePresentedType(presentedTypeDate)) },
                            text = {
                                Text(
                                    text = presentedTypeDate.name,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        )
                    }
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
                if(state.protocolsCountByDayList.isEmpty()) {
                    Text("List is empty")
                }
            }
            items(state.protocolsCountByDayList) {
                HistoryItem(
                    protocolCountByDayModel = it,
                    isLast = state.protocolsCountByDayList.last() == it
                )
            }
        }
    }
}