package Growgame;

public class BabyUndead extends Baby {

    private int corruption; // 부패 (0~100)
    private int curse;      // 저주 (0~10)
    private int toxicity;   // 유독성 (0~100)

    //기록용 변수
    private int highC = 0;
    private int skeletonBonus = 0;
    private int zeroA = 0;

    public BabyUndead(int fullness, int affinity, int stress) {
        super(fullness, affinity, stress);
        this.tribe = "언데드"; 

        // 언데드 고유 기본 전투 스탯
        this.hp = 90;
        this.speed = 30;
        this.attack = 160;
        this.defense = 50;
        this.magic = 100;

        // 신규 스탯 초기화
        this.corruption = 30;
        this.curse = 0;
        this.toxicity = 30;
    }

    @Override
    protected void displayIn() {
        System.out.println("종족: " + this.tribe);
        System.out.println("친밀도: " + this.affinity);
        System.out.println("부패: " + this.corruption);
        System.out.println("저주: " + this.curse);
        System.out.println("유독성: " + this.toxicity);
    }

    @Override
    public int state(int choice, int w) {
        // 행동 영향 로직 구현
        switch (choice) {
            case 0: // 기본 조정
                this.affinity += (random.nextInt(5) - 2); // 랜덤 증감
                
                // 날씨에 따른 부패 증감
                if (w == 0 || w == 7) this.corruption -= (3 + random.nextInt(3));
                else if (w == 1 || w == 2) this.corruption += (3 + random.nextInt(3));
                
                // 암전(10) 날씨일 때 저주 증가 
                if (w == 10) this.curse++;
                
                // 유독성 랜덤 증가 (저주 * 10 = MAX)
                int maxTox = this.curse * 10;
                if (maxTox > 0) {
                    this.toxicity += random.nextInt(maxTox + 1);
                }
                break;

            case 1: // 놀아주기
                this.affinity += (5 + random.nextInt(5));
                this.corruption += (random.nextInt(5) - 2); // 랜덤 증감
                this.toxicity -= (2 + random.nextInt(3));
                break;

            case 2: // 먹이주기
                this.affinity += (3 + random.nextInt(4));
                this.corruption += (4 + random.nextInt(4));
                this.toxicity -= (3 + random.nextInt(4));
                break;

            case 3: // 훈련
                this.affinity += (2 + random.nextInt(3));
                this.corruption -= (4 + random.nextInt(4));
                this.toxicity += (5 + random.nextInt(5));
                break;

            case 4: // 방치
                this.affinity -= (5 + random.nextInt(5));
                this.corruption += (6 + random.nextInt(5));
                this.toxicity += (5 + random.nextInt(5));
                break;

            case 5: // 씻기
                this.affinity -= (15 + random.nextInt(10)); // 매우 감소
                this.corruption -= (15 + random.nextInt(10)); // 매우 감소
                this.toxicity -= (15 + random.nextInt(10)); // 매우 감소
                break;

            default:
                return 1;
        }

        // 스탯 상하한선 방어 코드
        this.affinity = Math.max(0, Math.min(100, this.affinity));
        this.corruption = Math.max(0, Math.min(100, this.corruption));
        this.curse = Math.max(0, Math.min(10, this.curse));
        this.toxicity = Math.max(0, Math.min(100, this.toxicity));
        

        this.fullness = Math.max(0, Math.min(100, this.fullness));
        this.stress = Math.max(0, this.stress);

        return 0;
    }

    @Override
    public int calEv(int w, int f) {
        // 매 턴 누적 로직
        // state 메서드에 f가 없으므로 여기서 공허(4) 필드에 따른 저주 스탯 증가.
        if (f == 4) {
            this.curse++;
            this.curse = Math.min(10, this.curse);
        }

        if (this.corruption > 80) this.highC++;
        if (this.affinity == 0) this.zeroA++;
        
        if (this.corruption <= 50 && this.toxicity <= 50) {
            this.skeletonBonus += 2;
        } else {
            this.skeletonBonus -= 1;
        }


        // 0. 사신
        if (this.curse == 10) return 0;
        
        // 1. 영생자
        if (this.curse == 0 && this.toxicity < 30 && this.corruption < 30) return 1;


        // 2: 좀비, 3: 스켈레톤, 4: 포식자
        int[] weights = {0, 0, 50, 30, 1};

        // 2. 좀비 (부패가 80을 넘은 턴 수에 비례하여 가산)
        weights[2] += (this.highC * 8);

        // 3. 스켈레톤 (턴마다 누적된 스켈레톤 보너스 반영)
        weights[3] += this.skeletonBonus;

        // 4. 포식자 (친밀도가 0에 닿은 턴 수마다 매우 높은 가산)
        weights[4] += (this.zeroA * 20);

        // 확률 계산 루프
        int total = 0;
        for (int i = 2; i < weights.length; i++) {
            if (weights[i] < 0) weights[i] = 0; 
            total += weights[i];
        }

        if (total == 0) return 2; // 예외 발생 시 기본값이 가장 높은 좀비 반환

        int r = random.nextInt(total);
        int cum = 0;
        for (int i = 2; i < weights.length; i++) {
            cum += weights[i];
            if (r < cum) return i;
        }
        return 2;
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
    	return 6;
    }
}