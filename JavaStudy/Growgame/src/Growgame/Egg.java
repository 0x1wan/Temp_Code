package Growgame;


public class Egg extends Monster{
	
	 public Egg() {
		 super();
		 this.tribe = "알";
	 }
			
	private int temp = 36; //온도 변수 (Egg전용)
	
	// 인덱스: 0(드래곤), 1(정령), 2(슬라임), 3(골렘), 4(로봇), 5(언데드), 6(공허)
    protected int[] evScores = {100, 150, 150, 80, 40, 40, 0}; 

    public int calEv(int w, int f) {
        
        // 드래곤 (0번)
        this.evScores[0] += (this.fullness / 20); 
        if (this.temp > 36) this.evScores[0] += (this.temp - 36) / 2;
        if (w != 0 && w != 1) this.evScores[0] += 2; 
        if (w == 8) this.evScores[0] += 6; 
        if (f != 0) this.evScores[0] += 3; 
        if (w == 9 || w == 10) this.evScores[0] = -9999; 

        // 정령 (1번)
        if (f == 2 || f == 3) this.evScores[1] += 8; 
        if (w == 0 || w == 1) this.evScores[1] += 3; 
        this.evScores[1] -= (this.stress / 10); 
        this.evScores[1] += (this.affinity / 20); 

        // 슬라임 (2번)
        if (f == 0) this.evScores[2] += 4; 

        // 골렘 (3번)
        if (w == 6) this.evScores[3] += 10; 
        if (f == 3 || f == 5) this.evScores[3] += 5; 
        this.evScores[3] += ((100 - this.fullness) / 25); 
        if (this.temp <= 20) this.evScores[3] += 2; // 저온 가산

        // 로봇 (4번)
        if (f == 1) this.evScores[4] += 12; 
        if (w == 5) this.evScores[4] += 5; 
        this.evScores[4] += ((100 - this.fullness) / 20); 
        if (this.temp <= 20) this.evScores[4] += 2; // 저온 가산

        // 언데드 (5번)
        this.evScores[5] += (this.stress / 20); 
        this.evScores[5] += ((100 - this.fullness) / 20); 
        if (w == 10) this.evScores[5] += 10; 
        if (w == 8) this.evScores[5] += 5; 
        if (this.temp <= 20) this.evScores[5] += 2; // 저온 가산

        // 7. 공허생명체 (6번 - 히든) 및 공허 필드(4) 공통 특수 처리
        if (f == 4) {
            // 공허 필드일 경우 공허생명체를 제외한 타 종족(0~5번) 일괄 감산
            for (int i = 0; i < 6; i++) {
                this.evScores[i] -= 3; 
            }
            this.evScores[6] += 6; // 공허생명체는 공허 필드에서 더 높은 가산
        } else if (f == 5) {
            this.evScores[6] += 3; // 무중력(5)은 기존 가산치 유지
        }
        
        if (w == 8 || w == 10) this.evScores[6] += 2; // 종말(8), 암전(10) 가산
        this.evScores[6] += (this.stress / 25); // 스트레스 비례 가산
        
        // === 이하 진화 확률 추출 로직 ===
        
        int totalScore = 0;
        for (int i = 0; i < evScores.length; i++) {
            if (evScores[i] < 0) {
                evScores[i] = 0; // 음수 방지 처리
            }
            totalScore += evScores[i]; 
        }

        // 극단적 예외 상황: 모든 점수가 0이 될 경우 가장 안정적인 슬라임(2) 반환
        if (totalScore == 0) return 2; 

        int randObj = random.nextInt(totalScore); 
        int cumulative = 0;
        
        for (int i = 0; i < evScores.length; i++) {
            cumulative += evScores[i];
            if (randObj < cumulative) {
                return i; // 최종 진화할 종족의 인덱스 반환
            }
        }
        
        return 0; // 컴파일러를 위한 안전 장치
    }
	
	public void question() {
		System.out.println("\n무엇을 하시겠습니까? (1. 돌보기  2. 먹이주기  3. 둥지 관리 4. 방치하기 0. 종료)");
        System.out.print("입력: ");
	}
	
	
	public void displayIn() {//subclass들은 overriding 예정
		//내부 데이터 출력.
		System.out.println("포만감: "+ this.fullness);
		System.out.println("친밀도: "+ this.affinity);
		System.out.println("스트레스: "+ this.stress);
		System.out.println("온도: "+ this.temp + "°C");
		System.out.println("--------------------------------------------------");
		
	}
	
	public int state(int choice , int w) {
		//내부 로직 구현.
		
		// 행동 1, 2, 3을 했을 때 온도가 36도로 수렴하는 로직
		if (choice >= 1 && choice <= 3) {
			int diff = 36 - this.temp;
			
			if (diff > 0) {
				// 거리에 비례하여 오르되 속도를 대폭 늦춤
				this.temp += (diff / 5) + 1; 
			} else if (diff < 0) {
				// 거리에 비례하여 내리되 속도를 대폭 늦춤
				this.temp += (diff / 5) - 1; 
			}
			
			// 완전히 고정되지 않도록 턴마다 -1 ~ +1도의 자연 변동 추가
			this.temp += (random.nextInt(3) - 1); 
		}
		
		if(choice == 1) { 
			//돌보기
			this.affinity += (9 + random.nextInt(5));
			this.stress -= (5 + random.nextInt(3));
			this.fullness -= (2 + random.nextInt(2));
		}
		else if(choice == 2){
			//먹이주기
			this.affinity += (1 + random.nextInt(5));
			this.stress -= (random.nextInt(2));
			this.fullness += (9 + random.nextInt(10));
		}
		else if(choice == 3) {
			//둥지 관리
			this.affinity += (4 + random.nextInt(3));
			this.stress += (2-random.nextInt(5)); // 스트레스가 오를수도, 내릴수도
			this.fullness += (9 + random.nextInt(10));
		}
		else if (choice ==4) {
			//방치 하기
			this.affinity -= (10 + random.nextInt(5));
			this.stress += (random.nextInt(15));
			this.fullness -= (1 + random.nextInt(10));
			this.temp -= (1 + random.nextInt(2));
		}
		else if(choice == 0) { 
			//하루가 지나면 기본적으로 가산되는 데이터 (저쪽에서 state(0)을 호출할거임)
			this.affinity += (5 - random.nextInt(7));
			this.stress += (5 - random.nextInt(7));
			this.fullness -= (1+ random.nextInt(5));
			
			// 날씨(w)에 따른 온도 직접 타격
				if (w == 4) this.temp += (3 + random.nextInt(3)); // 폭염: 온도 대폭 상승					
				else if (w == 7) this.temp -= (3 + random.nextInt(4)); // 한파: 온도 대폭 하락
				else if (w == 2 || w == 3) this.temp -= 2; // 비, 바람: 온도 소폭 하락
				else if (w == 0) this.temp += 1; // 맑음: 온도 소폭 상승
		}
		else {
			//예외 처리
			return 1;
		}
		
		//각 수치 최소 최대 관리.
		if(this.stress < 0) {
			this.stress = 0;
		}
		if(this.affinity < 0) {
			this.affinity = 0;
		}
		else if(this.fullness < 0) {
			this.fullness = 0;
		}
		else if(this.fullness >100) {
			this.fullness = 100;
			this.stress += 5;
		}
		
		return 0;
	}
}
