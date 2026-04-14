package ShapeDraw;

public class Hourglass {
	private int id;
    private int height;
    private char pattern;
    private int h;

    public Hourglass() {
    	id = 0;
        height = 5;
        pattern = '*';
        
        if(height%2==0) { //역시 짝수값이 들어오면 홀수화
        	h = height-1;
        }
        else {
        	h = height;
        }
    }

    public Hourglass(int _id, int height, char p) {
        this.id = _id;
        this.height = height;
        this.pattern = p;
        
        if(height%2==0) {
        	h = height-1;
        }
        else {
        	h = height;
        }
    }

    public String toString () {
        String str;
        str = "Hourglass " + id + " (" + height + ", " + pattern + ")";
        return str;
    }
    


    public void draw () {
    	for(int i=h-1; i>0; i--) {
        	int Blank = h-i;
        	
        	for(int j=0; j<Blank;j++) {
        		Dtool.blank();
        	}
        	for(int j=0;j<i*2-1;j++) {
        		Dtool.point(pattern);
        	}
        	Dtool.ln();
        }
    	
        for(int i=1;i<=h;i++) {
        	int Blank = h-i;
        	
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
        
        if(height%2==0) {
        	h = height-1;
        }
        else {
        	h = height;
        }
    }

}
