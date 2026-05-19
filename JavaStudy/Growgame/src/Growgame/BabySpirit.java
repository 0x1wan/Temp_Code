package Growgame;

public class BabySpirit extends Baby {

    private int manaAdaptability; // 마나 적응력
    private boolean hasExperiencedVoid = false; // 공허 필드 경험 여부 트래킹

    public BabySpirit(int fullness, int affinity, int stress) {
        super(fullness, affinity, stress);
        this.tribe = "꼬마 정령";

        // 정령 고유 기본 전투 스탯
        this.hp = 50;
        this.speed = 120;
        this.attack = 50;
        this.defense = 50;
        this.magic = 210;

        // 신규 스탯 초기화
        this.manaAdaptability = 55; 
    }

    @Override
    protected void displayIn() {
        System.out.println("종족: " + this.tribe);
        System.out.println("포만감: " + this.fullness);
        System.out.println("친밀도: " + this.affinity);
        System.out.println("스트레스: " + this.stress);
        System.out.println("마나 적응력: " + this.manaAdaptability);
    }

    @Override
    public int state(int choice, int w) {
        // 행동 영향 로직 구현
        switch (choice) {
            case 0: // 기본 조정 (턴 경과)
                this.fullness -= (3 + random.nextInt(4));
                this.affinity += (random.nextInt(5) - 2);
                this.manaAdaptability += (2 + random.nextInt(3));
                
                // 20% 확률로 스트레스가 급증하도록 난수 조건
                if (random.nextInt(100) < 20) {
                    this.stress += (15 + random.nextInt(10));
                }
                break;

            case 1: // 놀아주기
                this.fullness -= (3 + random.nextInt(4));
                this.affinity += (6 + random.nextInt(4));
                this.manaAdaptability += (1 + random.nextInt(3));
                break;

            case 2: // 먹이주기
                this.fullness += (15 + random.nextInt(10));
                this.affinity += (2 + random.nextInt(3));
                this.stress -= (5 + random.nextInt(5));
                break;

            case 3: // 훈련하기
                this.manaAdaptability += (12 + random.nextInt(8)); // 매우 증가
                this.stress += (4 + random.nextInt(4));
                this.affinity += (random.nextInt(5) - 2);
                this.fullness -= (18 + random.nextInt(8)); // 매우 감소
                break;

            case 4: // 방치하기
                this.stress += (15 + random.nextInt(10)); // 매우 증가
                this.affinity -= (4 + random.nextInt(4));
                this.fullness -= (4 + random.nextInt(4));
                break;

            case 5: // 씻기기
                this.affinity += (5 + random.nextInt(5));
                this.stress -= (6 + random.nextInt(4));
                if (random.nextBoolean()) {
                    this.fullness -= (2 + random.nextInt(3));
                }
                break;

            default:
                return 1;
        }

        // 수치 한계선 보정
        this.fullness = Math.max(0, Math.min(100, this.fullness));
        this.affinity = Math.max(0, Math.min(100, this.affinity));
        this.stress = Math.max(0, this.stress); 
        this.manaAdaptability = Math.max(0, this.manaAdaptability); // 마나 적응력은 상한을 두지 않음

        return 0;
    }

    @Override
    public int calEv(int w, int f) {
        // 공허필드 체크
        if (f == 4) {
            this.hasExperiencedVoid = true; 
        }

        // 0: 미정령, 1: 정령왕, 2: 암흑정령, 3: 원소정령
        int[] weights = {0, 0, 5, 50}; 

        // 0미정령
        if (this.manaAdaptability < 100) {
            return 0; // 조건 미달 시 무조건 미정령 반환
        }

        // 1. 정령왕 
        if (this.manaAdaptability >= 150 && !this.hasExperiencedVoid) {
            weights[1] += 30; 
            if (f == 3 || f == 5) weights[1] += 10; // 신비로움, 무중력
            if (w == 8) weights[1] += 10; // 종말
        }

        // 2. 암흑정령 
        weights[2] += (this.stress / 5); // 최대 +20점 가산
        weights[2] += ((100 - this.fullness) / 5); // 포만감이 낮을수록 가점
        if (w == 10) weights[2] += 30; // 암전에 매우 높은 가점

        // 3. 원소정령 
        if (w >= 2 && w <= 7) { 
            weights[3] += 15; 
        }

        // 확률 계산 루프
        int total = 0;
        for (int i = 1; i < weights.length; i++) {
            if (weights[i] < 0) weights[i] = 0;
            total += weights[i];
        }

        if (total == 0) return 3; 

        int r = random.nextInt(total);
        int cum = 0;
        for (int i = 1; i < weights.length; i++) {
            cum += weights[i];
            if (r < cum) return i;
        }
        
        return 3;
    }
    
    


    @Override
    protected int getStaminaCost(int choice) {
        int[] costs = {0, 15, 7, 5, 5, 30};
        return costs[choice];
    }

    @Override
    protected void applyTrainingStats(int choice) {
        switch (choice) {
            case 1: this.hp += 3; System.out.println(this.tribe + "이(가) 정기를 흡수하여 체력이 3 증가했습니다."); break;
            case 2: this.speed += 7; System.out.println(this.tribe + "은(는) 바람의 기운을 받아드려 스피드가 7 증가했습니다."); break;
            case 3: this.attack += 3; System.out.println(this.tribe + "은(는) 주인을 위해 공격력이 3 증가했습니다."); break;
            case 4: this.defense += 3; System.out.println(this.tribe + "이(가) 마력을 둘러 방어력이 3 증가했습니다."); break;
            case 5: this.magic += 25; System.out.println(this.tribe + "은(는) 진정한 깨달음을 얻어 마력이 25 증가했습니다."); break;
        }
    }

    @Override
    protected String[] getComments() {
        return new String[] {
            this.tribe + "이(가) 투명한 형태가 더욱 선명해지며 영롱한 빛을 발합니다.",
            this.tribe + " 주변으로 순수한 정령 마나가 소용돌이치고 있습니다.",
            this.tribe + "의 맑은 눈동자가 허공의 마나 흐름을 조용히 쫓고 있습니다.",
            this.tribe + "이(가) 가볍게 부유하자 주변의 온도가 미세하게 변화합니다.",
            this.tribe + "의 외형 내부에서 원소의 기운이 강하게 응축되는 것이 느껴집니다.",
            this.tribe + "이(가) 공기 중의 순수 마나를 흡수하며 투명한 울림소리를 냅니다.",
            this.tribe + "이(가) 마스터님의 마력 파동을 감지하고 주변을 친근하게 맴돕니다."
        };
    }
    
    public int getCode() {
    	return 2;
    }
}