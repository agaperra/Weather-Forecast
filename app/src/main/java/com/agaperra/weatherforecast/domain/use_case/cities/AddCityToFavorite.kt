package com.agaperra.weatherforecast.domain.use_case.cities

import com.agaperra.weatherforecast.domain.model.CityItem
import com.agaperra.weatherforecast.domain.repository.CitiesRepository
import javax.inject.Inject

class AddCityToFavorite @Inject constructor(
    private val citiesRepository: CitiesRepository
) {

    suspend operator fun invoke(cityItem: CityItem) = citiesRepository.saveCity(cityItem = cityItem)

}