package com.potatomeme.searchapp

data class SampleItem(
    override val imgUrl: String,
    override val title: String,
    override val date: String
) : Item
