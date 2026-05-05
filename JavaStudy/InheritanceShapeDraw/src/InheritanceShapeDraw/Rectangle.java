package InheritanceShapeDraw;

public class Rectangle extends Shape{
	
	public Rectangle() {
		this.Shapetype = 1;
	}
	
	public Rectangle(int a, int b, int c, char p) {//상위 클래스의 생성자로 연결.
		super(a,b,c,p);
		this.Shapetype = 1;
	}
	
	public void draw () {
        for (int i = 0; i < length; i++ ) {
            for (int j = 0; j < length2; j++) {
                System.out.print(pattern);
            }
            System.out.println ();
        }
    }

}
