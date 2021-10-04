package com.agaperra.weatherforecast.data.model

import com.google.gson.annotations.SerializedName

/*
Copyright (c) 2021 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */


data class Hour (

    @SerializedName("time_epoch") val time_epoch : Double,
    @SerializedName("time") val time : String,
    @SerializedName("temp_c") val temp_c : Double,
    @SerializedName("temp_f") val temp_f : Double,
    @SerializedName("is_day") val is_day : Double,
    @SerializedName("condition") val condition : Condition,
    @SerializedName("wind_mph") val wind_mph : Double,
    @SerializedName("wind_kph") val wind_kph : Double,
    @SerializedName("wind_degree") val wind_degree : Double,
    @SerializedName("wind_dir") val wind_dir : String,
    @SerializedName("pressure_mb") val pressure_mb : Double,
    @SerializedName("pressure_in") val pressure_in : Double,
    @SerializedName("precip_mm") val precip_mm : Double,
    @SerializedName("precip_in") val precip_in : Double,
    @SerializedName("humidity") val humidity : Double,
    @SerializedName("cloud") val cloud : Double,
    @SerializedName("feelslike_c") val feelslike_c : Double,
    @SerializedName("feelslike_f") val feelslike_f : Double,
    @SerializedName("windchill_c") val windchill_c : Double,
    @SerializedName("windchill_f") val windchill_f : Double,
    @SerializedName("heatindex_c") val heatindex_c : Double,
    @SerializedName("heatindex_f") val heatindex_f : Double,
    @SerializedName("dewpoint_c") val dewpoint_c : Double,
    @SerializedName("dewpoint_f") val dewpoint_f : Double,
    @SerializedName("will_it_rain") val will_it_rain : Double,
    @SerializedName("chance_of_rain") val chance_of_rain : Double,
    @SerializedName("will_it_snow") val will_it_snow : Double,
    @SerializedName("chance_of_snow") val chance_of_snow : Double,
    @SerializedName("vis_km") val vis_km : Double,
    @SerializedName("vis_miles") val vis_miles : Double,
    @SerializedName("gust_mph") val gust_mph : Double,
    @SerializedName("gust_kph") val gust_kph : Double,
    @SerializedName("uv") val uv : Double
)