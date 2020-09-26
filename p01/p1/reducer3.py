#!/usr/bin/env python
 
import sys

class Person(object):

    def __init__(self, ID=None, Friends=None, FriendIDs=None):
        self._ID = ID
        self._Friends = Friends
        self._FriendIDs = FriendIDs
        self._MightKnow = None
        self._ProbablyKnow = None

    def __str__(self):
        return str(vars(self))

    def findPotentialContacts(self):
        for friend in self._Friends:
            id = friend._ID


class ReducerOutput(object):

    def __init__(self, Key=None, Person=None):
        """
        docstring
        """
        self._Key = Key
        self._Person = Person

    def __str__(self):
        return (str(vars(self))+str(vars(self._Person)))

    def reset(self):
        self._Key = None
        self._Person = None
        
    def process_line(self, Line):
        try:
            Line = Line.strip()
            key, values = Line.split('\t', 1)
            self.process_key(key)
            self.process_values(values)
        except Exception as e:
            # Record exception to stderr
            sys.stderr.write("Error Exception {} :{}\n".format(str(e), Line))
        
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
        Values = Values.strip()
        data = Values.split(':')
        personID = data[0].strip()
        friendIDs = data[1].split(' ')
        if(self._Key == personID):
            self._Person._FriendIDs=friendIDs
        else:
            person = Person(personID, None, friendIDs)
            self._Person._Friends.append(person)

    def process_output(self):
        # output = "{}:{}".format(self._Key, self._Count)
        print(str(self))
    
    def set_key(self, Key):
        """
        docstring
        """
        self._Key = Key
        self._Person = Person(Key, [])


reducerOutput = ReducerOutput()
for line in sys.stdin:
    line = line.strip()
    reducerOutput.process_line(line)
reducerOutput.process_output()