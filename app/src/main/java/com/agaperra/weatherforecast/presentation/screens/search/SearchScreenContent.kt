package com.agaperra.weatherforecast.presentation.screens.search

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
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
import com.agaperra.weatherforecast.domain.model.CityItem
import com.agaperra.weatherforecast.domain.model.ForecastDay
import com.agaperra.weatherforecast.domain.model.UnitsType
import com.agaperra.weatherforecast.presentation.components.ExpandableCard
import com.agaperra.weatherforecast.presentation.theme.ralewayFontFamily
import com.agaperra.weatherforecast.presentation.theme.secondOrangeDawn
import com.agaperra.weatherforecast.presentation.viewmodel.SearchViewModel
import kotlinx.coroutines.delay

@ExperimentalMaterialApi
@Composable
fun SearchScreenContent(
    onSearchedCityClicked: (Pair<Double, Double>) -> Unit,
    onFavoriteCityClicked: (Pair<Double, Double>) -> Unit,
    onAddCityToFavoriteClicked: (CityItem) -> Unit,
    onRemoveCityFromFavoriteClicked: (CityItem) -> Unit,
    searchViewModel: SearchViewModel = hiltViewModel()
) {
    val searchedCities by searchViewModel.searchedCitiesList.collectAsState()
    val favoriteCities by searchViewModel.favoriteCitiesList.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        if (favoriteCities is AppState.Success) favoriteCities.data?.let { favoriteCities ->
            Text(
                text = "Favorite Cities",
                color = Color.Black,
                modifier = Modifier.padding(start = 15.dp, top = 10.dp, bottom = 5.dp),
                fontFamily = ralewayFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 21.sp
            )
            DisplayCities(
                cities = favoriteCities,
                onCityClicked = { onFavoriteCityClicked(it) },
                onAddCityToFavoriteClicked = { onAddCityToFavoriteClicked(it) },
                onRemoveCityFromFavoriteClicked = { onRemoveCityFromFavoriteClicked(it) }
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(fraction = .8f)
                    .height(1.dp)
                    .background(Color.Black)
                    .align(CenterHorizontally)
            )
        }
        when (searchedCities) {
            is AppState.Error -> EmptyContent()
            is AppState.Loading -> CitiesLoading()
            is AppState.Success -> searchedCities.data?.let { searchedCities ->
                DisplayCities(
                    cities = searchedCities,
                    onCityClicked = { onSearchedCityClicked(it) },
                    onAddCityToFavoriteClicked = { onAddCityToFavoriteClicked(it) }
                )
            }
        }
    }
}

@Composable
fun CitiesLoading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(modifier = Modifier.size(30.dp), color = secondOrangeDawn)
    }
}

@ExperimentalMaterialApi
@Composable
fun DisplayCities(
    cities: List<CityItem>,
    onCityClicked: (Pair<Double, Double>) -> Unit,
    onAddCityToFavoriteClicked: (CityItem) -> Unit,
    onRemoveCityFromFavoriteClicked: (CityItem) -> Unit = {}
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
                isExpanded = lastExpandedItemPosition != -1 &&
                        cities[lastExpandedItemPosition] == city,
                onAddCityToFavoriteClicked = { onAddCityToFavoriteClicked(city) },
                onRemoveCityFromFavoriteClicked = { onRemoveCityFromFavoriteClicked(city) }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalMaterialApi
@Composable
fun CityItem(
    city: CityItem,
    isExpanded: Boolean,
    onCityClicked: (Pair<Double, Double>) -> Unit,
    onAddCityToFavoriteClicked: () -> Unit,
    onRemoveCityFromFavoriteClicked: () -> Unit,
    searchViewModel: SearchViewModel = hiltViewModel(),
) {
    val dayForecast by searchViewModel.dayForecast.collectAsState()

    var popupExpanded by remember { mutableStateOf(false) }
    var parentSize by remember { mutableStateOf(IntSize.Zero) }

    val dismissState = rememberDismissState()
    val dismissDirection = dismissState.dismissDirection
    val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)

    if (isDismissed && dismissDirection == DismissDirection.EndToStart) {
        LaunchedEffect(key1 = Unit) {
            delay(300)
            onRemoveCityFromFavoriteClicked()
        }
    }

    val degrees by animateFloatAsState(
        targetValue = if (dismissState.targetValue == DismissValue.Default) 0f else -45f
    )

    var itemAppear by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = true) { itemAppear = true }

    Box(modifier = Modifier.onGloballyPositioned { parentSize = it.size }) {
        if (city.isFavorite) {
            AnimatedVisibility(
                visible = itemAppear && !isDismissed,
                enter = expandVertically(animationSpec = tween(durationMillis = 300)),
                exit = shrinkVertically(animationSpec = tween(durationMillis = 300))
            ) {
                SwipeToDismiss(
                    state = dismissState,
                    directions = setOf(DismissDirection.EndToStart),
                    dismissThresholds = { FractionalThreshold(fraction = 1f) },
                    background = { RedBackground(degrees = degrees) }
                ) {
                    ExpandableCard(
                        title = city.name,
                        onCardClick = { onCityClicked(Pair(city.latitude, city.longitude)) },
                        onMoreButtonClicked = { if (!popupExpanded) popupExpanded = true },
                        descriptionBlock = {
                            when (dayForecast) {
                                is AppState.Error -> DayForecastError()
                                is AppState.Loading -> DayForecastLoading()
                                is AppState.Success -> dayForecast.data?.let {
                                    CityDayForecast(dayForecast = it)
                                }
                            }
                        },
                        isExpanded = isExpanded,
                        textFontFamily = ralewayFontFamily,
                        showOptions = false
                    )
                }
            }
        } else {
            ExpandableCard(
                title = city.name,
                onCardClick = { onCityClicked(Pair(city.latitude, city.longitude)) },
                onMoreButtonClicked = { if (!popupExpanded) popupExpanded = true },
                descriptionBlock = {
                    when (dayForecast) {
                        is AppState.Error -> DayForecastError()
                        is AppState.Loading -> DayForecastLoading()
                        is AppState.Success -> dayForecast.data?.let {
                            CityDayForecast(dayForecast = it)
                        }
                    }
                },
                isExpanded = isExpanded,
                textFontFamily = ralewayFontFamily,
                showOptions = true
            )
        }

        if (!city.isFavorite)
            DropdownMenu(
                expanded = popupExpanded,
                onDismissRequest = { popupExpanded = false },
                modifier = Modifier
                    .width(with(LocalDensity.current) { parentSize.width.toDp() })
                    .align(Alignment.BottomEnd)
            ) {
                DropdownMenuItem(onClick = { onAddCityToFavoriteClicked() }) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Add to favorite",
                        modifier = Modifier.align(CenterVertically)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Add to favorite",
                        fontFamily = ralewayFontFamily,
                        modifier = Modifier.align(CenterVertically)
                    )
                }
            }
    }
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
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Text(
            text = "Can not load forecast for this location",
            color = secondOrangeDawn,
            fontFamily = ralewayFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp
        )
    }
}

@Composable
fun RedBackground(degrees: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp)
            .background(Color.Red)
            .padding(horizontal = 12.dp),
        contentAlignment = CenterEnd
    ) {
        Icon(
            modifier = Modifier.rotate(degrees = degrees),
            imageVector = Icons.Filled.Delete,
            contentDescription = "Delete Icon",
            tint = Color.White
        )
    }
}

@Composable
fun CityDayForecast(
    searchViewModel: SearchViewModel = hiltViewModel(),
    dayForecast: ForecastDay
) {

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
            modifier = Modifier
                .weight(1.4f)
                .fillMaxHeight(),
            horizontalAlignment = CenterHorizontally
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
                    horizontalAlignment = CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = CenterVertically,
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
                    horizontalAlignment = CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = CenterVertically,
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
                    horizontalAlignment = CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = CenterVertically,
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
                    horizontalAlignment = CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = CenterVertically,
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
                verticalAlignment = CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_pressure),
                    contentDescription = stringResource(id = R.string.day_pressure_icon)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = dayForecast.dayPressure + " " + stringResource(id = R.string.pressure_units_mm_hg),
                    fontFamily = ralewayFontFamily
                )
            }
        }
    }
}
