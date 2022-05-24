package com.badger.familyorgfe.features.appjourney.common.productbottomsheet

interface IProductEvent {
    fun isBottomSheetClose(): Boolean = false
    fun isTitleChanged() : Boolean = false
    fun isQuantityChanged() : Boolean = false
    fun isMeasureChanged() : Boolean = false
    fun isExpirationDateChanged() : Boolean = false
    fun isExpirationDaysChanged() : Boolean = false
    fun isActionClicked() : Boolean = false
}