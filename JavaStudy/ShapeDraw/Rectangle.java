package ShapeDraw;

public class Rectangle {
    private int id;
    private int length;
    private int height;
    private char pattern;

    public Rectangle () {
        length = 5;
        height = 5;
        pattern = '*';
    }

    public Rectangle (int _id, int len, int height, char p) {
        this.id = _id;
        this.length = len;
        this.height = height;
        this.pattern = p;
    }

    public String toString () {
        String str;
        str = "Rectanlge " + id + " (" + length + 
              ", " + height + ", " + pattern + 
              ")";
        return str;
    }

    public void draw () {
        for (int i = 0; i < height; i++ ) {
            for (int j = 0; j < length; j++) {
                System.out.print(pattern);
            }
            System.out.println ();
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
    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length = length;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
}