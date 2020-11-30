//task4

// reset:
sc.setLogLevel("WARN")

val taxi = spark.read.format("csv").option("header", true).option("inferSchema", true).load("/user/zeppelin/taxi_test.csv")
taxi.collect()
taxi.count()

val stamp = taxi.withColumn("timestamp", unix_timestamp($"tpep_pickup_datetime", "MM/dd/yyyy HH:mm"))
stamp.collect()

import org.apache.spark.sql.expressions._

val windowSpec = Window.partitionBy("VendorID").orderBy("timestamp").rowsBetween(-10,10)

val answer = stamp.withColumn("someAverage", avg(stamp("fare_amount")).over(windowSpec))

answer.select("VendorID", "tpep_pickup_datetime", "timestamp", "fare_amount","someAverage").show()

val windowSpec = Window.partitionBy("VendorID").orderBy("timestamp").rangeBetween(-1800,1800)
