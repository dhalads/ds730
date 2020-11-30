//Problem 2

/*
(10 pts) One might posit that a single passenger is the most common taxi ride.
Which was more common: a single passenger_count or a passenger_count with
more than 1 person? Your code should output the total number of rows with a
passenger_count of 1 and the total number of rows with a passenger_count
greater than 1.
*/

/*
VendorID,tpep_pickup_datetime,tpep_dropoff_datetime,passenger_count,trip_distance,RatecodeID,store_and_fwd_flag,PULocationID,DOLocationID,payment_type,fare_amount,extra,mta_tax,tip_amount,tolls_amount,improvement_surcharge,total_amount
2,11/21/2018 07:20:05 PM,11/21/2018 07:21:49 PM,1,0.38,1,N,142,142,1,3.5,1,0.5,1.06,0,0.3,6.36
2,11/21/2018 07:17:42 PM,11/21/2018 07:24:37 PM,1,1.38,1,N,166,151,2,7,1,0.5,0,0,0.3,8.8
1,11/21/2018 07:07:19 PM,11/21/2018 07:34:31 PM,1,5,1,N,136,182,2,20,1,0.5,0,0,0.3,21.8
1,11/21/2018 07:12:45 PM,11/21/2018 07:16:51 PM,1,0.8,1,N,158,90,2,5,1,0.5,0,0,0.3,6.8
1,11/21/2018 07:17:59 PM,11/21/2018 07:29:29 PM,0,1.4,1,N,48,142,2,9,1,0.5,0,0,0.3,10.8
*/

val taxi = spark.read.format("csv").option("header", true).option("inferSchema",true).load("/user/zeppelin/taxi/taxi2018.csv").select($"passenger_count")

val withPassengerGroup = taxi.filter(col("passenger_count")>0).withColumn("passengerGroup", 
    when(col("passenger_count") === 1, "one")
    .when(col("passenger_count") > 1, "more than one")
    .when(col("passenger_count") === 0 , "zero")
    .otherwise("none")
    )

withPassengerGroup.createOrReplaceTempView("taxiView")

val output = spark.sqlContext.sql("SELECT passengerGroup, COUNT(*) AS tripCount FROM taxiView GROUP BY passengerGroup")
output.show(false)
