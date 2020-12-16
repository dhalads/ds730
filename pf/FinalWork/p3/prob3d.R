taxi <- read.df("/user/zeppelin/taxi_test.csv", "csv", header ="true", inferSchema = "true")
head(taxi)
only_passengers <- select(taxi, "passenger_count")
head(only_passengers)
small_fares <- filter(taxi, taxi$fare_amount < 10)
head(summarize(groupBy(taxi, taxi$passenger_count), how_many = n(taxi$passenger_count)))
totals <- summarize(groupBy(taxi, taxi$passenger_count),how_many = n(taxi$passenger_count))
head(arrange(totals, desc(totals$how_many)))
taxi$fare_plus_tip <- taxi$fare_amount + taxi$tip_amount
taxi_updated <- drop(taxi, "tolls_amount")

# createOrReplaceTempView(taxi, "taxi_for_sql")
# large_fares <- sql("SELECT * FROM taxi_for_sql WHERE fare_amount > 100")
# head(large_fares)