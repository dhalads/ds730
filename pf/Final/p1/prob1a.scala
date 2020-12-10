//Problem 1a

/*
In Oshkosh, which is more common: days where the temperature was
really cold (-10 or lower) at any point during the day or days where the
temperature was hot (95 or higher) at any point during the day?
*/

/*
Year,Month,Day,TimeCST,TemperatureF,Dew PointF,Humidity,Sea Level PressureIn,VisibilityMPH,Wind Direction,Wind SpeedMPH,Gust SpeedMPH,PrecipitationIn,Events,Conditions,WindDirDegrees
2000,1,1,12:53 AM,36,30.9,82,29.95,10,SSW,11.5,-,N/A,,Partly Cloudy,200
2000,1,1,1:53 AM,37,30.9,79,29.96,10,SSW,6.9,-,N/A,,Partly Cloudy,210
2000,1,1,2:53 AM,36,30,79,29.96,10,SW,5.8,-,N/A,,Partly Cloudy,220
2000,1,1,3:53 AM,34,28.9,82,29.96,10,Calm,Calm,-,N/A,,Clear,0
2000,1,1,4:53 AM,28.9,26.1,89,29.97,10,Calm,Calm,-,N/A,,Partly Cloudy,0
*/

val weather = spark.read.format("csv").option("header", true).option("inferSchema",true).load("/user/maria_dev/final/Oshkosh/OshkoshWeather.csv").select("Year", "Month", "Day", "TemperatureF")

weather.printSchema()

val answerC = weather.filter($"TemperatureF" > -100).filter($"TemperatureF" <= -10).groupBy("Year", "Month", "Day").agg(count("TemperatureF").as("readingCount"))

answerC.show(false)


val answerH = weather.filter($"TemperatureF" >= 95).groupBy("Year", "Month", "Day").agg(count("TemperatureF").as("readingCount"))

answerH.show(false)

answerC.count()
answerH.count()

