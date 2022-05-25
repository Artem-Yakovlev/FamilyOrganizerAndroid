package com.badger.familyorgfe.features.appjourney.profile.model

import com.badger.familyorgfe.data.model.User
import com.badger.familyorgfe.data.source.family.model.OnlineUser

data class FamilyMember(
    val name: String,
    val online: Boolean,
    val onlineUser: OnlineUser
) {

    val status = onlineUser.user.status

    val email = onlineUser.user.email

    val imageUrl = onlineUser.user.getImageUrl()

    companion object {

        const val ONLINE_DEBOUNCE = 5000L

        fun createEmpty() = FamilyMember(
            name = "",
            online = false,
            onlineUser = OnlineUser.createEmpty()
        )

        fun createForMainUser(user: User) = FamilyMember(
            name = user.name,
            online = true,
            onlineUser = OnlineUser(
                user = user,
                lastRegisterTime = System.currentTimeMillis()
            )
        )

        fun createForOnlineUser(name: String, onlineUser: OnlineUser) = FamilyMember(
            name = name,
            online = System.currentTimeMillis() - onlineUser.lastRegisterTime <= ONLINE_DEBOUNCE,
            onlineUser = OnlineUser(
                user = User.createEmpty(),
                lastRegisterTime = 0
            )
        )
    }
}

