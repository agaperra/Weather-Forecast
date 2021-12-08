package com.agaperra.weatherforecast.domain.model

enum class UnitsType {
    METRIC {
        override val next: UnitsType
            get() = IMPERIAL
    },
    IMPERIAL {
        override val next: UnitsType
            get() = METRIC
    };

    abstract val next: UnitsType
}