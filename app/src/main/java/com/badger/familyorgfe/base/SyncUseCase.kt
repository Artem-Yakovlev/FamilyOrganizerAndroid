package com.badger.familyorgfe.base

abstract class SyncUseCase<I, O> {

    abstract suspend operator fun invoke(arg: I): O
}