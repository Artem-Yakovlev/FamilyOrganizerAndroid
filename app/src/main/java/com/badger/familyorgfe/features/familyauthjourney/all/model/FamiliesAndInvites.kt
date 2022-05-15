package com.badger.familyorgfe.features.familyauthjourney.all.model

import com.badger.familyorgfe.data.model.Family

data class FamiliesAndInvites(
    val families: List<Family>,
    val invites: List<Family>
) {

    companion object {
        fun createEmpty() = FamiliesAndInvites(
            families = emptyList(),
            invites = emptyList()
        )
    }
}