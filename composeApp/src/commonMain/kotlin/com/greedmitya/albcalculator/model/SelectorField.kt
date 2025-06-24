package com.greedmitya.albcalculator.model

data class SelectorField(
    val title: String,
    val options: List<String>,
    var selected: String?
)
