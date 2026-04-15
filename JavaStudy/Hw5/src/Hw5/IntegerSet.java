package Hw5;

public class IntegerSet {
	
	private boolean[] arr;
	
	public IntegerSet() {
		arr = new boolean[101]; //boolean datatype은 기본값이 false임
	}
	
	public static IntegerSet union(IntegerSet s1,IntegerSet s2) {
		IntegerSet result = new IntegerSet();
		
		for(int i=0; i<101; i++) {
			if(s1.arr[i] || s2.arr[i]) {
				result.arr[i] = true;
			}
		}
		
		return result;
	}
	
	public static IntegerSet intersection(IntegerSet s1,IntegerSet s2) {
		IntegerSet result = new IntegerSet();
		
		for(int i=0; i<101; i++) {
			if(s1.arr[i] && s2.arr[i]) {
				result.arr[i] = true;
			}
		}
		
		return result;
	}
	
	public void insertElement(int i) {
		this.arr[i] = true;
	}
	
	public void deleteElement(int i) {
		this.arr[i] = false;
	}
	
	
	public String toString() {
		IntegerSet v = new IntegerSet();
		String result = "";
		
		if(isEqualTo(this,v)) {
			return "---";
		}
		
		for(int i=0; i<101;i++) {
			if(this.arr[i]) {
				result += i+" ";
			}
		}
		return result;
	}
	
	
	
	public boolean isEqualTo(IntegerSet s1, IntegerSet s2) {
		for(int i=0; i<101; i++) {
			if(s1.arr[i] != s2.arr[i]) {
				return false; // 하나라도 다르면 즉시 검사 종료
			}
		}
		return true;
	}
	
}
