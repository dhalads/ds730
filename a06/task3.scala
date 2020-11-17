val taxi = spark.read.format("csv").option("header", true).option("inferSchema",
true).load("/user/zeppelin/taxi_test.csv")

taxi.printSchema()

taxi.select($"passenger_count", $"tip_amount").show()
taxi.filter($"passenger_count" > 5).show()

taxi.filter($"passenger_count" > 5).filter($"tip_amount" > 10).select($"passenger_count", $"tip_amount").show()

taxi.groupBy($"passenger_count").avg("tip_amount").show()

taxi.createOrReplaceTempView("taxiView")

spark.sqlContext.sql("SELECT passenger_count, avg(tip_amount) FROM taxiView GROUP BY passenger_count").show()

// There are many questions you could ask of the taxi data. For example, what percentage
// of taxi trips had a toll involved? If you want to check your solution against mine, my
// solution was roughly 5.66%.

