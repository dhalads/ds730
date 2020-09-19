import sys

def calc_average():
    nums = []
    for line in sys.stdin:
        nums = line.split(" ")
        break
    nums = [int(i) for i in nums]
    average = sum(nums)/len(nums)
    sys.stdout.write(str(average))

calc_average()