//Problem 1c

/*
For Oshkosh, what 7 day period was the hottest? By hottest I mean, the
average temperature of all readings from 12:00:00am on day K to
11:59:59pm on day K+6. For example, April 30th, 2006 to May 6th, 2006
is a 7 day period. December 29, 2005 to January 4, 2006 is a 7 day
period. Look at all 7 day periods and determine which one is the hottest.
*/

/*
Year,Month,Day,TimeCST,TemperatureF,Dew PointF,Humidity,Sea Level PressureIn,VisibilityMPH,Wind Direction,Wind SpeedMPH,Gust SpeedMPH,PrecipitationIn,Events,Conditions,WindDirDegrees
2000,1,1,12:53 AM,36,30.9,82,29.95,10,SSW,11.5,-,N/A,,Partly Cloudy,200
2000,1,1,1:53 AM,37,30.9,79,29.96,10,SSW,6.9,-,N/A,,Partly Cloudy,210
2000,1,1,2:53 AM,36,30,79,29.96,10,SW,5.8,-,N/A,,Partly Cloudy,220
2000,1,1,3:53 AM,34,28.9,82,29.96,10,Calm,Calm,-,N/A,,Clear,0
2000,1,1,4:53 AM,28.9,26.1,89,29.97,10,Calm,Calm,-,N/A,,Partly Cloudy,0
*/

val weatherW = spark.read.format("csv").option("header", true).option("inferSchema",true).load("/user/maria_dev/final/Oshkosh/OshkoshWeather.csv"
    ).filter($"TemperatureF" > -100 ).select("Year", "Month", "Day", "TemperatureF")

val weatherI = spark.read.format("csv").option("header", true).option("inferSchema",true).load("/user/maria_dev/final/IowaCity/IowaCityWeather.csv"
    ).filter($"TemperatureF" > -100 ).select("Year", "Month", "Day", "TemperatureF")



