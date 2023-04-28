package com.mhmd.core.domain

data class Pagination<T>(
    val totalResults: Int?,
    val page: Int?,
    val totalPages: Int?,
    val results: List<T>?
)
