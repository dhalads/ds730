//Problem 7

/*
(30 pts) Imagine that you work in NYC. You are considering working for a
ride-sharing company to make a few extra dollars later in the evening. However,
you don't have that much time to spend driving other people around. You realize
you have 60 minutes that you can drive between 4:00:00pm and 11:00:00pm1.
You have determined that any total_amount that is over $200 is not going to be
possible so those ought to be filtered out. You want to maximize the amount of
money you could earn so you want to find the best 60 minute period between
4:00:00pm and 11:00:00pm, inclusive, to drive. You do not care about days. In
other words, a ride that starts at 9:45:13pm on June 12th is in the same 60
minute timeslot as a ride that starts at 9:45:13pm on August 15th.
Your goal is this, find the 60 minute time slot where you maximize the average
(mean) total_amount. You are only considering rides starting (i.e.
tpep_pickup_datetime) between 4:00:00pm and 11:00:00pm (you do not care
when those rides finish) and only rides that are $200 or less. Your answer should
go down to the second. In other words, your answer should be something similar
to 9:47:15pm - 10:47:14pm2. Your entire 60 minute time slot must be between
4:00:00pm and 11:00:00pm, again, inclusive. Your answer should not go outside
of these boundaries. Be sure that your code outputs your answer as a full 60
minute timeslot like this: 6:36:29pm - 7:36:28pm.
*/

/*
VendorID,tpep_pickup_datetime,tpep_dropoff_datetime,passenger_count,trip_distance,RatecodeID,store_and_fwd_flag,PULocationID,DOLocationID,payment_type,fare_amount,extra,mta_tax,tip_amount,tolls_amount,improvement_surcharge,total_amount
2,11/21/2018 07:20:05 PM,11/21/2018 07:21:49 PM,1,0.38,1,N,142,142,1,3.5,1,0.5,1.06,0,0.3,6.36
2,11/21/2018 07:17:42 PM,11/21/2018 07:24:37 PM,1,1.38,1,N,166,151,2,7,1,0.5,0,0,0.3,8.8
1,11/21/2018 07:07:19 PM,11/21/2018 07:34:31 PM,1,5,1,N,136,182,2,20,1,0.5,0,0,0.3,21.8
1,11/21/2018 07:12:45 PM,11/21/2018 07:16:51 PM,1,0.8,1,N,158,90,2,5,1,0.5,0,0,0.3,6.8
1,11/21/2018 07:17:59 PM,11/21/2018 07:29:29 PM,0,1.4,1,N,48,142,2,9,1,0.5,0,0,0.3,10.8
*/

val taxi = spark.read.format("csv").option("header", true).option("inferSchema",true).load("/user/zeppelin/taxi/taxi2018.csv").select($"tpep_pickup_datetime", $"total_amount")

val withExtra = taxi.filter(col("total_amount")<=200).withColumn("pickup_timestamp", 
    to_timestamp($"tpep_pickup_datetime", "MM/dd/yyyy hh:mm:ss a")
    ).withColumn("pickup_hour", 
    hour(col("pickup_timestamp"))
    ).withColumn("pickup_minute", 
    minute(col("pickup_timestamp"))
    ).withColumn("pickup_second", 
    second(col("pickup_timestamp"))
    ).filter(col("pickup_hour")>=16 && col("pickup_minute")>=0 && col("pickup_minute")>=0 && col("pickup_hour")<=22 && col("pickup_minute")<=0 && col("pickup_minute")<=0
    ).withColumn("unix_timestamp", 
    unix_timestamp($"tpep_pickup_datetime", "MM/dd/yyyy hh:mm:ss a")
    ).withColumn("window_start", 
    from_unixtime($"unix_timestamp", "hh:mm:ss a")
    ).withColumn("window_end", 
    from_unixtime($"unix_timestamp"+3600, "hh:mm:ss a")
    ).withColumn("timeslot", 
    concat(col("window_start"),lit(" - "),col("window_end"))
    )

withExtra.printSchema()
// withExtra.createOrReplaceTempView("taxiView")

// val output = spark.sqlContext.sql("SELECT tpep_pickup_datetime, pickup_timestamp FROM taxiView LIMIT 50")
// output.show()

// Save file local folder, delimiter by default is ,
// df.coalesce(1).write.option("header", "true").csv("sample_file.csv")
withExtra.coalesce(1).write.format("csv").option("header","true").mode("overwrite").option("sep",",").save("file:///home/maria_dev/ds730_local/zepplin/test_output2.csv")

// Save file to HDFS
// df.write.format('csv').option('header',True).mode('overwrite').option('sep','|').save('/output.csv')

import org.apache.spark.sql.expressions._

val windowSpec = Window.orderBy("unix_timestamp").rangeBetween(0,3600)
val windowSpec2 = Window.orderBy($"mean_total".desc)


val withExtra2 = withExtra.withColumn("mean_total", avg(withExtra("total_amount")).over(windowSpec)).withColumn("dense_rank", dense_rank().over(windowSpec2))


withExtra2.show()

withExtra2.coalesce(1).write.format("csv").option("header","true").mode("overwrite").option("sep",",").save("/user/zeppelin/p5_output")

val answer = withExtra2.filter($"dense_rank" <= 1).select($"timeslot")
answer.show(false)
// answer.collect()

