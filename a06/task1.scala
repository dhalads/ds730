sc.setLogLevel("WARN")
val numbers = Array(1,2,3,4,5,6)
val doubleIt = numbers.map(value=> value*2)
val small = doubleIt.filter(value => value < 5)
val moreNumbers = doubleIt.union(numbers);
//val moreNumbers = doubleIt.union(numbers).distinct
val total = doubleIt.reduce((a,b) => a + b)
val wordsFile = sc.textFile("/user/zeppelin/wap.txt")
wordsFile.count()
wordsFile.collect()
val onlyWar = wordsFile.filter(line => line.contains("war"))
onlyWar.count()
wordsFile.filter(line => line.contains("war")).count()
val tempWord = "This is a test";
val tempWordArray = tempWord.split(" ");
val numWords = tempWordArray.size
val mappedSize = wordsFile.map(line => line.split(" ").size)
val reduced = mappedSize.reduce((first,second) => if(first > second) first else second)
val flattenMap = wordsFile.flatMap(line => line.split(" "))
val mapped = wordsFile.map(line => line.split(" "))
mapped.count()
flattenMap.count()
val mapFirst = flattenMap.map(word => (word, 1))
val reduceSecond = mapFirst.reduceByKey((a,b) => a+b)
val mapreduce = flattenMap.map(word => (word, 1)).reduceByKey((a,b) => a+b)
mapreduce.collect()

//from task 5
wordsFile.map(line => line.length).reduce((first,second) => first+second)
val oneLess = mapreduce.mapValues(value => value-1)
val allKeys = mapreduce.keys
val manyWords = mapreduce.filter{case (key, value) => value > 2000}
manyWords.saveAsTextFile("/user/zeppelin/wap_output/")
val multFiles = sc.textFile("/user/zeppelin/wap_output/part*")
val wordsFile = sc.wholeTextFiles("/user/zeppelin/wap_output/part*")
