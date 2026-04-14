package ShapeDraw;

public class Dtool { //도형 클래스에서 공통으로 사용되는 기능들을 메소드화시켜서 정리해둠.
	

    public static void point(char p) {
    	System.out.print(p);
    }
    
    // 줄바꿈 도구
    public static void ln() {
    	System.out.println();
    }
    
    // 공백 출력 도구
    public static void blank() {
    	System.out.print(" ");
    }
}