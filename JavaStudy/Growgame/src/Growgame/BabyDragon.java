package Growgame;

public class BabyDragon extends Baby {

    // 드래곤 전용 신규 스탯
    private int ferocity;        // 포악함 (0 ~ 100)
    private int manaAdaptability; // 마나 적응력 (0 ~ 100)

    public BabyDragon(int fullness, int affinity, int stress) {
        super(fullness, affinity, stress);
        this.tribe = "유년기 드래곤";

        // 개체치 (Baby 단계에서는 고정)
        this.hp = 110;
        this.speed = 80;
        this.attack = 100;
        this.defense = 100;
        this.magic = 150;

        // 신규스탯
        this.ferocity = 0;
        this.manaAdaptability = 55;
    }

    @Override
    protected void displayIn() {
        System.out.println("종족: " + this.tribe);
        System.out.println("포만감: " + this.fullness);
        System.out.println("친밀도: " + this.affinity);
        System.out.println("스트레스: " + this.stress);
        System.out.println("포악함: " + this.ferocity);
        System.out.println("마나 적응력: " + this.manaAdaptability);
    }

    @Override
    public int state(int choice, int w) {

        switch (choice) {
            case 0: // 기본 조정 (하루 종료 시)
                this.fullness -= (5 + random.nextInt(5));
                this.ferocity += (2 + random.nextInt(3));
                int affDiff = random.nextInt(5) - 2;
                this.affinity += affDiff;
                this.stress -= affDiff - 1;
                this.ferocity -= affDiff; // 포악함은 친밀도와 반대로 증감
                this.manaAdaptability += (1 + random.nextInt(2));
                break;

            case 1: // 놀아주기
                this.fullness -= (3 + random.nextInt(3));
                this.ferocity -= (4 + random.nextInt(4));
                this.affinity += (8 + random.nextInt(5));
                this.manaAdaptability += 1;
                break;

            case 2: // 먹이주기
                this.fullness += (15 + random.nextInt(10));
                this.ferocity -= (2 + random.nextInt(2));
                this.affinity += (2 + random.nextInt(3));
                break;

            case 3: // 훈련하기
                this.manaAdaptability += (10 + random.nextInt(10));
                this.ferocity += (3 + random.nextInt(3));
                this.affinity += (random.nextInt(7) - 3); // -3 ~ +3 랜덤
                this.fullness -= (20 + random.nextInt(10));
                this.stress += (5 + random.nextInt(5));
                break;

            case 4: // 방치하기
                this.ferocity += (10 + random.nextInt(10));
                this.affinity -= (5 + random.nextInt(5));
                this.fullness -= (5 + random.nextInt(5));
                this.stress += (10 + random.nextInt(10));
                break;

            case 5: // 씻기기
                this.affinity += (5 + random.nextInt(5));
                this.ferocity -= (10 + random.nextInt(10));
                this.stress -= (5 + random.nextInt(5));
                break;

            default:
                return 1; // 예외 발생
        }

        // 수치 한계 관리 (0 ~ 100)
        this.ferocity = Math.max(0, Math.min(100, this.ferocity));
        this.manaAdaptability = Math.max(0, Math.min(100, this.manaAdaptability));
        this.fullness = Math.max(0, Math.min(100, this.fullness));
        this.affinity = Math.max(0, Math.min(100, this.affinity));
        this.stress = Math.max(0, this.stress);

        return 0;
    }

    @Override
    public int calEv(int w, int f) {
        // 2차 진화 가중치 연산
        // 인덱스: 0:엘더, 1:원소, 2:환상, 3:토룡, 4:흑염
        int[] weights = {10, 40, 15, 50, 5}; // 베이스라인

        // 0. 엘더드래곤 (기본 10)
        if (w == 9) weights[0] += 15; // 광명 매우 가점 
        if (f == 2) weights[0] += 2; // 과잉마나 가점
        if (this.ferocity > 60) weights[0] -= 3; // 일정 포악함 초과 시 감점 
        if (this.manaAdaptability > 85) weights[0] += 4; // 마나 적응력 조건

        // 1. 원소드래곤 (기본 40)
        // 맑음(0), 흐림(1), 종말(8), 광명(9), 암전(10) 제외 시 가점
        if (w >= 2 && w <= 7) weights[1] += 3; //

        // 2. 환상룡 (기본 15)
        if (f == 3 || f == 2) weights[2] += 4; // 신비로움, 과잉마나 가점
        weights[2] += (this.manaAdaptability / 20); // 마나 적응력 비례
        weights[2] -= (this.ferocity / 30); // 포악함 감점

        // 3. 토룡 (기본 50)
        weights[3] += (this.fullness / 40); // 포만감 비례 가점

        // 4. 흑염룡 (기본 5)
        weights[4] += (this.ferocity / 5); // 포악함 높은 가점
        if (w == 10) weights[4] += 20; // 암전 가점
        if (this.manaAdaptability > 80) weights[4] += 2; // 마나 적응력 조건

        // 확률 계산 루프
        int total = 0;
        for (int i = 0; i < weights.length; i++) {
            if (weights[i] < 0) weights[i] = 0;
            total += weights[i];
        }

        if (total == 0) return 3; // 최악의 경우 가장 흔한 토룡 반환

        int r = random.nextInt(total);
        int cum = 0;
        for (int i = 0; i < weights.length; i++) {
            cum += weights[i];
            if (r < cum) return i;
        }
        return 3;
    }
    
   

    @Override
    protected int getStaminaCost(int choice) {
        int[] costs = {0, 10, 10, 15, 15, 5};
        return costs[choice];
    }

    @Override
    protected void applyTrainingStats(int choice) {
        switch (choice) {
            case 1: this.hp += 5; System.out.println(this.tribe + "이(가) 강인해지며 체력이 5 증가했습니다."); break;
            case 2: this.speed += 5; System.out.println(this.tribe + "의 날개가 더욱 성장하며 스피드가 5 증가했습니다."); break;
            case 3: this.attack += 7; System.out.println(this.tribe + "의 포악성이 증가하며 공격력이 7 증가했습니다."); break;
            case 4: this.defense += 5; System.out.println(this.tribe + "의 비늘이 두터워지며 방어력이 5 증가했습니다."); break;
            case 5: this.magic += 15; System.out.println(this.tribe + "의 마력이 요동칩니다. 마력이 15 증가했습니다."); break;
        }
    }

    @Override
    protected String[] getComments() {
        return new String[] {
            this.tribe + "의 비늘이 눈부시게 빛나고 있습니다.",
            this.tribe + "의 숨결이 대기를 요동치게 만듭니다.",
            this.tribe + " 주변의 마나가 고요히 안정되고 있습니다.",
            this.tribe + "의 눈동자에 고대의 지혜가 깃듭니다.",
            this.tribe + "이(가) 날개를 펼치자 주변에 강풍이 일어납니다.",
            this.tribe + "의 발톱 끝에 응축된 마력이 맴듭니다.",
            this.tribe + "이(가) 낮게 포효하자 대지가 미세하게 진동합니다."
        };
        
    }
    
    public int getCode() {
    	return 1;
    }
    
  
}