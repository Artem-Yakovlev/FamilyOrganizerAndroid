package com.badger.familyorgfe.base

abstract class BaseUseCase<I, O> {

    abstract suspend operator fun invoke(arg: I): O
}