package InheritanceShapeDraw;

public class Circle extends Shape{

	private int radius = length; //이름 변경
	
	public Circle() {
		this.Shapetype = 5;
	}
	
	public Circle(int a, int b, char p) {//상위 클래스의 생성자로 연결.
		super(a,b,p);
		this.Shapetype = 5;
	}
	
	public void draw () {
        int matrix = radius*2-1;
        
        for (int i = 0; i < matrix; i++) {
            for (int j = 0; j < matrix; j++) {
                //인덱스를 데카르트 좌표로 변환
                int x = j - (radius - 1);
                int y = (radius - 1) - i;
                
                if (x*x + y*y*2 <= radius * radius) {//문자열의 생김새 보정하는 상수값 y에 곱해주기(2)
                    Dtool.point(pattern);
                } else {
                    Dtool.blank();
                }
            }
            Dtool.ln();
        }
    }
	
}
