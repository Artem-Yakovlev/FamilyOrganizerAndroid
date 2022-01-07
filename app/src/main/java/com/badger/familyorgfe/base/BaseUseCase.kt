package com.badger.familyorgfe.base

abstract class BaseUseCase<I, O> {

    abstract operator fun invoke(arg: I): O
}