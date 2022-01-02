package com.badger.familyorgfe.base

import kotlinx.coroutines.flow.Flow

abstract class FlowUseCase<I, O> {

    abstract operator fun invoke(arg: I): Flow<O>
}