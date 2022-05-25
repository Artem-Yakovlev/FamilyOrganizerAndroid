package com.badger.familyorgfe.data.source.family

import com.badger.familyorgfe.base.BaseResponse
import com.badger.familyorgfe.data.source.family.json.ExcludeFamilyMemberJson
import com.badger.familyorgfe.data.source.family.json.GetAllMembersJson
import com.badger.familyorgfe.data.source.family.json.GetFamilyJson
import com.badger.familyorgfe.data.source.family.json.InviteFamilyMemberJson
import retrofit2.http.Body
import retrofit2.http.POST

interface FamilyApi {

    companion object {
        private const val API = "/family"
        private const val GET_FAMILY = "$API/getFamily"
        private const val GET_ALL_MEMBERS = "$API/getAllMembers"
        private const val EXCLUDE_FAMILY_MEMBER = "$API/excludeFamilyMember"
        private const val INVITE_FAMILY_MEMBER = "$API/inviteFamilyMember"
    }

    @POST(GET_ALL_MEMBERS)
    suspend fun getAllMembers(@Body form: GetAllMembersJson.Form): BaseResponse<GetAllMembersJson.Response>

    @POST(GET_FAMILY)
    suspend fun getFamily(@Body form: GetFamilyJson.Form): BaseResponse<GetFamilyJson.Response>

    @POST(EXCLUDE_FAMILY_MEMBER)
    suspend fun excludeFamilyMember(@Body form: ExcludeFamilyMemberJson.Form): BaseResponse<ExcludeFamilyMemberJson.Response>

    @POST(INVITE_FAMILY_MEMBER)
    suspend fun inviteFamilyMember(@Body form: InviteFamilyMemberJson.Form): BaseResponse<InviteFamilyMemberJson.Response>
}