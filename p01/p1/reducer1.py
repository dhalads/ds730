#!/usr/bin/env python
 
import sys
import operator
import os

class ReducerOutput(object):

    def __init__(self, Key=None, Month=None, Country=None):
        """
        docstring
        """
        self._Key = Key
        self._Month = Month
        self._Country = Country
        self._Data = {}

    def __str__(self):
        return str(vars(self))

    def reset(self):
        self._Key = None
        self._Month = None
        self._Country = None
        self._Data = {}
        
    def process_line(self, line):
        key, values = line.split('\t', 1)
        self.process_key(key)
        self.process_values(values)
        
    def process_key(self , Key):
        if(self._Key == None):
            self.set_key(Key)
        elif(self._Key != Key):
            self.process_output()
            self.reset()
            self.set_key(Key)
        else:
            pass

    def process_values(self, Values):
        customerID, amount = Values.split(",", 1)
        amount = round(float(amount),2)
        if(self._Data.get(customerID) is not None):
            self._Data[customerID] = self._Data[customerID] + amount
        else:
            self._Data[customerID] = amount

    def process_output(self):
        sorted_data = sorted(self._Data.items(), key=operator.itemgetter(1), reverse=True)
        data_count = len(sorted_data)
        customers = []
        if(data_count>0):
            customerId, top_amount = sorted_data[0]
            customers.append(customerId )
            for i in range(1, data_count):
                customerId, amount = sorted_data[i]
                if(amount == top_amount):
                    customers.append(customerId)
            customers.sort()
            customers_output = ""
            for i in range(0, len(customers)):
                customers_output = customers_output + customers[i]
                if(i != len(customers)-1):
                   customers_output = customers_output + ","
            # sorted(self._Data.items(), key=lambda item: item[1])
            output = "{},{}:{}".format(self._Month, self._Country,customers_output)
            # print(os.environ.get('python_debug', 0))
            if(len(customers)>1 and os.environ.get('python_debug', 0) == '1'):
                print(str(self))
            print(str(output))
            # and os.environ['python_debug'] == '1'
    

    def coalesce(self, *arg):
         return reduce(lambda x, y: x if x is not None else y, arg)

    def set_key(self, Key):
        """
        docstring
        """
        self._Key = Key
        month, country = Key.split(",", 1)
        self._Month = month
        self._Country = country


reducerOutput = ReducerOutput(None, None, None)
for line in sys.stdin:
    line = line.strip()
    reducerOutput.process_line(line)
reducerOutput.process_output()