package Growgame;

public class BabyGolem extends Baby {

    private int solid; // 견고함 (초기값 50)
    
    
    private int lowFullnessTurns = 0; 
    private int coldWaveTurns = 0;    

    public BabyGolem(int fullness, int affinity, int stress) {
        super(fullness, affinity, stress);
        this.tribe = "작은 골렘"; 

        // 골렘 고유 기본 전투 스탯 세팅
        this.hp = 200;
        this.speed = 10;
        this.attack = 80;
        this.defense = 150;
        this.magic = 30;

        this.solid = 50; 
    }

    @Override
    protected void displayIn() {
        System.out.println("종족: " + this.tribe);
        System.out.println("포만감: " + this.fullness);
        System.out.println("친밀도: " + this.affinity);
        System.out.println("견고함: " + this.solid);
        // 골렘은 스트레스가 없기에 은닉.
    }

    @Override
    public int state(int choice, int w) {
        // 행동 영향 로직 구현
        switch (choice) {
            case 0: // 기본 조정
                this.fullness -= (8 + random.nextInt(5)); // 매우 감소
                this.affinity += random.nextInt(3);       // 살짝 랜덤 증가
                break;

            case 1: // 놀아주기
                this.fullness -= (3 + random.nextInt(3));
                this.affinity += (5 + random.nextInt(5));
                break;

            case 2: // 먹이주기
                this.fullness += (15 + random.nextInt(10));
                this.affinity += (1 + random.nextInt(2)); // 살짝 증가
                break;

            case 3: // 훈련
                this.fullness -= (4 + random.nextInt(3));
                this.solid += (6 + random.nextInt(4));    // 견고함 증가
                break;

            case 4: // 방치
                this.solid -= (5 + random.nextInt(4));    // 견고함 감소
                this.fullness -= (10 + random.nextInt(5)); // 매우 감소
                break;

            case 5: // 씻기
                this.affinity += (5 + random.nextInt(4));
                this.solid -= (2 + random.nextInt(2));    // 살짝 감소
                break;

            default:
                return 1;
            }

        // 스탯 한계선 보정
        this.fullness = Math.max(0, Math.min(100, this.fullness));
        this.affinity = Math.max(0, Math.min(100, this.affinity));
        this.solid = Math.max(0, Math.min(100, this.solid));

        return 0;
    }

    @Override
    public int calEv(int w, int f) {
        
        if (this.fullness <= 15) {
            this.lowFullnessTurns++;
        }
        if (w == 7) { // 한파(7) 조우 체크
            this.coldWaveTurns++;
        }

        // 0:거대골렘, 1:키네틱골렘, 2:가드골렘, 3:원소골렘, 4:한철골렘
        int[] weights = {5, 5, 15, 50, 1}; 

        // 0. 거대골렘
        weights[0] += (this.solid / 10); // 견고함 비례 가산 (최대 +10)

        // 1. 키네틱 골렘
        if (f == 5 || f == 3 || f == 2) { // 무중력(5), 신비로움(3), 과잉마나(2)
            weights[1] += 15;
        }

        // 2. 가드골렘
        weights[2] += (this.affinity / 10); // 친밀도 비례 가산
        weights[2] -= (this.fullness / 10); // 포만감 비례 감산

        // 3. 원소골렘
        if (w >= 2 && w <= 7) { // 원소조건 공통 적용
            weights[3] += 10;
        }
        weights[3] += (this.lowFullnessTurns * 15); // 15 이하 조우 횟수당 강한 가산

        // 4. 한철골렘
        weights[4] += (this.coldWaveTurns * 25); // 한파 조우 횟수당 파격적인 가산

        // 확률 계산 루프
        int total = 0;
        for (int i = 0; i < weights.length; i++) {
            if (weights[i] < 0) weights[i] = 0;
            total += weights[i];
        }

        if (total == 0) return 3; // 예외 발생 시 베이스가 가장 높은 원소골렘 반환

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
        int[] costs = {0, 10, 15, 10, 10, 15};
        return costs[choice];
    }

    @Override
    protected void applyTrainingStats(int choice) {
        switch (choice) {
            case 1: this.hp += 20; System.out.println(this.tribe + "이(가) 대지의 기운을 흡수하여 체력이 20 증가했습니다."); break;
            case 2: this.speed += 5; System.out.println(this.tribe + "의 관절부가 미세하게 마모되어 스피드가 5 증가했습니다."); break;
            case 3: this.attack += 15; System.out.println(this.tribe + "이(가) 질량을 응축하여 공격력이 15 증가했습니다."); break;
            case 4: this.defense += 20; System.out.println(this.tribe + "의 외장이 더욱 견고해져 방어력이 20 증가했습니다."); break;
            case 5: this.magic += 5; System.out.println(this.tribe + "이(가) 동력원의 출력을 끌어올려 마력이 5 증가했습니다."); break;
        }
    }

    @Override
    protected String[] getComments() {
        return new String[] {
            this.tribe + "이(가) 육중한 몸체를 일으키며 묵직한 마찰음을 냅니다.",
            this.tribe + "은(는) 침묵 속에서 마스터님의 명령을 묵묵히 대기하고 있습니다.",
            this.tribe + "이(가) 발걸음을 내디딜 때마다 지면이 둔탁하게 울립니다.",
            this.tribe + "은(는) 미동조차 하지 않은 채 흔들림 없는 내구도를 과시합니다.",
            this.tribe + "의 몸체를 구성하는 광물들이 마나에 반응하여 은은하게 공명합니다.",
            this.tribe + "이(가) 방어 태세를 취하며 거대한 성벽처럼 우뚝 섭니다.",
            this.tribe + "은(는) 주변의 파편들을 끌어당겨 자신의 손상된 표면을 메웁니다."
        };
    }
    
    public int getCode() {
    	return 4;
    }
}