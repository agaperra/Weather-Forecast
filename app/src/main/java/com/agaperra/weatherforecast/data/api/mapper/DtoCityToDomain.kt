package com.agaperra.weatherforecast.data.api.mapper

import com.agaperra.weatherforecast.data.api.dto.CityListResponse
import com.agaperra.weatherforecast.domain.model.City
import javax.inject.Inject

class DtoCityToDomain  @Inject constructor(){

    fun map(cityListResponse: CityListResponse):List<City> =
        cityListResponse.data.map {
            City(name = it.name, longitude = it.longitude, latitude = it.latitude)

    }


}
