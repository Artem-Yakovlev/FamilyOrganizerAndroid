package com.badger.familyorgfe.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    open fun clearData() {}
}