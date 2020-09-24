#!/usr/bin/env python

import sys

# InvoiceNo,StockCode,Description,Quantity,InvoiceDate,UnitPrice,CustomerID,Country
class LineItem(object):
    def __init__(self, InvoiceNo, StockCode, Description, Quantity, InvoiceDate, UnitPrice, CustomerID, widthCountry):
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

#     def __str__(self):
#         return "Make: {}, Model Year: {}, Model_id: {}, year: {}, horsepower: {}, highway_mpg: {},\
# city_mpg: {}, width: {}, hybrid: {}".format(self._Make, self._Model_year, self._Model_id,\
#         self._year, self._horsepower, self._highway_mpg, self._city_mpg, self._width, self._hybrid)
    
#     def get_make(self):
#         return self._Make
        
    def parse_line(self, text):
        try:
          splits = line.split(',')
          self._InvoiceNo = splits[0].trim()
          self._StockCode = splits[1]
          self._Description = splits[1]
          self._Quantity = splits[1]
          self._InvoiceDate = splits[1]
          self._UnitPrice = splits[1]
          self._CustomerID = splits[1]
          self._Country = splits[1]
        except Exception as e:
            pass
        

line = sys.stdin.readline()
while line:
  #Do something with line here to create/output
  #as many (key,value) pairs as you want.
  #Do not add anything above this line. The one 
  #exception is that you can add import statements.
  aLineItem = LineItem()
  aLineItem.parse_line(line)
  
  
  #Do not add anything below this line.
  #Read in the next line of the input.
  line = sys.stdin.readline()
