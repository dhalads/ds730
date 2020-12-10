//Problem 1e

/*
Which city had a time period of 24 hours or less that saw the largest
temperature difference? Report the city, the temperature difference and
the minimum amount of time it took to obtain that difference. Do not only
consider whole days for this problem. The largest temperature difference
may have been from 3pm on a Tuesday to 3pm on a Wednesday. The
largest temperature difference could have been from 11:07am on a
Tuesday to 4:03am on a Wednesday. Or the largest difference could have
been from 3:06pm on a Wednesday to 7:56pm on that same Wednesday.
For a concrete example, consider Iowa City on January 1, 2000 at 2:53pm
through January 2, 2000 at 2:53pm. The maximum temperature in that 24
hour span was 54 and the minimum temperature in that 24 hour span was
36. Therefore, in that 24 hour span, the largest temperature difference
was 18 degrees. If this were the final answer, you would output “Iowa
City”, “18 degrees” and January 2, 2000 3:53am to January 2, 2000
10:53am.
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

val extraW = weatherW.withColumn("dateString", 
    concat($"Year", lit("-"), format_string("%02d", $"Month"), lit("-"), format_string("%02d", $"Day"), lit(" "), $"TimeCST"  )
    ).withColumn("totalSeconds", 
    unix_timestamp($"dateString", "yyyy-MM-dd h:mm a")
    ).withColumn("Hour", 
    from_unixtime($"totalSeconds","HH") cast "Int"
    )

val windowSpec = Window.orderBy("totalSeconds").rangeBetween(0,60*60*24-1)
val windowSpec2 = Window.orderBy($"mean_temp".desc)

val extraW2 = extraW.withColumn("min_temp", min(extraW("TemperatureF")).over(windowSpec)
).withColumn("max_temp", max(extraW("TemperatureF")).over(windowSpec)
).withColumn("temp_diff", $"max_temp" - $"min_temp")

extraW2.show(false)

val answerW = extraW2.orderBy($"temp_diff".desc).limit(1)

val seconds24 = answerW.select("totalSeconds").map(r => r.getLong(0)).collect.toList

val answerW2 = extraW2.filter($"totalSeconds" >= seconds24(0) && $"totalSeconds" <= seconds24(0) + 60*60*24-1).orderBy("TemperatureF")
answerW2.show(50, false)
