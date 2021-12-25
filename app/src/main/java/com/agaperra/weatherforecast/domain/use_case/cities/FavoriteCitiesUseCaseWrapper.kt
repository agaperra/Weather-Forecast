package com.agaperra.weatherforecast.domain.use_case.cities

import javax.inject.Inject

data class FavoriteCitiesUseCaseWrapper @Inject constructor(
    val addCityToFavorite: AddCityToFavorite,
    val removeCityFromFavorite: RemoveCityFromFavorite,
    val getFavoriteCities: GetFavoriteCities,
    val searchFavoriteCities: SearchFavoriteCities
)
