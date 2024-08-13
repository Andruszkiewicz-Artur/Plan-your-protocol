package com.andruszkiewiczarturmobileeng.planyourprotocol.presentation.home

data class DataInfoModel(
    var id: Int? = null,
    var idDocument: String = "",
    var state: DataInfoRealizationDate = DataInfoRealizationDate.Today,
    var time: String = "",
    var date: String = "",
    var resone: String = ""
)
