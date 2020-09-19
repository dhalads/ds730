import sys

def factorial(val):
    output = 1
    if (val <0 ):
        output = val
    elif ( val == 0 ):
        output = 1
    else:
        for i in range(1, val+1):
            output = output * i
    return output
