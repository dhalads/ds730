The mapper.py and reducer.py are you mapper and reducer. The TestStateless code
only accepts 1 input file and is specified in the inputFile argument. It produces 1 output
file and is specified by the outputFile argument. The last argument is either a Y or an N.
If you put a Y, the temporary mapper and reducer files will be kept. The mapper
temporary files are not that interesting as they are just your inputFile split up into files 1
line at a time. The temporary reducer files store all (key,value) pairs that have the same
key. These will likely be of interest to you. All lines in reducerInX.tmp will end up on the
same reducer. Lines from a file called reducerInY.tmp may or may not end up on the
same reducer as reduceInX.tmp (assuming Y ≠ X). There are several caveats to using
this Java program:
iv. Your input file needs to be relatively small. The program creates a new file for
each line of your input. If you have 50,000 lines in your input, the program will
create 50,000 files. Make sure your input is small enough.
v. The number of keys you create must be relatively small. The program creates a
new file for each unique key your mapper generates so if you create thousands
of keys, the program will create thousands of files.

