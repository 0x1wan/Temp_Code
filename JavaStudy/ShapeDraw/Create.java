package ShapeDraw;

import java.util.Scanner;

public class Create {
    
	Scanner input = new Scanner(System.in);
    // 사각형 생성
    public static Rectangle createRect(Scanner input) {
    	System.out.print("Enter ID: ");
    	int id = input.nextInt();
    	System.out.print("Enter length: ");
    	int length = input.nextInt();
    	System.out.print("Enter height: ");
    	int height = input.nextInt();
    	System.out.print("Enter pattern: ");
    	char pattern = input.next().charAt(0); 
    	
    	return new Rectangle(id, length, height, pattern);
    }
    
    // 삼각형 생성
    public static Triangle createTriangle(Scanner input) {
    	System.out.print("Enter ID: ");
    	int id = input.nextInt();
    	System.out.print("Enter height: ");
    	int height = input.nextInt();
    	System.out.print("Enter pattern: ");
    	char pattern = input.next().charAt(0); 
    	
    	return new Triangle(id, height, pattern);
    }

    // 다이아몬드 생성
    public static Diamond createDiamond(Scanner input) {
    	System.out.print("Enter ID: ");
    	int id = input.nextInt();
    	System.out.print("Enter height: ");
    	int height = input.nextInt();
    	System.out.print("Enter pattern: ");
    	char pattern = input.next().charAt(0); 
    	
    	return new Diamond(id, height, pattern);
    }

    // 모래시계 생성
    public static Hourglass createHourglass(Scanner input) {
    	System.out.print("Enter ID: ");
    	int id = input.nextInt();
    	System.out.print("Enter height: ");
    	int height = input.nextInt();
    	System.out.print("Enter pattern: ");
    	char pattern = input.next().charAt(0); 
    	
    	return new Hourglass(id, height, pattern);
    }

    // 원 생성
    public static Circle createCircle(Scanner input) {
    	System.out.print("Enter ID: ");
    	int id = input.nextInt();
    	System.out.print("Enter radius: ");
    	int radius = input.nextInt();
    	System.out.print("Enter pattern: ");
    	char pattern = input.next().charAt(0); 
    	
    	return new Circle(id, radius, pattern);
    }
}