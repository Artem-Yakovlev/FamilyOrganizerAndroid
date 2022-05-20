package com.badger.familyorgfe.data.source.family

import com.badger.familyorgfe.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface FamilyApi {

    companion object {
        private const val API = "/family"
        private const val GET_FAMILY = "$API/getFamily"
        private const val GET_ALL_MEMBERS = "$API/getAllMember"
    }

    @POST(GET_ALL_MEMBERS)
    suspend fun getAllMembers(@Body form: GetAllMembersJson.Form): BaseResponse<GetAllMembersJson.Response>

    @POST(GET_FAMILY)
    suspend fun getFamily(@Body form: GetFamilyJson.Form): BaseResponse<GetFamilyJson.Response>
}