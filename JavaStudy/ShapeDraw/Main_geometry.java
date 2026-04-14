package ShapeDraw;
// 2022113960 최이완
import java.util.Scanner;

public class Main_geometry {
    public static final String QUIT = "0"; 
    
    public static void printMenu () {
        System.out.println ("***************");
        System.out.println ("* 1. Rectangle ");
        System.out.println ("* 2. Triangle ");
        System.out.println ("* 3. Diamond ");
        System.out.println ("* 4. Hourglass ");
        System.out.println ("* 5. Circle ");
        System.out.println ("* 6. All_Log ");
        System.out.println ("* 7. All_Log_Delete"); 
        System.out.println ("* Number + 'log' (ex: 1log, 2log)"); 
        System.out.println ("* 0. Quit ");
        System.out.println ("***************");
        System.out.print ("Enter your choice : ");
    }
    
    public static void main (String[] args) {
        Scanner input = new Scanner(System.in);
        String select; 
        
        Rectangle[] rectArr = new Rectangle[100];
        int rectCnt = 0;
        Triangle[] triArr = new Triangle[100];
        int triCnt = 0;
        Diamond[] diaArr = new Diamond[100];
        int diaCnt = 0;
        Hourglass[] hgArr = new Hourglass[100];
        int hgCnt = 0;
        Circle[] cirArr = new Circle[100];
        int cirCnt = 0;
        
        //그려지는 모든 도형을 기록하는 2차원 배열
        int[][] logArr = new int[100][100]; 
        int totalCnt = 0;
        
        printMenu();
        select = input.next(); 
        
        while (!select.equals(QUIT)) {
            
            if (select.equals("1")) {
                Rectangle rect = Create.createRect(input);
                rectArr[rectCnt] = rect; 
                logArr[totalCnt][0] = 1;
                logArr[totalCnt][1] = rectCnt; 
                rectCnt++;
                totalCnt++;
                rect.draw();
            }
            else if (select.equals("2")) {
                Triangle tri = Create.createTriangle(input);
                triArr[triCnt] = tri;
                logArr[totalCnt][0] = 2;
                logArr[totalCnt][1] = triCnt;
                triCnt++;
                totalCnt++;
                tri.draw();
            }
            else if (select.equals("3")) {
                Diamond dia = Create.createDiamond(input);
                diaArr[diaCnt] = dia;
                logArr[totalCnt][0] = 3;
                logArr[totalCnt][1] = diaCnt;
                diaCnt++;
                totalCnt++;
                dia.draw();
            }
            else if (select.equals("4")) {
                Hourglass hg = Create.createHourglass(input);
                hgArr[hgCnt] = hg;
                logArr[totalCnt][0] = 4;
                logArr[totalCnt][1] = hgCnt;
                hgCnt++;
                totalCnt++;
                hg.draw();
            }
            else if (select.equals("5")) {
                Circle cir = Create.createCircle(input);
                cirArr[cirCnt] = cir;
                logArr[totalCnt][0] = 5;
                logArr[totalCnt][1] = cirCnt;
                cirCnt++;
                totalCnt++;
                cir.draw();
            }
            else if (select.equals("6")) {
                System.out.println("=== 전체 도형 Log (" + totalCnt + "개) ===");
                for (int i = 0; i < totalCnt; i++) {
                    int type = logArr[i][0]; 
                    int idx = logArr[i][1];   
                    if (type == 1) rectArr[idx].draw();
                    else if (type == 2) triArr[idx].draw();
                    else if (type == 3) diaArr[idx].draw();
                    else if (type == 4) hgArr[idx].draw();
                    else if (type == 5) cirArr[idx].draw();
                }
            }
            else if (select.equals("7")) {
                rectCnt = 0; triCnt = 0; diaCnt = 0; hgCnt = 0; cirCnt = 0; totalCnt = 0;
                System.out.println("모든 도형 기록이 삭제되었습니다.");
            }
            else if (select.equals("1log")) {
                for (int i=0; i<rectCnt; i++) rectArr[i].draw();
            }
            else if (select.equals("2log")) {
                for (int i=0; i<triCnt; i++) triArr[i].draw();
            }
            else if (select.equals("3log")) {
                for (int i=0; i<diaCnt; i++) diaArr[i].draw();
            }
            else if (select.equals("4log")) {
                for (int i=0; i<hgCnt; i++) hgArr[i].draw();
            }
            else if (select.equals("5log")) {
                for (int i=0; i<cirCnt; i++) cirArr[i].draw();
            }
            else {
                System.out.println("잘못된 입력입니다. 메뉴 번호를 다시 확인하십시오.");
            }
            
            printMenu();
            select = input.next();
        }
        
        input.close();
        System.out.println("프로그램을 종료합니다. 수고하셨습니다.");
    }
}