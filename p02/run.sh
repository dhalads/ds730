#!/bin/bash

pigTest="./p2/P4.pig"

pigRemote="./ds730_local/script_dev.pig"

myCommand="rsync -v $pigTest awshorton:$pigRemote"
echo $myCommand
$myCommand

# ssh awshorton "pig -v -f $pigRemote"

# ssh awshorton "pig"