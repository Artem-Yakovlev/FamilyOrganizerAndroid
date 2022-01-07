package com.tydeya.justbalance.base

abstract class SyncUseCase<I, O> {

    abstract suspend operator fun invoke(arg: I): O
}