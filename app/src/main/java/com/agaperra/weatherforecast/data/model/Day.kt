package com.agaperra.weatherforecast.data.model

import com.google.gson.annotations.SerializedName

/*
Copyright (c) 2021 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */


data class Day (

	@SerializedName("maxtemp_c") val maxtemp_c : Double,
	@SerializedName("maxtemp_f") val maxtemp_f : Double,
	@SerializedName("mintemp_c") val mintemp_c : Double,
	@SerializedName("mintemp_f") val mintemp_f : Double,
	@SerializedName("avgtemp_c") val avgtemp_c : Double,
	@SerializedName("avgtemp_f") val avgtemp_f : Double,
	@SerializedName("maxwind_mph") val maxwind_mph : Double,
	@SerializedName("maxwind_kph") val maxwind_kph : Double,
	@SerializedName("totalprecip_mm") val totalprecip_mm : Double,
	@SerializedName("totalprecip_in") val totalprecip_in : Double,
	@SerializedName("avgvis_km") val avgvis_km : Double,
	@SerializedName("avgvis_miles") val avgvis_miles : Double,
	@SerializedName("avghumidity") val avghumidity : Double,
	@SerializedName("daily_will_it_rain") val daily_will_it_rain : Double,
	@SerializedName("daily_chance_of_rain") val daily_chance_of_rain : Double,
	@SerializedName("daily_will_it_snow") val daily_will_it_snow : Double,
	@SerializedName("daily_chance_of_snow") val daily_chance_of_snow : Double,
	@SerializedName("condition") val condition : Condition,
	@SerializedName("uv") val uv : Double
)