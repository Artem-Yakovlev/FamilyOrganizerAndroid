package com.badger.familyorgfe.data.model

data class Family(
    val id: Long,
    val name: String,
    val members: List<String>,
    val invites: List<String>
)