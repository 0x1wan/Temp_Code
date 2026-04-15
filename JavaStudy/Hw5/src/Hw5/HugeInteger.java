package Hw5;

public class HugeInteger {
	

	private int[] arr;
	
	
	public HugeInteger() {
		arr = new int[40];
	}
	
	
	public void parse(String str) {
		int arrIdx = 39; 
		
		for(int i = str.length() - 1; i >= 0; i--) {
			this.arr[arrIdx] = str.charAt(i) - '0';
			arrIdx--; // 배열 인덱스도 같이 한 칸씩 앞으로 이동
		}
	}
	
	
	public String toString() {
		// TODO: 앞에 붙은 불필요한 0들을 제외하고 문자열로 합쳐서 반환할 것
		
		if(isZero()) {
			return "0";
		}
		
		int cnt =0;
		String str = "";
		
		for(int i=0;i<this.arr.length; i++) {
			if(this.arr[i]==0) {
				cnt++;
			}
			else {
				break;
			}
		}
		
		for(int i=cnt; i<this.arr.length;i++) {
			str += this.arr[i];
		}
		
		return str; 
	}
	
	// 덧셈
	public void add(HugeInteger h) {
		// TODO: 자리 올림을 고려하여 배열의 끝(39)부터 0까지 더할 것
		
		boolean carry = false;
		
		for(int i=39; i>=0; i--) {
			int sum;
			
			if(carry) {
				sum = this.arr[i]+h.arr[i]+1;
			}
			else {
				sum = this.arr[i]+h.arr[i];
			}
			
			if(sum>=10) {
				carry = true;
				sum -= 10;
			}
			else {
				carry = false;
			}
			this.arr[i] = sum;
		}
		
		if(carry == true) {
			System.out.println("최대값을 초과했습니다.");
		}
		
	}
	
	// 뺄셈
	public void subtract(HugeInteger h) {
		// TODO: 자리 내림(borrow)을 고려하여 배열의 끝부터 뺄 것
		
		if(isLessThan(h)) {
			System.out.println("음수는 지원하지 않습니다."); 
		}
		else {
			boolean carry = false;
			
			for(int i=39; i>=0; i--) {
				int sub;
				
				if(carry) {
					sub = this.arr[i]-h.arr[i]-1;
				}
				else {
					sub = this.arr[i]-h.arr[i];
				}
				
				if(sub<0) {
					carry = true;
					sub = sub+10;
				}
				else {
					carry = false;
				}
				this.arr[i] = sub;
			}
		}
	}
	
	//아래는 객체 비교 연산
	
	public boolean isZero() {
		HugeInteger v = new HugeInteger();
		if(isEqualTo(v)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isEqualTo(HugeInteger h) {
		for(int i=0; i<40; i++) {
			if(this.arr[i] != h.arr[i]) {
				return false; // 하나라도 다르면 즉시 검사 종료
			}
		}
		return true;
	}
	
	public boolean isNotEqualTo(HugeInteger h) {
		if(isEqualTo(h)) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public boolean isGreaterThan(HugeInteger h) {
		// TODO: 앞에서부터(인덱스 0) 비교하여 내가 더 큰지 확인할 것
		
		for(int i=0; i<this.arr.length;i++) {
			if(this.arr[i] > h.arr[i]) {
				return true;
			}
			else if(this.arr[i]<h.arr[i]) {
				return false;
			}
		}
		
		return false; //동일값일때.
	}
	
	public boolean isLessThan(HugeInteger h) {
		if(isEqualTo(h) || isGreaterThan(h)) {
			return false;
		}
		
		else {
			return true;
		}
	}
	
	public boolean isGreaterThanOrEqualTo(HugeInteger h) {
		if(isLessThan(h)) {
			return false;
		}
		return true; 
	}
	
	public boolean isLessThanOrEqualTo(HugeInteger h) {
		if(isGreaterThan(h)) {
			return false;
		}
		return true;
	}
}