package InheritanceShapeDraw;
//2022113960 최이완

import java.util.Scanner;

public class Main_geometry {
	
	//초기 세팅
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
	    
	    //초기 세팅 끝
	    
	    public static void main (String[] args) {
	        Scanner input = new Scanner(System.in);
	        String select; 
	        int cnt = -1; //배열 갯수 카운트 (배열이 비어있음을 표시하기 위해 초기값을 -1로 선언)
	        
	        printMenu();
	        select = input.next(); 
	        
	        Shape[] sArr = new Shape[100]; //전체 배열 선언

	        
	        while (!select.equals(QUIT)) {
	            //nlog같은 입력 경우가 있기에 입력값을 str으로 받기.
	            if (select.equals("1")) {
	                //1번 생성
	            	cnt++;
	            	System.out.print("Enter ID: ");
	            	int id = input.nextInt();
	            	System.out.print("Enter length: ");
	            	int length = input.nextInt();
	            	System.out.print("Enter height: ");
	            	int height = input.nextInt();
	            	System.out.print("Enter pattern: ");
	            	char pattern = input.next().charAt(0); 
	            	
	            	if(length <=0 || height <=0) {
	            		System.out.println("양수를 입력해주세요.");
	            		cnt--;
	            	}
	            	else {
	            		sArr[cnt] = new Rectangle(id,height,length,pattern); 
		            	sArr[cnt].draw();
	            	}
	            }
	            else if (select.equals("2")) {
	                //2번 생성
	            	cnt++;
	            	System.out.print("Enter ID: ");
	            	int id = input.nextInt();
	            	System.out.print("Enter height: ");
	            	int height = input.nextInt();
	            	System.out.print("Enter pattern: ");
	            	char pattern = input.next().charAt(0); 
	            	
	            	if(height<=0) {
	            		System.out.println("양수를 입력해주세요.");
	            		cnt--;
	            	}
	            	else {
	            		sArr[cnt] = new Triangle(id, height, pattern);
	    	            sArr[cnt].draw();
	            		
	            	}
	            }
	            else if (select.equals("3")) {
	                //3번 생성
	            	cnt++;
	            	System.out.print("Enter ID: ");
	            	int id = input.nextInt();
	            	System.out.print("Enter height: ");
	            	int height = input.nextInt();
	            	System.out.print("Enter pattern: ");
	            	char pattern = input.next().charAt(0); 
	            	
	            	if(height<=0) {
	            		System.out.println("양수를 입력해주세요.");
	            		cnt--;
	            	}
	            	else {
	            		if(height%2 == 0) {
		            		System.out.println("높이는 홀수만 가능합니다.");
		            		cnt--;
		            	}
	            		else {
	            			sArr[cnt] = new Diamond(id, height, pattern);
	    	            	sArr[cnt].draw();
	            		}
	            	}
	            }
	            else if (select.equals("4")) {
	                //4번 생성
	            	cnt++;
	            	System.out.print("Enter ID: ");
	            	int id = input.nextInt();
	            	System.out.print("Enter height: ");
	            	int height = input.nextInt();
	            	System.out.print("Enter pattern: ");
	            	char pattern = input.next().charAt(0); 
	            	
	            	if(height<=0) {
	            		System.out.println("양수를 입력해주세요.");
	            		cnt--;
	            	}
	            	else {
	            		if(height%2 == 0) {
		            		System.out.println("높이는 홀수만 가능합니다.");
		            		cnt--;
		            	}
	            		else {
	            			sArr[cnt] = new Hourglass(id, height, pattern);
	    	            	sArr[cnt].draw();
	            		}
	            	}
	            	
	            	
	            }
	            else if (select.equals("5")) {
	                //5번 생성
	            	cnt++;
	            	System.out.print("Enter ID: ");
	            	int id = input.nextInt();
	            	System.out.print("Enter radius: ");
	            	int radius = input.nextInt();
	            	System.out.print("Enter pattern: ");
	            	char pattern = input.next().charAt(0); 
	            	
	            	if(radius <=0) {
	            		System.out.println("양수를 입력해주세요.");
	            		cnt--;
	            	}
	            	else {
	            		sArr[cnt] = new Circle(id,radius,pattern);
		            	sArr[cnt].draw();
	            	}
	            }
	            
	            else if (select.equals("6")) {
	            	if(cnt == -1) {
	            		System.out.println("저장된 도형이 없습니다.");
	            	}
	            	else {
	            		System.out.println("=== 전체 도형 Log (" + (cnt+1) + "개) ===");
	            		for(int i=0; i<=cnt;i++) {
	            			sArr[i].draw();
	            		}
	            	}
	            }
	            else if (select.equals("7")) {
	                //배열 초기화
	            	cnt = -1;
	                System.out.println("모든 도형 기록이 삭제되었습니다.");
	            }
	            
	            
	            else if (select.matches("^[1-5]log$") ){//nlog형태의 입력값 받기.
	            	char num = select.charAt(0);
	            	int Stype = num - '0'; //어떤 도형의 log를 불러올지 정수형으로 읽기.
	            	int checknull =0; //특정 도형이 없는 걸 체크하기 위한 변수
	            	
	            	if(cnt == -1) {//전체 배열이 비었을 경우.
	            		System.out.println("저장된 도형이 없습니다.");
	            	}
	            	else {
	            		for(int i=0; i<=cnt;i++) {
		            		if(sArr[i].Shapetype == Stype) {
		            			sArr[i].draw();
		            			checknull++;
		            		}
		            	}
		            	if(checknull==0) {//배열 끝까지 순회했지만, 해당 도형이 존재하지 않았을 경우.
		            		System.out.println("해당 도형이 존재하지 않습니다.");
		            	}
	            	}
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
