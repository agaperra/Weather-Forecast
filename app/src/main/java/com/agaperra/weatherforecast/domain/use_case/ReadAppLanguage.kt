package com.agaperra.weatherforecast.domain.use_case

import com.agaperra.weatherforecast.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class ReadAppLanguage @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    operator fun invoke() = dataStoreRepository.readLanguageSettings.map {
        Locale.forLanguageTag(it)
    }
}