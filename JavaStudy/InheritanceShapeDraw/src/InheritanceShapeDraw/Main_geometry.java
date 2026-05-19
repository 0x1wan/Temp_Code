package InheritanceShapeDraw;
//2022113960 최이완

import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

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
	        System.out.println ("* 8. Save (result.txt)"); 
	        System.out.println ("* 9. Load (result.txt)");
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
	            else if (select.equals("8")) {
	                // 파일 저장
	                try {
	                    if (cnt == -1) {
	                        System.out.println("저장할 도형이 없습니다.");
	                    } else {
	                    	String content = "";
	                    	for (int i = 0; i <= cnt; i++) {
	                    	    // format: 타입 id 길이 길이2 패턴\n
	                    	    content += sArr[i].Shapetype + " " 
	                    	             + sArr[i].id + " " 
	                    	             + sArr[i].length + " " 
	                    	             + sArr[i].length2 + " " 
	                    	             + sArr[i].pattern + "\n";
	                    	}
	                        // 기본적으로 덮어쓰기 수행 (이어쓰기로 코드를 짤 경우 , StandardOpenOption.APPEND 추가)
	                        Files.writeString(Path.of("result.txt"), content.toString());
	                        System.out.println("result.txt 파일에 성공적으로 저장되었습니다.");
	                    }
	                } catch (Exception e) {
	                    System.out.println("파일 저장 중 오류가 발생했습니다: " + e.getMessage());//익셉션에서 오류 로그 때오기
	                }
	            }
	            else if (select.equals("9")) {
	                // 파일 불러오기
	                try {
	                    Scanner fileInput = new Scanner(Path.of("result.txt"));
	                    cnt = -1; // 기존 배열 정보를 초기화하고 파일 내용으로 덮어씀
	                    
	                    while (fileInput.hasNextInt()) {
	                        int type = fileInput.nextInt();
	                        int id = fileInput.nextInt();
	                        int len = fileInput.nextInt();
	                        int len2 = fileInput.nextInt();
	                        char pat = fileInput.next().charAt(0);
	                        
	                        cnt++;
	                        // 불러온 타입에 맞춰서 다시 객체 생성
	                        switch (type) {
	                            case 1: sArr[cnt] = new Rectangle(id, len, len2, pat); break;
	                            case 2: sArr[cnt] = new Triangle(id, len, pat); break;
	                            case 3: sArr[cnt] = new Diamond(id, len, pat); break;
	                            case 4: sArr[cnt] = new Hourglass(id, len, pat); break;
	                            case 5: sArr[cnt] = new Circle(id, len, pat); break;
	                        }
	                    }
	                    fileInput.close();
	                    System.out.println("result.txt 파일에서 총 " + (cnt + 1) + "개의 도형을 불러왔습니다.");
	                } catch (Exception e) {
	                    System.out.println("파일을 찾을 수 없거나 불러오는 중 오류가 발생했습니다.");
	                }
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
