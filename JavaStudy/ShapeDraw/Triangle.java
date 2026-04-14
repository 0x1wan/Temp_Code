package ShapeDraw;

public class Triangle {
    private int id;
    private int height;
    private char pattern;

    public Triangle () {
    	id = 0;
        height = 5;
        pattern = '*';
    }

    public Triangle (int _id, int height, char p) {
        this.id = _id;
        this.height = height;
        this.pattern = p;
    }

    public String toString () {
        String str;
        str = "Triangle " + id + " (" + height + ", " + pattern + ")";
        return str;
    }
    


    public void draw () {
        for(int i=1;i<=height;i++) {
        	int Blank = height-i;
        	
        	for(int j=0;j<Blank;j++) {
        		Dtool.blank();
        	}
        	for(int j=0;j<i*2-1;j++) {
        		Dtool.point(pattern);
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
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
}