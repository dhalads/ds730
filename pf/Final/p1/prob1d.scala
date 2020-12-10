//Problem 1d

/*
Solve this problem for Oshkosh only. For each day in the input file (e.g.
February 1, 2004, May 11, 2010, January 29, 2007), determine the coldest
time for that day. The coldest time for any given day is defined as the
hour(s) that has/have the coldest average. For example, a day may have
had two readings during the 4am hour, one at 4:15am and one at 4:45am.
The temperatures may have been 10.5 and 15.3. The average for 4am is
12.9. The 5am hour for that day may have had two readings at 5:14am
and 5:35am and those readings were 11.3 and 11.5. The average for 5am
is 11.4. 5am is thus considered colder. If multiple hours have the same
coldest average temperature on any given day, then those hours that have
the coldest average are all considered the coldest for that day. Once you
have determined the coldest hour for each day, return the hour that has
the highest frequency. This is not a windowing problem. You only need to
consider the 24 “hours” of the day, i.e. 12am, 1am, 2am, etc.
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

val extraW = weatherW.withColumn("dateString", 
    concat($"Year", lit("-"), format_string("%02d", $"Month"), lit("-"), format_string("%02d", $"Day"), lit(" "), $"TimeCST"  )
    ).withColumn("totalSeconds", 
    unix_timestamp($"dateString", "yyyy-MM-dd h:mm a")
    ).withColumn("Hour", 
    from_unixtime($"totalSeconds","HH") cast "Int"
    )

val windowSpec2 = Window.partitionBy("Year", "Month", "Day").orderBy("avgTemp")

val extraW2 = extraW.groupBy("Year", "Month", "Day", "Hour").agg(avg("TemperatureF").as("avgTemp")
).orderBy("Year", "Month", "Day", "Hour"
).withColumn("dense_rank", dense_rank().over(windowSpec2)
).filter($"dense_rank" <=1
).orderBy("Year", "Month", "Day")

extraW2.show(40, false)

val answer = extraW2.groupBy("Hour").agg(count("avgTemp").as("daysColdestHour")).orderBy($"daysColdestHour".desc)
answer.select("Hour").limit(1).show(24, false)
