package Hw5;

public class Q8_16 {
	public static void main(String[] args) {
		System.out.println("===HugeInteger 테스트===");

		// 1. 객체 생성 및 parse 테스트
		HugeInteger num1 = new HugeInteger();
		HugeInteger num2 = new HugeInteger();
		HugeInteger num3 = new HugeInteger(); 

		// 39자리의 9와 1자리 1을 세팅
		num1.parse("999999999999999999999999999999999999999"); 
		num2.parse("1"); 
		num3.parse("0");

		System.out.println("num1 입력값: " + num1.toString());
		System.out.println("num2 입력값: " + num2.toString());
		System.out.println("num3 입력값 (0 검증): " + num3.toString());
		System.out.println("------------------------------------------------");

		// 2. 덧셈 테스트 
		num1.add(num2);
		System.out.println("덧셈 결과 (num1 + 1): " + num1.toString()); // 맨 앞에 1이 오고 나머지가 0이 되어야 함

		// 3. 뺄셈 테스트
		// 현재 num1은 위 덧셈으로 인해 끝자리가 0인 상태. 여기서 다시 1을 뺌
		num1.subtract(num2);
		System.out.println("뺄셈 결과 (num1 - 1): " + num1.toString()); // 다시 전부 9로 돌아와야 함

		// 4. 비교 연산 및 0 검증 테스트
		System.out.println("------------------------------------------------");
		System.out.println("num1이 num2보다 큰가? " + num1.isGreaterThan(num2));
		System.out.println("num1이 num2와 같은가? " + num1.isEqualTo(num2));
		System.out.println("num3이 0인가? " + num3.isZero());

		// 5. 음수 방어 로직 테스트
		System.out.println("------------------------------------------------");
		System.out.print("작은 수(num2)에서 큰 수(num1) 빼기 시도: ");
		num2.subtract(num1); 

		System.out.println("=== 테스트 종료 ===");
	}

}
