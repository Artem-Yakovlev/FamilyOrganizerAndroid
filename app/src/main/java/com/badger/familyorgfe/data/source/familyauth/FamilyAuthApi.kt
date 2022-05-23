package com.badger.familyorgfe.data.source.familyauth

import com.badger.familyorgfe.data.source.familyauth.json.AcceptInviteJson
import com.badger.familyorgfe.data.source.familyauth.json.CreateJson
import com.badger.familyorgfe.data.source.familyauth.json.GetAllJson
import com.badger.familyorgfe.data.source.familyauth.json.LeaveJson
import retrofit2.http.Body
import retrofit2.http.POST

interface FamilyAuthApi {

    companion object {
        private const val API = "/familyAuth"
        private const val GET_ALL = "$API/getAll"
        private const val ACCEPT_INVITE = "$API/acceptInvite"
        private const val LEAVE = "$API/leave"
        private const val CREATE = "$API/create"
    }

    @POST(GET_ALL)
    suspend fun getAll(@Body form: GetAllJson.Form): GetAllJson.Response

    @POST(ACCEPT_INVITE)
    suspend fun acceptInvite(@Body form: AcceptInviteJson.Form): AcceptInviteJson.Response

    @POST(LEAVE)
    suspend fun leave(@Body form: LeaveJson.Form): LeaveJson.Response

    @POST(CREATE)
    suspend fun create(@Body form: CreateJson.Form): CreateJson.Response

}