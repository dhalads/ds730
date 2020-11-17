//Problem B
/*
Consider the complete taxi dataset that you downloaded in step 2a. Which grouping of
fares tips the best? The fare groups you should consider are this: $0 - $24.99, $25 -
$49.99, $50 - $74.99, $75+. When considering the best tip, you should consider the tip
as a percentage of the fare. You should sum up the total tips for each group and divide
it by the total fares for each group to get the percentage. Print out each grouping along
with it’s tip percentage. Do not do any rounding.
*/

// VendorID,tpep_pickup_datetime,tpep_dropoff_datetime,passenger_count,trip_distance,pickup_longitude,pickup_latitude,RatecodeID,store_and_fwd_flag,dropoff_longitude,dropoff_latitude,payment_type,fare_amount,extra,mta_tax,tip_amount,tolls_amount,improvement_surcharge,total_amount
// 2,2016-06-09 21:06:36,2016-06-09 21:13:08,2,.79,-73.983360290527344,40.760936737060547,1,N,-73.977462768554688,40.753978729248047,2,6,0.5,0.5,0,0,0.3,7.3
// 2,2016-06-09 21:06:36,2016-06-09 21:35:11,1,5.22,-73.981719970703125,40.736667633056641,1,N,-73.981636047363281,40.670242309570313,1,22,0.5,0.5,4,0,0.3,27.3
// 2,2016-06-09 21:06:36,2016-06-09 21:13:10,1,1.26,-73.994316101074219,40.751071929931641,1,N,-74.004234313964844,40.742168426513672,1,6.5,0.5,0.5,1.56,0,0.3,9.36
// 2,2016-06-09 21:06:36,2016-06-09 21:36:10,1,7.39,-73.98236083984375,40.773891448974609,1,N,-73.929466247558594,40.851539611816406,1,26,0.5,0.5,1,0,0.3,28.3


sc.setLogLevel("WARN")

val taxi = spark.read.format("csv").option("header", true).option("inferSchema",true).load("/user/zeppelin/taxi.csv").select($"fare_amount", $"tip_amount")


val withFairGroup = taxi.withColumn("fairGroup", 
    when(col("fare_amount") >= 75.00, "$75+")
    .when(col("fare_amount") >= 50.00, "$50 - $74.99")
    .when(col("fare_amount") >= 25.00, "$25 - $49.99")
    .otherwise("$0 - $24.99")
    )

withFairGroup.createOrReplaceTempView("taxiView")

val output = spark.sqlContext.sql("SELECT fairGroup, SUM(tip_amount)/SUM(fare_amount)*100 AS tipPercentage FROM taxiView GROUP BY fairGroup ORDER BY fairGroup")
output.show()

