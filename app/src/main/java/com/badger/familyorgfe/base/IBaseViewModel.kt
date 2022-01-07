package com.badger.familyorgfe.base

interface IBaseViewModel<E> {

    fun onEvent(event: E) {}
}