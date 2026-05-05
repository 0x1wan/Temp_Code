package InheritanceShapeDraw;


public abstract class Shape {
	
	protected int Shapetype = 0; //도형의 타입을 구분 짓는 변수. 이 변수를 통해서 전체배열에서 특정 유형의 도형을 탐색가능함.
	
	protected int id;
	protected int length;
	protected int length2; //subclass들은 최대 2개의 길이 변수를 요함. 1개 값을 쓰는 class들은 이 값이 존재는 하나 쓰지 않는 더미 데이터.
	protected char pattern;

    public Shape () {
    	id = 0;
    	length = 5;
    	length2 = 5;
        pattern = '*';
    }
    
    public Shape (int _id, int length, char p) {//변수 1개 전용 생성자
        this.id = _id;
        this.length = length;
        this.pattern = p;
    }
    
    public Shape (int _id, int length,int length2, char p) {//변수 2개 전용 생성자
        this.id = _id;
        this.length = length;
        this.length2 = length2;
        this.pattern = p;
    }
    
    public String toString () {
        String str;
        String Name = this.getClass().getSimpleName(); //subclass의 이름 따오기.
        str = Name + id + " (" + length + ", " + pattern + ")";
        return str;
    }
    


    public abstract void draw ();
       //업캐스팅을 위한 더미 메소드(이 메소드로 인해 업캐스팅된 subclass 객체들은 draw메소드 사용 가능.)
    
    
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
    public int getlength() {
        return length;
    }
    public void setlength(int length) {
        this.length = length;
    }
}