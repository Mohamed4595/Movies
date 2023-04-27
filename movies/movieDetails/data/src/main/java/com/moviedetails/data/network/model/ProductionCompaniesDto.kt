package com.moviedetails.data.network.model

import com.mhmd.constants.ImageConstants
import com.moviedetails.domain.ProductionCompanies
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductionCompaniesDto(
    val id: Long? = null,
    val name: String? = null,
    @SerialName("logo_path")
    val logoPath: String? = null,

    )

fun ProductionCompaniesDto.toProductionCompanies(): ProductionCompanies? {
    if (id == null) return null
    if (name == null) return null

    return ProductionCompanies(
        id = id, name = name,
        logoImage = ImageConstants.POSTER_IMAGE_URL + logoPath
    )
}