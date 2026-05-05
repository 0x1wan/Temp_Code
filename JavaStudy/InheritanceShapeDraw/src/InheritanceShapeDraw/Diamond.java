package InheritanceShapeDraw;

public class Diamond extends Shape{
	
	public Diamond() {
		this.Shapetype = 3;
	}
	
	public Diamond(int a, int b, char p) {//상위 클래스의 생성자로 연결.
		super(a,b,p);
		this.Shapetype = 3;
	}
	
	public void draw () {
        for(int i=1;i<=length;i++) {
        	int Blank = length-i;
        	
        	for(int j=0;j<Blank;j++) {
        		Dtool.blank();
        	}
        	for(int j=0;j<i*2-1;j++) {
        		Dtool.point(pattern);
        	}
        	Dtool.ln();
        }
        for(int i=length-1; i>0; i--) {
        	int Blank = length-i;
        	
        	for(int j=0; j<Blank;j++) {
        		Dtool.blank();
        	}
        	for(int j=0;j<i*2-1;j++) {
        		Dtool.point(pattern);
        	}
        	Dtool.ln();
        }
    }
}
