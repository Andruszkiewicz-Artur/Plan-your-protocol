package com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.comp

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.andruszkiewiczarturmobileeng.planyourprotocol.navigation.Screen
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.HomeEvent
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.HomeState
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.HomeViewModel
import com.andruszkiewiczarturmobileeng.planyourprotocol.util.Constant
import com.andruszkiewiczarturmobileeng.planyourprotocol.util.convertLongToStringDate

@Composable
fun ProtocolListView(
    vm: HomeViewModel,
    state: HomeState,
    navHostController: NavHostController,
) {
    val clipboardManager = LocalClipboardManager.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
    ) {
        item {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Protocols date",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { vm.onEvent(HomeEvent.ChangeDateListOfProtocols(true)) }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ChevronLeft,
                                contentDescription = null
                            )
                        }

                        Text(
                            text = state.currentDatePresenting.convertLongToStringDate(),
                            style = MaterialTheme.typography.titleMedium
                        )

                        IconButton(
                            onClick = { vm.onEvent(HomeEvent.ChangeStatusOfPopUpOfCalendar(true)) }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.CalendarMonth,
                                contentDescription = null
                            )
                        }

                        IconButton(
                            onClick = { vm.onEvent(HomeEvent.ChangeDateListOfProtocols(false)) }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ChevronRight,
                                contentDescription = null
                            )
                        }
                    }
                }

                Spacer(Modifier.width(16.dp))

                Row {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                    ) {
                        Text(
                            text = "This month",
                            style = MaterialTheme.typography.labelLarge
                        )

                        Spacer(Modifier.height(4.dp))

                        Text(
                            text = "${state.protocolsInThisMonth}",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }

                    IconButton(
                        onClick = { navHostController.navigate(Screen.History.route) }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp)
                        )
                    }
                }
            }

            AnimatedVisibility(
                state.protocolsList.isNotEmpty(),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    TextButton(
                        onClick = { vm.onEvent(HomeEvent.ChangeAllSelection(!state.isAllSelected)) }
                    ) {
                        AnimatedContent(state.isAllSelected) { isSelected ->
                            if (isSelected) {
                                Text(
                                    text = "Unselect All"
                                )
                            } else {
                                Text(
                                    text = "Select All"
                                )
                            }
                        }
                    }


                    IconButton(
                        onClick = {
                            vm.onEvent(HomeEvent.ClickCopyData(clipboardManager))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = null
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            AnimatedVisibility(state.protocolsList.isEmpty()) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Today's list is empty",
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
                    )
                }
            }
        }

        items(state.protocolsList) { protocol ->
            DataInfoItem(
                protocol = protocol,
                onClickDelete = { vm.onEvent(HomeEvent.DeleteProtocol(protocol)) },
                onClickEdit = {
                    navHostController.currentBackStackEntry?.savedStateHandle?.set(
                        Constant.EDIT_PROTOCOL_ARGUMENT,
                        protocol.idDocument
                    )
                    navHostController.navigate(Screen.AddEdit.route)
                },
                onClickSelect = {vm.onEvent(HomeEvent.SelectProtocol(protocol, it))}
            )

            if (state.protocolsList.last() != protocol) {
                HorizontalDivider()
            }
        }
    }
}