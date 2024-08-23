package com.andruszkiewiczarturmobileeng.planyourprotocol

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.andruszkiewiczarturmobileeng.planyourprotocol.domain.model.ProtocolModule
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.comp.AddingNewValueView
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.comp.DataInfoItem
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.comp.HomePresentation
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.comp.PopUpOfCalendar
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.comp.PopUpOfReasonDialog
import com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home.comp.PopUpOfTimer
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {

    MaterialTheme {
        HomePresentation()
    }
}