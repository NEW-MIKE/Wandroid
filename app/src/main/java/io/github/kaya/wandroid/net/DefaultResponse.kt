package io.github.kaya.wandroid.net

class DefaultResponse<T>(
    override val code: Int,
    override val msg: String?,
    override val data: T?
) : IResponse<T>