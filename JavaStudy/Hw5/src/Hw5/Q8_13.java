package Hw5;

public class Q8_13 {

	public static void main(String[] args) {
		
		System.out.println("=== IntegerSet 테스트===");
		
		// 1. 공집합 생성 테스트
		IntegerSet set1 = new IntegerSet();
		IntegerSet set2 = new IntegerSet();
		System.out.println("초기 set1: " + set1.toString());
		
		// 2. 요소 삽입 테스트
		set1.insertElement(10);
		set1.insertElement(20);
		set1.insertElement(30);
		System.out.println("set1 (10, 20, 30 삽입): " + set1.toString());
		
		set2.insertElement(20);
		set2.insertElement(30);
		set2.insertElement(40);
		System.out.println("set2 (20, 30, 40 삽입): " + set2.toString());
		
		// 3. 합집합(Union) 테스트
		IntegerSet unionSet = IntegerSet.union(set1, set2);
		System.out.println("합집합 결과 (기댓값: 10 20 30 40): " + unionSet.toString());
		
		// 4. 교집합(Intersection) 테스트
		IntegerSet intersectionSet = IntegerSet.intersection(set1, set2);
		System.out.println("교집합 결과 (기댓값: 20 30): " + intersectionSet.toString());
		
		// 5. 삭제(Delete) 테스트
		set1.deleteElement(10);
		System.out.println("set1에서 10 삭제 후: " + set1.toString());
		
		// 6. 동치(isEqualTo) 테스트
		System.out.println("set1과 set2가 완벽히 같은가?: " + set1.isEqualTo(set1, set2));
		
		// 7. 공집합 동치 테스트 추가
		IntegerSet emptySet = new IntegerSet();
		System.out.println("빈 객체끼리 비교: " + emptySet.isEqualTo(emptySet, new IntegerSet()));
	}
}


	