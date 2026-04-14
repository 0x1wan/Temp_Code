package ShapeDraw;

public class Circle {
	private int id;
    private int radius;
    private char pattern;

    public Circle() {
    	id = 0;
    	radius = 5;
        pattern = '*';
    }

    public Circle(int _id, int radius, char p) {
        this.id = _id;
        this.radius = radius;
        this.pattern = p;
    }

    public String toString () {
        String str;
        str = "Circle " + id + " (" + radius + ", " + pattern + ")";
        return str;
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

    public char getPattern() {
        return pattern;
    }
    public void setPattern (char pattern) {
        this.pattern = pattern;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getRadius() {
        return radius;
    }
    public void setRadius(int radius) {
        this.radius = radius;
    }
}
