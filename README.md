# Weather-Forecast
Weather application using Jetpack Compose.

## Description
Our application allows you to find out the weather conditions in your current location, as well as in any other city. Allows you to save the desired cities and view the current weather in them.

## Technological solutions
The project is completely written in kotlin. Its main difference from a regular project is the use of the Jetpack Compose framework instead of XML.
The SQLite database was used to store user data, such as “favorite cities”. For more convenient work with it, the Room library was used.
The main component of dependency injection is Hilt (an add-on over Dagger).
