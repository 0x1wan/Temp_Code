#include <stdio.h>

int min(int a, int b) {
    return (a < b) ? a : b;
}

int calc(int x, int y,char F[51][51]){
    int wcnt = 0;
    int bcnt = 0;

    for(int i=0;i<8;i++){
        for(int j=0;j<8;j++){
            int xy = (x-i)+(y-j);
            char correct;

            if(xy%2 == 0){
                correct = 'B';
            }
            else{
                correct = 'W';
            }
            if(F[x-i][y-j] != correct){
                bcnt +=1;
            }
            else{
                wcnt +=1;
            }
        }
    }

    return min(bcnt,wcnt);
}

int main(){
    int n, m;
    char F[51][51];

    scanf("%d %d", &n, &m);
    int cnt = n*m;
    // 보드 상태 입력
    for (int i = 0; i < n; i++) {
        scanf("%s", F[i]);
    }

    for (int i=7; i<n; i++){
        for(int j=7; j<m; j++){
            int cal = calc(i,j,F);

            cnt = min(cnt,cal);
        }
    }

    printf("%d",cnt);

    return 0;
}