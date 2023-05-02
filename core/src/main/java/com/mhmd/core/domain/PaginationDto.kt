package com.mhmd.core.domain

import com.mhmd.core.domain.Pagination
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaginationDto<T>(
    @SerialName("total_results")
    val totalResults: Int?,
    val page: Int?,
    @SerialName("total_pages")
    val totalPages: Int?,
    val results: List<T>?
)
fun <T, R>  PaginationDto<T>.toPagination(transform: (T) -> R?): Pagination<R> {
    return Pagination(
        totalPages = totalPages,
        totalResults = totalResults,
        results = results?.mapNotNull { transform(it) },
        page = page
    )
}