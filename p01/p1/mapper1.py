#!/usr/bin/env python

import sys
import datetime

# InvoiceNo,StockCode,Description,Quantity,InvoiceDate,UnitPrice,CustomerID,Country
class LineItem(object):
    def __init__(self, InvoiceNo=None, StockCode=None, Description=None, Quantity=None, InvoiceDate=None, UnitPrice=None, CustomerID=None, Country=None):
        self._InvoiceNo = InvoiceNo
        self._StockCode = StockCode
        self._Description = Description
        self._Quantity = Quantity
        self._InvoiceDate = InvoiceDate
        self._UnitPrice = UnitPrice
        self._CustomerID = CustomerID
        self._Country = Country
        
    
#     def __repr__(self):
#         return "Car('" + self.Make + "', " +  str(self.year) +  ")"

    def __str__(self):
        return str(vars(self))
        
    def get_amount(self):
        output = 0
        output = round(self._Quantity * self._UnitPrice, 2)
        return output
    
    def get_output(self):
        output = ""
        output = "{},{}\t{},{}".format(self._InvoiceDate.strftime("%m"), self._Country, self._CustomerID, self.get_amount())
        return output
        
    def parse_line(self, Line):
        output = True
        try:
            Line = Line.strip()
            splits = Line.split(',')
            self._InvoiceNo = splits[0].strip()
            self._StockCode = splits[1].strip()
            self._Description = splits[2].strip()
            self._Quantity = int(splits[3])
            self._InvoiceDate = datetime.datetime.strptime(splits[4],"%m/%d/%Y %H:%M") #12/1/2010 8:26
            self._UnitPrice = float(splits[5])
            self._CustomerID = splits[6].strip()
            self._Country = splits[7].strip()
        except Exception as e:
            # Record exception to stderr
            sys.stderr.write("Error Exception {} :{}\n".format(str(e), line))
            output = False
        else:
            if(self._InvoiceNo.startswith("C")):
                self.reset()
                output = False
            else:
                isValidOutput, message = self.isValid()
                if(not isValidOutput):
                    output = False
                    sys.stderr.write("Error isValid {} :{}\n".format(message, line))
        return output

    def exceptionOnBlank(self, Value):
        """
        docstring
        """
        if(Value is None or len(Value) == 0):
            raise Exception("Value is blank")

    def isValid(self):
        """
        docstring
        """
        output = True
        message = ""
        variables = vars(self)
        for key in variables.keys():
            if(key in ("_Quantity", "_InvoiceDate", "_UnitPrice", "_Description")):
                pass
            else:
                value = variables.get(key)
                if(self.isEmpty(value)):
                    output = False
                    message = "{} is empty".format(key)
                    break
        if(output):
            if(self._InvoiceNo.startswith("C")):
                output = False
        return (output, message)
        

    def isEmpty(self, Value):
        """
        docstring
        """
        output = False
        if(Value is None or len(Value) == 0):
            output = True
        return output

    def reset(self):
        """
        docstring
        """
        self._InvoiceNo = None
        self._StockCode = None
        self._Description = None
        self._Quantity = None
        self._InvoiceDate = None
        self._UnitPrice = None
        self._CustomerID = None
        self._Country = None
# End class LineItem
        
aLineItem = LineItem()
line = sys.stdin.readline()
while line:
  #Do something with line here to create/output
  #as many (key,value) pairs as you want.
  #Do not add anything above this line. The one 
  #exception is that you can add import statements.
    if(line.startswith("InvoiceNo")):
        line = sys.stdin.readline()
        continue
    aLineItem.reset()
    if(aLineItem.parse_line(line)):
        print(aLineItem.get_output())


#Do not add anything below this line.
#Read in the next line of the input.
    line = sys.stdin.readline()
