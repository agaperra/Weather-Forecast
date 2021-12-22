package com.agaperra.weatherforecast.presentation.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.agaperra.weatherforecast.R
import com.agaperra.weatherforecast.domain.model.AppState
import com.agaperra.weatherforecast.domain.model.City
import com.agaperra.weatherforecast.domain.model.ForecastDay
import com.agaperra.weatherforecast.domain.model.UnitsType
import com.agaperra.weatherforecast.presentation.components.ExpandableCard
import com.agaperra.weatherforecast.presentation.theme.ralewayFontFamily
import com.agaperra.weatherforecast.presentation.viewmodel.SearchViewModel

@ExperimentalMaterialApi
@Composable
fun SearchScreenContent(
    onCityClicked: (Pair<Double, Double>) -> Unit,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    val searchedCities by searchViewModel.citiesList.collectAsState()

    when (searchedCities) {
        is AppState.Error -> EmptyContent()
        is AppState.Loading -> EmptyContent()
        is AppState.Success -> searchedCities.data?.let { cities ->
            DisplayCities(
                cities = cities,
                onCityClicked = { onCityClicked(it) }
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun DisplayCities(
    cities: List<City>,
    onCityClicked: (Pair<Double, Double>) -> Unit
) {
    var lastExpandedItemPosition by remember { mutableStateOf(-1) }

    LazyColumn(contentPadding = PaddingValues(horizontal = 15.dp, vertical = 10.dp)) {
        items(items = cities, key = { it.name }) { city ->
            CityItem(
                city = city,
                onCityClicked = {
                    val currentCityPosition = cities.indexOf(city)
                    lastExpandedItemPosition =
                        if (currentCityPosition != lastExpandedItemPosition) {
                            onCityClicked(it)
                            currentCityPosition
                        } else -1
                },
                isExpanded = lastExpandedItemPosition != -1 && cities[lastExpandedItemPosition] == city
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun CityItem(
    city: City,
    onCityClicked: (Pair<Double, Double>) -> Unit,
    isExpanded: Boolean,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    val dayForecast by searchViewModel.dayForecast.collectAsState()

    ExpandableCard(
        title = city.name,
        onCardClick = { onCityClicked(Pair(city.latitude, city.longitude)) },
        descriptionBlock = {
            when (dayForecast) {
                is AppState.Error -> DayForecastError()
                is AppState.Loading -> DayForecastLoading()
                is AppState.Success -> dayForecast.data?.let { CityDayForecast(dayForecast = it) }
            }
        },
        isExpanded = isExpanded,
        textFontFamily = ralewayFontFamily
    )
}

@Composable
fun DayForecastLoading() {
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.Red)
    }
}

@Composable
fun DayForecastError() {

}

@Composable
fun CityDayForecast(
    searchViewModel: SearchViewModel = hiltViewModel(),
    dayForecast: ForecastDay) {

    val unitsState by searchViewModel.unitsSettings.collectAsState()
    var size by remember { mutableStateOf(IntSize.Zero) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(5.dp)
            .onGloballyPositioned { size = it.size }
    ) {
        Column(
            modifier = Modifier.weight(1.4f).fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(bottom = 10.dp),
                text = dayForecast.dayName,
                color = Color.Black,
                fontFamily = ralewayFontFamily,
                fontSize = 18.sp
            )
            Icon(
                painter = painterResource(id = dayForecast.dayIcon),
                contentDescription = stringResource(id = R.string.day_status_icon),
                modifier = Modifier.size(50.dp),
                tint = Color.Unspecified
            )
            Text(
                text = dayForecast.dayTemp + when (unitsState) {
                    UnitsType.METRIC -> "°"
                    else -> "°F"
                },
                color = Color.Black,
                fontFamily = ralewayFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp
            )
            Text(
                text = dayForecast.dayStatus,
                color = Color.Black,
                fontFamily = ralewayFontFamily,
                fontWeight = FontWeight.Medium
            )
        }
        Column(
            modifier = Modifier
                .weight(4f)
                .height(with(LocalDensity.current) { size.height.toDp() }),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_sunrise),
                            contentDescription = stringResource(id = R.string.sunrise_icon)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = dayForecast.sunrise,
                            color = Color.Black,
                            fontFamily = ralewayFontFamily,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_sunset),
                            contentDescription = stringResource(id = R.string.sunset_icon)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = dayForecast.sunset,
                            color = Color.Black,
                            fontFamily = ralewayFontFamily,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
            Row {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_humidity),
                            contentDescription = stringResource(id = R.string.humidity_icon)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = dayForecast.dayHumidity,
                            color = Color.Black,
                            fontFamily = ralewayFontFamily,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_wind_icon),
                            contentDescription = stringResource(id = R.string.day_wind_icon)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = dayForecast.dayWindSpeed + when (unitsState) {
                                UnitsType.METRIC -> stringResource(id = R.string.m_s)
                                else -> stringResource(id = R.string.f_s)
                            },
                            color = Color.Black,
                            fontFamily = ralewayFontFamily,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_pressure),
                    contentDescription = stringResource(id = R.string.day_pressure_icon)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = dayForecast.dayPressure +" "+ stringResource(id = R.string.pressure_units_mm_hg),
                    fontFamily = ralewayFontFamily)
            }
        }
    }
}
