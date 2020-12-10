//Problem 1b

/*
When I moved from Wisconsin to Iowa for school, the summers and
winters seemed similar but the spring and autumn seemed much more
tolerable. For this problem, we will be using meteorological seasons:
Winter - Dec, Jan, Feb
Spring - Mar, Apr, May
Summer - Jun, Jul, Aug
Fall - Sep, Oct, Nov
Compute the average temperature (sum all temperatures in the time
period and divide by the number of readings) for each season for Oshkosh
and Iowa City. What is the difference in average temperatures for each
season for Oshkosh vs Iowa City?
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

val extraW = weatherW.withColumn("Season", 
    when(col("Month") === 12 || col("Month") === 1 || col("Month") === 2, "Winter")
    .when(col("Month") === 3 || col("Month") === 4 || col("Month") === 5, "Spring")
    .when(col("Month") === 6 || col("Month") === 7 || col("Month") === 8 , "Summer")
    .otherwise("Fall")
    ).groupBy("Season").agg(avg("TemperatureF").as("avgTemp"))

val extraI = weatherI.withColumn("Season", 
    when(col("Month") === 12 || col("Month") === 1 || col("Month") === 2, "Winter")
    .when(col("Month") === 3 || col("Month") === 4 || col("Month") === 5, "Spring")
    .when(col("Month") === 6 || col("Month") === 7 || col("Month") === 8 , "Summer")
    .otherwise("Fall")
    ).groupBy("Season").agg(avg("TemperatureF").as("avgTemp"))



    extraW.show(false)
    extraI.show(false)

val join1 = extraW.alias("a").join(extraI.alias("b"), "Season").withColumn("Difference",
$"a.avgTemp" - $"b.avgTemp"
)
val join2 = join1.select($"Season", $"a.avgTemp".alias("WAvgTemp"), $"b.avgTemp".alias("IAvgTemp"), $"Difference")
join2.show(false)

