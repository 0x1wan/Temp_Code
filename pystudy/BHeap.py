class BHeap:
    def __init__(self, a):
        self.a = a
        self.N = len(a) -1

    def cheap(self): # 초기 힙 생성
        for i in range(self.N//2, 0, -1):
            self.downheap(i)

    def insert(self, val):
        self.N +=1
        self.a.append(val)
        self.upheap(self.N)

    def downheap(self, i): #힙 내려가면서 속성 회복. 
        while 2*i <= self.N:
            k = 2*i #자식 노드 번호(실제로 노드는 아니긴 함)
            if k <self.N and self.a[k][0] > self.a[k+1][0]:
                k += 1 #자식 둘중 더 강자 고르기
            if self.a[i][0] <self.a[k][0]:
                break
            self.a[i], self.a[k] = self.a[k], self.a[i]
            i=k
        
    def upheap(self, j):
        while (j>1 and self.a[j//2][0] > self.a[j][0]):
            self.a[j], self.a[j//2] = self.a[j//2], self.a[j]
            j = j//2

    def dmin(self):
        if self.N == 0:
            print('heap is empty')
            return None
        min = self.a[1]
        self.a[1], self.a[-1] = self.a[-1], self.a[1]
        del self.a[-1]
        self.N -= 1
        self.downheap(1)
        return min
    
    def print_heap(self):
        for i in range(1,self.N+1):
            print('[%2d' % self.a[i][0], self.a[i][1], ']',end='')
        
        print('힙 크기:',self.N)

