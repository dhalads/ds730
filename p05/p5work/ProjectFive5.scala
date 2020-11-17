//Problem 5

/*
(15 pts) Who tips better: people who pay with a credit card before noon or people
who pay with credit card from noon until the end of the day (i.e. 12:00:00pm -
11:59:59pm)? We will use the dropoff time to determine when a person paid. The
payment_type has a numeric score of 1 if the person paid by a credit card. To
figure out who tipped the best, take the sum of the tip_amount and divide it by
the sum of the fare_amount. You should print out the (sum(tip_amount) /
sum(fare_amount)) for each of the 2 requested groups.
*/

/*
VendorID,tpep_pickup_datetime,tpep_dropoff_datetime,passenger_count,trip_distance,RatecodeID,store_and_fwd_flag,PULocationID,DOLocationID,payment_type,fare_amount,extra,mta_tax,tip_amount,tolls_amount,improvement_surcharge,total_amount
2,11/21/2018 07:20:05 PM,11/21/2018 07:21:49 PM,1,0.38,1,N,142,142,1,3.5,1,0.5,1.06,0,0.3,6.36
2,11/21/2018 07:17:42 PM,11/21/2018 07:24:37 PM,1,1.38,1,N,166,151,2,7,1,0.5,0,0,0.3,8.8
1,11/21/2018 07:07:19 PM,11/21/2018 07:34:31 PM,1,5,1,N,136,182,2,20,1,0.5,0,0,0.3,21.8
1,11/21/2018 07:12:45 PM,11/21/2018 07:16:51 PM,1,0.8,1,N,158,90,2,5,1,0.5,0,0,0.3,6.8
1,11/21/2018 07:17:59 PM,11/21/2018 07:29:29 PM,0,1.4,1,N,48,142,2,9,1,0.5,0,0,0.3,10.8
*/

val taxi = spark.read.format("csv").option("header", true).option("inferSchema",true).load("/user/zeppelin/taxi/taxi2018.csv").select($"tpep_dropoff_datetime", $"payment_type", $"fare_amount", $"tip_amount")

val withExtra = taxi.filter(col("payment_type")===1).withColumn("AM_or_PM", 
    // amPmFormat.format(to_date($"tpep_dropoff_datetime", "MM/dd/yyyy hh:mm:ss a"))
    // date_format(to_date($"tpep_dropoff_datetime", "MM/dd/yyyy hh:mm:ss a"), "a")
    // date_format(to_date($"tpep_dropoff_datetime"), "a")
    from_unixtime(unix_timestamp($"tpep_dropoff_datetime", "MM/dd/yyyy hh:mm:ss a"), "a")
    // month(col("tpep_dropoff_datetime"))
    )

withExtra.createOrReplaceTempView("taxiView")

val output = spark.sqlContext.sql("SELECT AM_or_PM, (sum(tip_amount)/sum(fare_amount)) AS ratioTipToFare, COUNT(*) as dayCount FROM taxiView GROUP BY AM_or_PM")
output.show()
