//Problem 1f

/*
As a runner, I want to know when is the best time and place to run. For
each month, provide the hour (e.g. 7am, 5pm, etc) and city that is the best
time to run. The best time and place to run will be defined as the time
where the temperature is as close to 50 as possible. For each month, you
are averaging all temperatures with the same city and same hour and
checking how far that average is from 50 degrees. If there is a tie, a
tiebreaker will be the least windy hour (i.e. the windspeed column) on
average. If there is still a tie, both hours and cities are reported.
*/

/*
Year,Month,Day,TimeCST,TemperatureF,Dew PointF,Humidity,Sea Level PressureIn,VisibilityMPH,Wind Direction,Wind SpeedMPH,Gust SpeedMPH,PrecipitationIn,Events,Conditions,WindDirDegrees
2000,1,1,12:53 AM,36,30.9,82,29.95,10,SSW,11.5,-,N/A,,Partly Cloudy,200
2000,1,1,1:53 AM,37,30.9,79,29.96,10,SSW,6.9,-,N/A,,Partly Cloudy,210
2000,1,1,2:53 AM,36,30,79,29.96,10,SW,5.8,-,N/A,,Partly Cloudy,220
2000,1,1,3:53 AM,34,28.9,82,29.96,10,Calm,Calm,-,N/A,,Clear,0
2000,1,1,4:53 AM,28.9,26.1,89,29.97,10,Calm,Calm,-,N/A,,Partly Cloudy,0
*/
import org.apache.spark.sql.expressions._

val weatherW = spark.read.format("csv").option("header", true).option("inferSchema",true).load("/user/maria_dev/final/Oshkosh/OshkoshWeather.csv"
    ).filter($"TemperatureF" > -100 ).select("Year", "Month", "Day","TimeCST", "TemperatureF")

val weatherI = spark.read.format("csv").option("header", true).option("inferSchema",true).load("/user/maria_dev/final/IowaCity/IowaCityWeather.csv"
    ).filter($"TemperatureF" > -100 ).select("Year", "Month", "Day", "TemperatureF")
