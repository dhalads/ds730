Usage

/*
     * 
     * file="filename" : input file. default is input2.txt 
     * MTT="1" : use MultiThread true or false, default is true 
     * NumMT="8" : max thread in thread pool, default is 8 
     * DPart="1" , what data level to split for MTT, default is 1
     * 
     */

If accept defaults. Takes from input2.txt. Runs with 8 threads max. Partitions data based on number of buildings. 13 buildings will do 12 paritions.


    java FinalRunner

If want to change defaults. If set DPart=2, 13 buildings will do 132 data partitions.

    java FinalRunner file="../../FinalWork/p2/input10.txt" MTT=true NumMT=4 DPart=1

Timings from my local box.

    13 buildings times (milliseconds). Data split into 12 partitions.
    single thread - 113071, 100196
    two threads - 75527, 70734
    three threads - 57655, 59412
    eight threads - 44997, 49658
    12 threads -  43340, 43943

Problem! Time to complete does not decrease linearly with more threads applied. Seems after 3 threads, the decrease is minimal.
If I had more time, I would investigate why do not see a more linear decrease in time.
