package com.mychefassistant.core.utils

abstract class BaseSuspendUseCase<in Request, Response> {
    suspend operator fun invoke(parameter: Request): Result<Response> = try {
        execute(parameter)
    } catch (e: Exception) {
        Result.failure(e)
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameter: Request): Result<Response>
}