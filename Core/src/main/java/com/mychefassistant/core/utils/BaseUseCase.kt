package com.mychefassistant.core.utils

abstract class BaseUseCase<in Request, Response> {
    open operator fun invoke(parameter: Request): Result<Response> = try {
        execute(parameter)
    } catch (e: Exception) {
        Result.failure(e)
    }

    @Throws(RuntimeException::class)
    protected abstract fun execute(parameter: Request): Result<Response>
}