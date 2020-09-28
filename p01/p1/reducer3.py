#!/usr/bin/env python
 
import sys
import os
from json import loads, dumps

class DS730Base(object):
    @staticmethod
    def toStr(Obj):
        """
        docstring
        """
        className = type(Obj).__name__
        output = "Start {}({}):\n".format(className, str(id(Obj)))
        selfdict = vars(Obj)
        for key in selfdict.keys():
            value = selfdict.get(key)
            # print(type(value))
            # print(isinstance(value, Person))
            output = output + str(key) + "=" + str(value) + "\n"
        output = output + "End {}({})".format(className, str(id(Obj)))
        return output

class Person(object):

    def __init__(self, ID=None, Friends=None, FriendIDs=None):
        self._ID = ID
        self._Friends = Friends
        self._FriendIDs = FriendIDs
        self._SharedContacts = None
        self._MightKnow = None
        self._ProbablyKnow = None

    def __str__(self):
        return DS730Base.toStr(self)

    def findPotentialContacts(self):
        sharedContacts = {}
        num_friends = len(self._Friends)
        for i in range(0, num_friends):
            friendi = self._Friends[i]._FriendIDs
            for id in friendi:
                if(self.isFriend(id)):
                    continue
                count = sharedContacts.get(id)
                if(count is None):
                    #check number of common friends
                    count = 0
                    for j in range(i+1, num_friends):
                        friendj = self._Friends[j]._FriendIDs
                        if(id in friendj):
                            count = count + 1
                    sharedContacts[id] = count
                else:
                    pass
        # self._MightKnow = sharedContacts
        self._MightKnow = []
        self._ProbablyKnow = []
        for key in sharedContacts.keys():
            count = sharedContacts.get(key)
            if(count == 2 or count == 3):
                self._MightKnow.append(key)
            elif(count > 3):
                self._ProbablyKnow.append(key)
        self._MightKnow.sort()
        self._ProbablyKnow.sort()
        self._SharedContacts = sharedContacts

    def isFriend(self, ID):
        output = False
        if(ID in self._FriendIDs or ID == self._ID):
            output = True
        return output

    def get_output(self):
        output = self._ID + ":"
        if(len(self._MightKnow) > 0):
            output = output + "Might(" + ",".join(self._MightKnow) + ")"
            if(len(self._ProbablyKnow)>0):
                output = output + " "
        if(len(self._ProbablyKnow)>0):
            output = output + "Probably(" + ",".join(self._ProbablyKnow) + ")"
        return output
        
# End Class Person


class ReducerOutput(object):

    def __init__(self, Key=None, Person=None):
        """
        docstring
        """
        self._Key = Key
        self._Person = Person

    def __str__(self):
        return DS730Base.toStr(self)

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
            # add a friend to the Person
            person = Person(personID, None, friendIDs)
            self._Person._Friends.append(person)

    def process_output(self):
        self._Person.findPotentialContacts()
        output = self._Person.get_output()
        if(os.environ.get('python_debug', 0) == '1'):
            print(str(self))
        print(output)
    
    def set_key(self, Key):
        """
        docstring
        """
        self._Key = Key
        self._Person = Person(Key, [], [])

# End Class ReducerOutput


reducerOutput = ReducerOutput()
for line in sys.stdin:
    line = line.strip()
    reducerOutput.process_line(line)
reducerOutput.process_output()