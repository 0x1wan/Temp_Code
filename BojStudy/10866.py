import sys

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
    
    
q = Deque()

# 빠른 입력을 위한 세팅
n = int(sys.stdin.readline())

for i in range(n):
 
    cmd = sys.stdin.readline().split()

    if cmd[0] == 'push_front':
        x = int(cmd[1]) 
        q.add_f(x)

    elif cmd[0] == 'push_back':
        x = int(cmd[1]) 
        q.add_r(x)

    elif cmd[0] == 'pop_front':
        print(q.remove_f()) 
    
    elif cmd[0] == 'pop_back':
        print(q.remove_r())

    elif cmd[0] == 'size':
        print(q.sizeout())
        
    elif cmd[0] == 'empty':
 
        print(int(q.is_empty()))
        
    elif cmd[0] == 'front':
        print(q.get_front()) 
        
    elif cmd[0] == 'back':
        print(q.get_rear())

