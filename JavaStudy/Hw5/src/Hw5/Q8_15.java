package Hw5;

public class Q8_15 {

	public static void main(String[] args) {
		
			System.out.println("=== 유리수(Rational) 연산 테스트 ===");

			Rational r1 = new Rational(2, 4);
			Rational r2 = new Rational(1, 3);
			Rational empty = new Rational(); // 기본 생성자 테스트 (0/1)

			System.out.println("r1 (2/4 입력): " + r1.toFractionString());
			System.out.println("r2 (1/3 입력): " + r2.toFractionString());
			System.out.println("empty (기본 생성자): " + empty.toFractionString());
			System.out.println("------------------------------------------------");

			
			Rational sum = Rational.add(r1, r2);
			System.out.printf("덧셈 (r1 + r2): %s (소수점 둘째 자리: %s)%n", 
					sum.toFractionString(), sum.toFloatString(2));

			
			Rational diff = Rational.subtract(r1, r2);
			System.out.printf("뺄셈 (r1 - r2): %s (소수점 셋째 자리: %s)%n", 
					diff.toFractionString(), diff.toFloatString(3));

			
			Rational prod = Rational.multiply(r1, r2);
			System.out.printf("곱셈 (r1 * r2): %s (소수점 첫째 자리: %s)%n", 
					prod.toFractionString(), prod.toFloatString(1));

			
			Rational div = Rational.divide(r1, r2);
			System.out.printf("나눗셈 (r1 / r2): %s (소수점 넷째 자리: %s)%n", 
					div.toFractionString(), div.toFloatString(4));
			
			System.out.println("=== 테스트 종료 ===");

	}

}
