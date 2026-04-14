class Node:
    def __init__(self, item, n=None):
        self.item = item
        self.next = n

class LinkedQueue:
    def __init__(self):
        # 큐를 처음 만들 때, 머리(front)와 꼬리(rear) 모두 비어있는 상태로 초기화합니다.
        self.front = None
        self.rear = None
        self.size = 0

    def is_empty(self):
        return self.size == 0

    def add(self, item): # 삽입 (Enqueue)
        new_node = Node(item, None)
        
        if self.is_empty():
            # 큐가 비어있다면, 첫 노드가 머리이자 곧 꼬리가 됩니다.
            self.front = new_node
        else:
            # 비어있지 않다면, 현재 꼬리 노드의 다음에 새 노드를 연결합니다.
            self.rear.next = new_node
            
        # 꼬리 포인터를 방금 들어온 새 노드로 업데이트합니다.
        self.rear = new_node
        self.size += 1

    def remove(self): # 삭제 (Dequeue)
        if self.is_empty():
            return -1
            
        # 삭제할 맨 앞의 데이터를 임시로 저장해 둡니다.
        fitem = self.front.item
        # 머리 포인터를 그 다음 노드로 옮겨버립니다. (기존 머리는 가비지 컬렉터가 처리)
        self.front = self.front.next
        self.size -= 1
        
        # 만약 방금 노드를 빼서 큐가 텅 비게 되었다면, 꼬리 포인터도 None으로 끊어줍니다.
        if self.is_empty():
            self.rear = None
            
        return fitem

    def sizeout(self):
        return self.size
    def get_front(self):
        if(self.is_empty()):
            return -1
        return self.front.item
    
    def back(self):
        if(self.is_empty()):
            return -1
        return self.rear.item
    
q = LinkedQueue()

n = int(input())
c = 0
f = None

for i in range(1,n+1):
    q.add(i)

while(q.sizeout()!=1):
    if (c==0):
        q.remove()
        c = 1
    else:
        f = q.remove()
        q.add(f)
        c=0

print(int(q.get_front()))