//Problem 4

/*
(20 pts) Which month has the highest average fare_amount? You should use the
pickup date/time as the month to which a row belongs. You should take the sum
of the fare_amounts and divide it by the total number of rows for that month. To
ensure we have reliable data, you should filter out all rows where the
fare_amount is less than or equal to 0. You should filter out all rows where the
(fare_amount / trip_distance) is greater than 10,000. An obvious side-effect of
this is to filter out all rows with a trip_distance of 0. Your code should print out the
average fare_amount for each month.
*/

/*
VendorID,tpep_pickup_datetime,tpep_dropoff_datetime,passenger_count,trip_distance,RatecodeID,store_and_fwd_flag,PULocationID,DOLocationID,payment_type,fare_amount,extra,mta_tax,tip_amount,tolls_amount,improvement_surcharge,total_amount
2,11/21/2018 07:20:05 PM,11/21/2018 07:21:49 PM,1,0.38,1,N,142,142,1,3.5,1,0.5,1.06,0,0.3,6.36
2,11/21/2018 07:17:42 PM,11/21/2018 07:24:37 PM,1,1.38,1,N,166,151,2,7,1,0.5,0,0,0.3,8.8
1,11/21/2018 07:07:19 PM,11/21/2018 07:34:31 PM,1,5,1,N,136,182,2,20,1,0.5,0,0,0.3,21.8
1,11/21/2018 07:12:45 PM,11/21/2018 07:16:51 PM,1,0.8,1,N,158,90,2,5,1,0.5,0,0,0.3,6.8
1,11/21/2018 07:17:59 PM,11/21/2018 07:29:29 PM,0,1.4,1,N,48,142,2,9,1,0.5,0,0,0.3,10.8
*/

val taxi = spark.read.format("csv").option("header", true).option("inferSchema",true).load("/user/zeppelin/taxi/taxi2018.csv").select($"tpep_pickup_datetime", $"trip_distance", $"fare_amount")

val withTollsPaid = taxi.filter(col("trip_distance")>0 && col("fare_amount")>0).filter((col("fare_amount")/col("trip_distance"))<10000).withColumn("month", 
    month(to_date($"tpep_pickup_datetime", "MM/dd/yyyy HH:mm"))
    // month(unix_timestamp($"tpep_pickup_datetime", "MM/dd/yyyy HH:mm"))
    // month(col("tpep_pickup_datetime"))
    )

withTollsPaid.createOrReplaceTempView("taxiView")

val output = spark.sqlContext.sql("SELECT month, AVG(fare_amount) AS avgFare FROM taxiView GROUP BY month ORDER BY AVG(fare_amount) DESC")
output.show()
