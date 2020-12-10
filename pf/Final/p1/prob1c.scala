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
import org.apache.spark.sql.expressions._

val weatherW = spark.read.format("csv").option("header", true).option("inferSchema",true).load("/user/maria_dev/final/Oshkosh/OshkoshWeather.csv"
    ).filter($"TemperatureF" > -100 ).select("Year", "Month", "Day", "TemperatureF")

val extraW = weatherW.withColumn("dateString", 
    concat($"Year", format_string("%02d", $"Month"), format_string("%02d", $"Day") )
    ).withColumn("totalSeconds", 
    unix_timestamp($"dateString", "yyyyMMdd")
    ).withColumn("dateInt", 
    $"dateString" cast "Int"
    )

val windowSpec = Window.orderBy("totalSeconds").rangeBetween(0,3600*24*7-1)
val windowSpec2 = Window.orderBy($"mean_temp".desc)

val extraW2 = extraW.withColumn("mean_temp", avg(extraW("TemperatureF")).over(windowSpec)
    // ).filter(col("totalSeconds")<=23*3600+0*60+0
    ).withColumn("dense_rank", rank().over(windowSpec2))

extraW2.show(false)

// Used to check answer
// extraW.filter($"dateInt" >= 20120630 && $"dateInt" <= 20120706).agg(avg("TemperatureF")).show()

val answer = extraW2.filter($"dense_rank" <=1).limit(1).withColumn("start",
    from_unixtime($"totalSeconds", "MMM dd, yyyy")
).withColumn("end",
    from_unixtime($"totalSeconds"+3600*24*7-1, "MMM dd, yyyy")
).select("start", "end", "mean_temp")
answer.show()
