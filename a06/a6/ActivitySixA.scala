//Problem A
/*
Print out all words that appear between 5 and 7 times (including 5 and 7) in the wap.txt
document. The words can appear in any order. Print out only the words and not the
associated count. All words are separated by whitespace (spaces, tabs, newlines) only.
All other characters belong to a word. For example, don’t is a word and hello, is a word
(note the comma) which is different from the word hello that might appear in the
document. A word must contain at least 1 character (i.e. the empty string is ignored for
all problems). The words should be case-insensitive: the and The are the same word.
*/

sc.setLogLevel("WARN")
val lineList = sc.textFile("/user/zeppelin/wap.txt")

val wordList = lineList.flatMap(line => line.split(" "))

val filteredWordList = wordList.filter(word => word.length()>0)

val lowerWordList = filteredWordList.map(word => word.toLowerCase())

val wordCountList = lowerWordList.map(word => (word, 1)).reduceByKey((a,b) => a+b)

val filteredWordCountList = wordCountList.filter{case (key, value) => value >=5 && value <=7}

val allWords = filteredWordCountList.keys
allWords.collect()
