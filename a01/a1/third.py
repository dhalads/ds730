import sys

def get_input():
    nums = []
    for line in sys.stdin:
        nums = line.split(" ")
        break
    nums = [int(i) for i in nums]
    return (nums[0], nums[1])

def set_output(primes):
    output = ""
    separators = (":", "!", "&")
    num_primes = len(primes)
    num_separators = len(separators)
    separator_index = 0
    if(num_primes != 0):
        for x in range(0, num_primes):
            output = output + str(primes[x])
            if(x<num_primes-1):
                # add separate becasue there is another prime in list
                output = output + separators[separator_index]
                separator_index += 1
                if(separator_index == num_separators):
                    separator_index = 0
    else:
        output = "No Primes"
    sys.stdout.write(output)

def isPrime(num):
    result = True
    if num % 2 == 0:
        result = False
    else:
        for divisor in range(3, int(num/2), 2):
            if num % divisor == 0:
                result = False
                break
    return result
    

def calc_primes(a, b):
    output = []
    if(a<=b):
        min = a
        max = b
    else:
        min = b
        max = a
    for num in range(min, max+1):
        if(isPrime(num)):
            output.append(num)
    return output

# nums = get_input()
# nums = [ 5, 24]
# primes = calc_primes(4, 24)
# set_output(primes)
# primes = calc_primes(24, 5)
# set_output(primes)

a, b = get_input()
primes = calc_primes(a, b)
set_output(primes)
