import sys
import copy

#수제 덱 자료구조 클래스
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

d = Deque()

nm = input().split()

n = int(nm[0])
m = int(nm[1])

target = input().split()

cnt = 0

for i in range(1,n+1):
    d.add_r(i)

def dpop():
    return d.remove_f()
def leftmv():
    fitem = d.remove_f()
    d.add_r(fitem)
    global cnt
    cnt += 1
def rightmv():
    fitem = d.remove_r()
    d.add_f(fitem)
    global cnt
    cnt +=1

for i in range(m):
    tg = int(target[i])
    ranger = 1
    temp_d = copy.deepcopy(d)

    while(True):
        if(tg == int(temp_d.get_front())):
            break
        a = temp_d.remove_f()
        
        if(int(a) == tg):
            break
        ranger += 1

    lcost = ranger - 1
    rcost = d.sizeout() - ranger + 1

    if (ranger == 1):
        dpop()
    elif (rcost < lcost):
        while(True):
            rightmv()
            if(tg == int(d.get_front())):
                break
        
        dpop()
    else:
        while(True):
            leftmv()
            if(tg == int(d.get_front())):
                break
    
        dpop()
            
print(cnt)
