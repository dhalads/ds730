val taxi = spark.read.format("csv").option("header", true).option("inferSchema",
true).load("smaller.csv")
val stamp = taxi.withColumn("timestamp",
unix_timestamp($"tpep_pickup_datetime", "MM/dd/yyyy HH:mm"))
import org.apache.spark.sql.expressions._
val windowSpec = Window.partitionBy("VendorID").orderBy("timestamp").rowsBetween(-4000,4000)
val answer = stamp.withColumn("someAverage", avg(stamp("fare_amount")).over(windowSpec))
answer.select("VendorID", "tpep_pickup_datetime", "timestamp", "fare_amount","someAverage").show()
val windowSpec = Window.partitionBy("VendorID").orderBy("timestamp").rangeBetween(-1800,1800)
