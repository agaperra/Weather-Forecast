import com.google.gson.annotations.SerializedName

/*
Copyright (c) 2021 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */


data class Current (

	@SerializedName("last_updated_epoch") val last_updated_epoch : Int,
	@SerializedName("last_updated") val last_updated : String,
	@SerializedName("temp_c") val temp_c : Int,
	@SerializedName("temp_f") val temp_f : Double,
	@SerializedName("is_day") val is_day : Int,
	@SerializedName("condition") val condition : Condition,
	@SerializedName("wind_mph") val wind_mph : Double,
	@SerializedName("wind_kph") val wind_kph : Double,
	@SerializedName("wind_degree") val wind_degree : Int,
	@SerializedName("wind_dir") val wind_dir : String,
	@SerializedName("pressure_mb") val pressure_mb : Int,
	@SerializedName("pressure_in") val pressure_in : Double,
	@SerializedName("precip_mm") val precip_mm : Int,
	@SerializedName("precip_in") val precip_in : Int,
	@SerializedName("humidity") val humidity : Int,
	@SerializedName("cloud") val cloud : Int,
	@SerializedName("feelslike_c") val feelslike_c : Int,
	@SerializedName("feelslike_f") val feelslike_f : Double,
	@SerializedName("vis_km") val vis_km : Int,
	@SerializedName("vis_miles") val vis_miles : Int,
	@SerializedName("uv") val uv : Int,
	@SerializedName("gust_mph") val gust_mph : Int,
	@SerializedName("gust_kph") val gust_kph : Double
)