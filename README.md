# Weather-Forecast
Weather application using Jetpack Compose.

## Description
Our application allows you to find out the weather conditions in your current location, as well as in any other city. Allows you to save the desired cities and view the current weather in them.

## Technological solutions
The project is completely written in kotlin. Its main difference from a regular project is the use of the Jetpack Compose framework instead of XML.
The SQLite database was used to store user data, such as “favorite cities”. For more convenient work with it, the Room library was used.
The main component of dependency injection is Hilt (an add-on over Dagger).

## Functionality
The main function of the application is to display the weather by the current location of the device. On the main screen, you can see the main current weather by location, as well as the weather forecast for the week with the ability to view in detail each of the days.
<div align=center><img src="https://github.com/agaperra/Weather-Forecast/raw/master/images/Screenshot_1640685725.png" width="300" /> <img src="https://github.com/agaperra/Weather-Forecast/raw/master/images/Screenshot_1640685889.png" width="300" /></div><br>


In addition to the above, the main screen also has the ability to update the weather by clicking on the corresponding update icon, and you can also switch to the settings screen and the search screen.
<div align=center><img src="https://github.com/agaperra/Weather-Forecast/raw/master/images/Screenshot_1640686114.png" width="300" /> <img src="https://github.com/agaperra/Weather-Forecast/raw/master/images/Screenshot_1640686138.png" width="300" /></div><br>

The settings screen allows you to change the metrics system.
<div align=center><img src="https://github.com/agaperra/Weather-Forecast/raw/master/images/Screenshot_1640687808.png" width="300" /> <img src="https://github.com/agaperra/Weather-Forecast/raw/master/images/Screenshot_1640687838.png" width="300" /></div><br>
<div align=center><img src="https://github.com/agaperra/Weather-Forecast/raw/master/images/cBdakWiCDcc.jpg" width="600" /> <img src="https://github.com/agaperra/Weather-Forecast/raw/master/images/qQrScJkHpPQ.jpg" width="600" /></div><br>

The search screen allows you to find the current weather in any city.
The screen also has the ability to add a city to favorites.
<div align=center><img src="https://github.com/agaperra/Weather-Forecast/raw/master/images/Screenshot_1640687960.png" width="200" /> <img src="https://github.com/agaperra/Weather-Forecast/raw/master/images/Screenshot_1640687974.png" width="200" /> <img src="https://github.com/agaperra/Weather-Forecast/raw/master/images/Screenshot_1640687981.png" width="200" /></div><br>
