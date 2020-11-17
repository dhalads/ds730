//Problem C
/*
Consider the complete taxi dataset that you downloaded in step 2a. What were the top
10 (fare_amount / trip_distance) rows? In other words, what trips cost the most for the
least amount of distance traveled? For this problem, you should not consider any
trip_distances with a value of 0 as all of them would have an infinite fare to distance
ratio. Also do not consider any negative trip_distances. Print your answer in order by
rank (i.e. all rank 1’s first, then all rank 2’s, etc.). If there are ties, use the dense rank
that we’ve used in previous activities. Only print out the (fare_amount / trip_distance)
answer with its corresponding rank.
*/

// VendorID,tpep_pickup_datetime,tpep_dropoff_datetime,passenger_count,trip_distance,pickup_longitude,pickup_latitude,RatecodeID,store_and_fwd_flag,dropoff_longitude,dropoff_latitude,payment_type,fare_amount,extra,mta_tax,tip_amount,tolls_amount,improvement_surcharge,total_amount
// 2,2016-06-09 21:06:36,2016-06-09 21:13:08,2,.79,-73.983360290527344,40.760936737060547,1,N,-73.977462768554688,40.753978729248047,2,6,0.5,0.5,0,0,0.3,7.3
// 2,2016-06-09 21:06:36,2016-06-09 21:35:11,1,5.22,-73.981719970703125,40.736667633056641,1,N,-73.981636047363281,40.670242309570313,1,22,0.5,0.5,4,0,0.3,27.3
// 2,2016-06-09 21:06:36,2016-06-09 21:13:10,1,1.26,-73.994316101074219,40.751071929931641,1,N,-74.004234313964844,40.742168426513672,1,6.5,0.5,0.5,1.56,0,0.3,9.36
// 2,2016-06-09 21:06:36,2016-06-09 21:36:10,1,7.39,-73.98236083984375,40.773891448974609,1,N,-73.929466247558594,40.851539611816406,1,26,0.5,0.5,1,0,0.3,28.3

sc.setLogLevel("WARN")
import org.apache.spark.sql.expressions._

val taxi = spark.read.format("csv").option("header", true).option("inferSchema",true).load("/user/zeppelin/taxi.csv").select($"fare_amount", $"trip_distance")

val windowSpec = Window.orderBy($"costPerDistance".desc)

val taxi2 = taxi.filter(col("trip_distance")>0).withColumn("costPerDistance", col("fare_amount")/col("trip_distance"))

val taxi3 = taxi2.withColumn("dense_rank", dense_rank().over(windowSpec))

taxi3.createOrReplaceTempView("taxiView")

val output = spark.sqlContext.sql("SELECT costPerDistance, dense_rank FROM taxiView where dense_rank<=10 ORDER BY dense_rank")
output.show()

