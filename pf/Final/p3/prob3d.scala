
/* isd-history.csv
,USAF,WBAN,STATION NAME,CTRY,STATE,ICAO,LAT,LON,ELEV(M)
18890,725033,99999,NEW YORK CITY  CENTRAL PARK,US,NY,,40.767,-73.983,27.0
18922,725060,94728,NEW YORK CENTRAL PARK,US,NY,KNYC,40.779,-73.969,47.5
25978,999999,14732,NEW YORK LAGUARDIA ARPT,US,NY,KLGA,40.779,-73.88,9.4
26020,999999,14786,NEW YORK FLOYD BENNE,US,NY,NSC,40.583,-73.883,4.9
26728,999999,93732,NEW YORK SHOALS AFS,US,NY,,39.8,-72.667,25.9
26839,999999,94728,NEW YORK CENTRAL PARK ARSNL B,US,NY,KNYC,40.779,-73.969,47.5
26846,999999,94789,NEW YORK FORT TOTTEN,US,NY,KJFK,40.639,-73.762,6.7
*/

/* isd-inventory.csv
Station-Year,ID,USAF,WBAN,YEAR,Last_Updated,JAN,FEB,MAR,APR,MAY,JUN,JUL,AUG,SEP,OCT,NOV,DEC
725060-94728-2010,725060-94728,725060,94728,2010,2016-09-09 04:26:19.149296,0,0,0,0,0,0,0,166,174,216,210,217
725060-94728-2011,725060-94728,725060,94728,2011,2016-09-09 06:10:01.256996,216,196,217,210,217,210,217,217,210,217,210,217
725060-94728-2012,725060-94728,725060,94728,2012,2016-09-09 08:03:10.963458,217,203,216,209,217,210,216,0,0,0,0,0
*/

/*
ID,USAF,WBAN,Elevation,Country_Code,Latitude,Longitude,Date,Year,Month,Day,Mean_Temp,Mean_Temp_Count,Mean_Dewpoint,Mean_Dewpoint_Count,Mean_Sea_Level_Pressure,Mean_Sea_Level_Pressure_Count,Mean_Station_Pressure,Mean_Station_Pressure_Count,Mean_Visibility,Mean_Visibility_Count,Mean_Windspeed,Mean_Windspeed_Count,Max_Windspeed,Max_Gust,Max_Temp,Max_Temp_Quality_Flag,Min_Temp,Min_Temp_Quality_Flag,Precipitation,Precip_Flag,Snow_Depth,Fog,Rain_or_Drizzle,Snow_or_Ice,Hail,Thunder,Tornado
725060-94728,725060,94728,47.5,US,40.779,-73.969,2011-01-01,2011,01,01,44.8,24,31.9,24,1019.4,23,1014.4,24,6.1,24,3.1,24,7.0,,52.0,1,41.0,1,0.00,G,13.0,0,0,0,0,0,0
725060-94728,725060,94728,47.5,US,40.779,-73.969,2011-01-02,2011,01,02,48.3,24,42.0,24,1013.9,15,1008.9,24,6.2,24,2.4,24,6.0,,51.1,1,44.6,1,0.01,G,11.8,1,1,0,0,0,0
725060-94728,725060,94728,47.5,US,40.779,-73.969,2011-01-03,2011,01,03,33.7,24,14.6,24,1018.1,24,1013.2,24,10.0,24,8.5,24,13.0,25.1,46.0,1,28.0,1,0.00,I,9.1,0,0,0,0,0,0
725060-94728,725060,94728,47.5,US,40.779,-73.969,2011-01-04,2011,01,04,33.9,24,15.0,24,1016.1,24,1011.1,24,9.9,24,5.6,24,8.9,15.9,39.9,1,30.9,1,0.00,G,5.9,0,0,0,0,0,0
*/

/*
vendor_id,pickup_datetime,dropoff_datetime,passenger_count,trip_distance,pickup_longitude,pickup_latitude,rate_code,store_and_fwd_flag,dropoff_longitude,dropoff_latitude,payment_type,fare_amount,surcharge,mta_tax,tip_amount,tolls_amount,total_amount

CMT,2011-06-15 17:50:28,2011-06-15 18:11:32,1,1.1000000000000001,-73.967978000000002,40.753256,1,N,-73.983118000000005,40.756233000000002,CSH,10.9,1,0.5,0,0,12.4
CMT,2011-06-16 15:24:41,2011-06-16 15:28:41,1,0.69999999999999996,-73.999369000000002,40.739030999999997,1,N,-74.001597000000004,40.729320000000001,CSH,4.5,0,0.5,0,0,5
CMT,2011-06-15 22:46:41,2011-06-15 22:49:09,0,0.59999999999999998,-74.004099999999994,40.747799999999998,1,N,-73.997799999999998,40.756500000000003,CSH,4.0999999999999996,0.5,0.5,0,0,5.0999999999999996
*/

val weather = spark.read.format("csv").option("header", true).option("inferSchema",true).load("/user/maria_dev/finalp3/725060-94728.csv"
    ).select("Date", "Year", "Month", "Day","Mean_Temp","Precipitation","Precip_Flag","Snow_Depth","Fog","Rain_or_Drizzle","Snow_or_Ice","Hail","Thunder","Tornado"
    ).withColumn("Date", 
    to_date($"Date", "yyyy-MM-dd")
    )


val taxi = spark.read.format("csv").option("header", true).option("inferSchema",true).load("/user/maria_dev/finalp3/yellow_tripdata_2011-06.csv"
    ).filter($"passenger_count" >= 1 && $"fare_amount" > 0
    ).select("pickup_datetime", "passenger_count", "fare_amount","tip_amount", "total_amount"
    ).withColumn("timestamp", 
    unix_timestamp($"pickup_datetime", "yyyy-MM-dd HH:mm:ss")
    ).withColumn("Date", 
    to_date($"pickup_datetime", "yyyy-MM-dd HH:mm:ss")
    )

    weather.printSchema()
    taxi.printSchema()


    weather.select("Precip_Flag").distinct().show()

    weather.select("Precipitation").agg(min("Precipitation").as("minPrecip"), mean("Precipitation").as("meanPrecip"), max("Precipitation").as("maxPrecip")).show(20,false)

weather.show()
taxi.show()

val join = taxi.alias("a").join(weather.alias("b"), "Date")

join.show()

val agg1 = join.groupBy("Rain_or_Drizzle").agg(
    count("Date").as("numberTrips"),
    mean("passenger_count").as("meanPass"),
    mean("fare_amount").as("meanFare"),
    (sum("tip_amount")/sum("fare_amount")).as("tipFareRatio")
    )

    // val agg1 = join.groupBy("Rain_or_Drizzle").agg(
    // count("Date").as("numberTrips"),
    // min("passenger_count").as("minPass"),mean("passenger_count").as("meanPass"),stddev("passenger_count").as("stddevPass"),max("passenger_count").as("maxPass"),
    // min("fare_amount").as("minFare"),mean("fare_amount").as("meanFare"),stddev("fare_amount").as("stddevFare"),max("fare_amount").as("maxFare"),
    // (sum("tip_amount")/sum("fare_amount")).as("tipFareRatio2"),
    // min("tipFareRatio").as("minTFR"),mean("tipFareRatio").as("meanTFR"),stddev("tipFareRatio").as("stddevTFR"),max("tipFareRatio").as("maxTFR")
    // )

    

    answer.show()

    weather.count()
    weather.filter($"Mean_Temp" >= 32).count()

val dayCount = join.select("Rain_or_Drizzle", "Date").distinct().groupBy("Rain_or_Drizzle").agg(count("Rain_or_Drizzle").as("dayCount"))

val join2 = dayCount.join(agg1, "Rain_or_Drizzle").withColumn("tripsPerDay",
    $"numberTrips"/$"dayCount"
    ).select("Rain_or_Drizzle","tripsPerDay","meanPass","meanFare","tipFareRatio")

join2.show()
