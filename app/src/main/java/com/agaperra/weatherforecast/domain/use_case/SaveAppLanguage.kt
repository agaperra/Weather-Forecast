package com.agaperra.weatherforecast.domain.use_case

import com.agaperra.weatherforecast.domain.repository.DataStoreRepository
import java.util.*
import javax.inject.Inject

class SaveAppLanguage @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
    suspend operator fun invoke(language: Locale) {
        dataStoreRepository.persistLanguage(language = language.toLanguageTag())
    }
}