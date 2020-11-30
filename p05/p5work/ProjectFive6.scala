//Problem 6

/*
(15 pts) What are the top ten worst rides with respect to time and distance? In
other words, who sat in the taxi the longest and went the shortest distance? You
should remove any ride whose trip_distance is 0. We want to maximize the
following calculation:
(number of seconds in the taxi / trip_distance)
Your code should print out the entire row along with the new column representing
the calculation in descending order. You only need to print out 10 answers and
do not need to worry about ranks.
*/

/*
VendorID,tpep_pickup_datetime,tpep_dropoff_datetime,passenger_count,trip_distance,RatecodeID,store_and_fwd_flag,PULocationID,DOLocationID,payment_type,fare_amount,extra,mta_tax,tip_amount,tolls_amount,improvement_surcharge,total_amount
2,11/21/2018 07:20:05 PM,11/21/2018 07:21:49 PM,1,0.38,1,N,142,142,1,3.5,1,0.5,1.06,0,0.3,6.36
2,11/21/2018 07:17:42 PM,11/21/2018 07:24:37 PM,1,1.38,1,N,166,151,2,7,1,0.5,0,0,0.3,8.8
1,11/21/2018 07:07:19 PM,11/21/2018 07:34:31 PM,1,5,1,N,136,182,2,20,1,0.5,0,0,0.3,21.8
1,11/21/2018 07:12:45 PM,11/21/2018 07:16:51 PM,1,0.8,1,N,158,90,2,5,1,0.5,0,0,0.3,6.8
1,11/21/2018 07:17:59 PM,11/21/2018 07:29:29 PM,0,1.4,1,N,48,142,2,9,1,0.5,0,0,0.3,10.8
*/


val taxi = spark.read.format("csv").option("header", true).option("inferSchema",true).load("/user/zeppelin/taxi/taxi2018.csv")
// .select($"tpep_pickup_datetime", $"tpep_dropoff_datetime", $"trip_distance")

// val withExtra = taxi.filter(col("trip_distance")>0).withColumn("duration",
//     (unix_timestamp($"tpep_dropoff_datetime", "MM/dd/yyyy hh:mm:ss a") - unix_timestamp($"tpep_pickup_datetime", "MM/dd/yyyy hh:mm:ss a"))
//     ).withColumn(
//     "durationPerDistance", 
//     (unix_timestamp($"tpep_dropoff_datetime", "MM/dd/yyyy hh:mm:ss a") - unix_timestamp($"tpep_pickup_datetime", "MM/dd/yyyy hh:mm:ss a"))/col("trip_distance")
//     )

val withExtra = taxi.filter(col("trip_distance")>0).withColumn(
    "durationPerDistance", 
    (unix_timestamp($"tpep_dropoff_datetime", "MM/dd/yyyy hh:mm:ss a") - unix_timestamp($"tpep_pickup_datetime", "MM/dd/yyyy hh:mm:ss a"))/col("trip_distance")
    )

withExtra.createOrReplaceTempView("taxiView")

// val output = spark.sqlContext.sql("SELECT tpep_dropoff_datetime AS tpep_dropoff_datetimexxxxxxxxxx,tpep_pickup_datetime AS tpep_pickup_datetimexxxxxxxxxx, trip_distance, duration, durationPerDistance FROM taxiView ORDER BY durationPerDistance DESC")
val output = spark.sqlContext.sql("SELECT * FROM taxiView ORDER BY durationPerDistance DESC LIMIT 10")
output.show(false)
