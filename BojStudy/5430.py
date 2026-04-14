import sys

class Node:
    def __init__(self, item, n=None, p=None):
        self.item = item
        self.next = n
        self.prev = p

class Deque:
    def __init__(self):
        self.front = None
        self.rear = None
        self.size = 0

    def is_empty(self):
        return self.size == 0

    def add_f(self, item): 
        new_node = Node(item, None,None)
        
        if self.is_empty():
            
            self.front = new_node
            self.rear = new_node
        else:
            self.front.prev = new_node
            new_node.next = self.front
            
        
        self.front = new_node
        self.size += 1

    def add_r(self, item): 
        new_node = Node(item, None,None)
        
        if self.is_empty():
            
            self.front = new_node
            self.rear = new_node
        else:
            self.rear.next = new_node
            new_node.prev = self.rear
            
        
        self.rear = new_node
        self.size += 1

    def remove_f(self): 
        if self.is_empty():
            return -1
            
        fitem = self.front.item
        
        self.front = self.front.next
        self.size -= 1
        
        
        if self.is_empty():
            self.rear = None
        else:
            self.front.prev = None
            
        return fitem

    def remove_r(self): 
        if self.is_empty():
            return -1
            
        fitem = self.rear.item
        
        self.rear = self.rear.prev
        self.size -= 1
        
        
        if self.is_empty():
            self.front = None
        else:
            self.rear.next = None    
        return fitem


    def sizeout(self):
        return self.size

    def get_front(self):
        if(self.is_empty()):
            return -1
        return self.front.item
    
    def get_rear(self):
        if(self.is_empty()):
            return -1
        return self.rear.item
    
    

def Reverse(v):
    if v == True:
        return False
    else:
        return True

def Trash(d,v):
    if v == True:
        d.remove_f()
    else:
        d.remove_r()

t=int(input())

for i in range(t):
    q = Deque()
    p = sys.stdin.readline().strip()
    revec = True
    testcase = True
    n = int(input())
    arr = sys.stdin.readline().strip()[1:-1].split(',')
    if arr == ['']:
        arr = []
    
    for i in range(n):
        q.add_r(arr[i])
    
    
    for cmd in p:
        if(cmd == 'R'):
            revec = Reverse(revec)
        elif(cmd == 'D'):
            if(q.sizeout() <= 0):
                testcase = False
                break
            Trash(q,revec)
        else:
            testcase = False
            break
    
    if(testcase == False):
        print("error")
    else:
        print('[',end="")
        len = q.sizeout()
        for i in range(len):
            if(revec == True):
                print(q.get_front(),end="")
                q.remove_f()
            else:
                print(q.get_rear(),end="")
                q.remove_r()
            if(q.sizeout() > 0):
                print(',',end="")
        print(']')
