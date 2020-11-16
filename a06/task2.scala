// VendorID,tpep_pickup_datetime,tpep_dropoff_datetime,passenger_count,trip_distance,pickup_longitude,pickup_latitude,RatecodeID,store_and_fwd_flag,dropoff_longitude,dropoff_latitude,payment_type,fare_amount,extra,mta_tax,tip_amount,tolls_amount,improvement_surcharge,total_amount
// 2,2016-06-09 21:06:36,2016-06-09 21:13:08,2,.79,-73.983360290527344,40.760936737060547,1,N,-73.977462768554688,40.753978729248047,2,6,0.5,0.5,0,0,0.3,7.3
// 2,2016-06-09 21:06:36,2016-06-09 21:35:11,1,5.22,-73.981719970703125,40.736667633056641,1,N,-73.981636047363281,40.670242309570313,1,22,0.5,0.5,4,0,0.3,27.3
// 2,2016-06-09 21:06:36,2016-06-09 21:13:10,1,1.26,-73.994316101074219,40.751071929931641,1,N,-74.004234313964844,40.742168426513672,1,6.5,0.5,0.5,1.56,0,0.3,9.36
// 2,2016-06-09 21:06:36,2016-06-09 21:36:10,1,7.39,-73.98236083984375,40.773891448974609,1,N,-73.929466247558594,40.851539611816406,1,26,0.5,0.5,1,0,0.3,28.3
// 2,2016-06-09 21:06:36,2016-06-09 21:23:23,1,3.10,-73.987106323242187,40.733173370361328,1,N,-73.985908508300781,40.766445159912109,1,13.5,0.5,0.5,2.96,0,0.3,17.76

case class TaxiRides(VendorID: Integer,
tpep_pickup_datetime: String,
tpep_dropoff_datetime: String,
passenger_count: Integer,
trip_distance: Double,
pickup_longitude: Double,
pickup_latitude: Double,
RatecodeID: Integer,
store_and_fwd_flag: String,
dropoff_longitude: Double,
dropoff_latitude: Double,
payment_type: Integer,
fare_amount: Double,
extra: Double,
mta_tax: Double,
tip_amount: Double,
tolls_amount: Double,
improvement_surcharge: Double,
total_amount: Double)

import org.apache.spark.sql._

val taxi_ds: Dataset[TaxiRides] = spark.sqlContext.read.option("header",
"true").option("delimiter", ",").option("inferSchema",
"true").csv("/user/zeppelin/taxi_test.csv").as[TaxiRides]

taxi_ds.show(50)
taxi_ds.show(40, false)
taxi_ds.count()
taxi_ds.filter(x => x.tip_amount>=300).show()
taxi_ds.map(x => x.total_amount).reduce((x,y) => x+y)
taxi_ds.agg(sum("total_amount")).show()
taxi_ds.groupBy("VendorID").agg(sum("total_amount")).show()
