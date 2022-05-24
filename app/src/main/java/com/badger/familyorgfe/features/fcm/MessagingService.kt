package com.badger.familyorgfe.features.fcm

import com.badger.familyorgfe.features.fcm.domain.SendFcmTokenUseCase
import com.google.firebase.messaging.FirebaseMessagingService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var sendFcmTokenUseCase: SendFcmTokenUseCase

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        runBlocking {
            sendFcmTokenUseCase(token)
        }
    }

}