package Growgame;

public class BabyVoid extends Baby {

    private int evo; // 진화치 (0 ~ 999, 초기값 0)

    public BabyVoid(int fullness, int affinity, int stress) {
        // 부모 생성자를 통해 기본 스탯을 이관받지만, 공허유충은 내부적으로 포만감과 친밀도를 표시ㄴ
        super(fullness, affinity, stress);
        this.tribe = "공허유충"; 


        this.hp = 199;
        this.speed = 99;
        this.attack = 99;
        this.defense = 99;
        this.magic = 199;

        this.evo = 0;
    }

    @Override
    protected void displayIn() {
        System.out.println("종족: " + this.tribe);
        System.out.println("스트레스: " + this.stress);
        System.out.println("진화치: " + this.evo + " / 999");
    }

    @Override
    public int state(int choice, int w) {
  
        switch (choice) {
            case 0: // 기본 조정
                this.stress += (3 + random.nextInt(4)); // 스트레스 증가
                break;

            case 1: // 놀아주기
                this.stress -= (5 + random.nextInt(4)); // 스트레스 감소
                this.evo += (10 + random.nextInt(11));  // 진화치 10~20 증가
                break;

            case 2: // 먹이주기 (밥)
                this.stress -= (4 + random.nextInt(4)); // 스트레스 감소
                this.evo += (20 + random.nextInt(20));  // 진화치 20~39 증가
                break;

            case 3: // 훈련
                this.stress += (4 + random.nextInt(4)); // 스트레스 증가
                this.evo += (20 + random.nextInt(20));  // 진화치 20~39 증가
                break;

            case 4: // 방치
                this.stress += (8 + random.nextInt(5)); // 스트레스 증가
                this.evo += (10 + random.nextInt(15));  // 진화치 10~24 증가
                break;

            case 5: // 씻기
                this.stress += (3 + random.nextInt(3)); // 스트레스 증가
                this.evo += (10 + random.nextInt(26));  // 진화치 10~35 증가
                break;

            default:
                return 1;
        }


        this.stress = Math.max(0, Math.min(100, this.stress));
        this.evo = Math.max(0, Math.min(999, this.evo));
        
  
        this.fullness = Math.max(0, Math.min(100, this.fullness));
        this.affinity = Math.max(0, Math.min(100, this.affinity));

        return 0;
    }

    @Override
    public int calEv(int w, int f) {
 
        
        // 0. 공허 여왕: 진화치 999 도달 시 최우선 고정 진화
        if (this.evo >= 999) {
            return 0;
        }
        
        // 1. 공허 포식자: 스트레스 최댓값(100) 유지 및 진화치 850 이상일 때 고정 진화
        if (this.stress >= 100 && this.evo >= 850) {
            return 1;
        }


        //  2:군주, 3:유랑자, 4:생산자, 5:수확자
        int[] weights = {0, 0, 1, 31, 41, 21};
        
        if (this.stress == 0) {
            weights[2] += 29; // 공허군주 추가 확률
        }

        // 일반 확률 연산 
        int total = 0;
        for (int i = 2; i < weights.length; i++) {
            total += weights[i];
        }

        if (total == 0) return 4; // 예외 발생 시 베이스가 가장 높은 공허 생산자 반환

        int r = random.nextInt(total);
        int cum = 0;
        for (int i = 2; i < weights.length; i++) {
            cum += weights[i];
            if (r < cum) return i;
        }
        return 4;
    }
    
    @Override
    protected int getStaminaCost(int choice) {
        int[] costs = {0, 10, 20, 10, 20, 10};
        return costs[choice];
    }

    @Override
    protected void applyTrainingStats(int choice) {
        switch (choice) {
            case 1: this.hp += 10; System.out.println(this.tribe + "이(가) 음기를 흡수하여 체력이 10 증가했습니다."); break;
            case 2: this.speed += 5; System.out.println(this.tribe + "의 낡은 관절이 마력을 받아 스피드가 5 증가했습니다."); break;
            case 3: this.attack += 10; System.out.println(this.tribe + "이(가) 흉포한 기운을 뿜어내며 공격력이 10 증가했습니다."); break;
            case 4: this.defense += 10; System.out.println(this.tribe + "은(는) 뼈와 살을 단단하게 굳혀 방어력이 10 증가했습니다."); break;
            case 5: this.magic += 10; System.out.println(this.tribe + " 주변의 원혼이 짙어지며 마력이 10 증가했습니다."); break;
        }
    }

    @Override
    protected String[] getComments() {
        return new String[] {
            this.tribe + "이(가) 불길한 안개를 뿜어내며 기괴하게 일그러집니다.",
            this.tribe + "은(는) 초점 없는 눈으로 허공의 무언가를 응시합니다.",
            this.tribe + " 주변의 온도가 급격히 떨어지며 서늘한 한기가 감돕니다.",
            this.tribe + "이(가) 삐걱거리는 소리를 내며 불규칙하게 움직입니다.",
            this.tribe + "의 몸통에서 죽음의 마나가 짙은 그림자처럼 흘러내립니다.",
            this.tribe + "은(는) 산 자의 생명력을 탐하듯 거친 숨소리를 냅니다.",
            this.tribe + "이(가) 땅바닥에 기분 나쁜 얼룩을 남기며 제자리를 맴돕니다."
        };
    }
    
    public int getCode() {
    	return 7;
    }
}