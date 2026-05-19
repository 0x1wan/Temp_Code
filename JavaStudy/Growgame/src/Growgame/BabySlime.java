package Growgame;

public class BabySlime extends Baby {

   
    private boolean[] expWeather = new boolean[11];
    private boolean[] expFields = new boolean[6];

    public BabySlime(int fullness, int affinity, int stress) {
        super(fullness, affinity, stress);
        this.tribe = "슬라임";

     
        this.hp = 80;
        this.speed = 80;
        this.attack = 80;
        this.defense = 80;
        this.magic = 80;
    }

    @Override
    protected void displayIn() {
        System.out.println("종족: " + this.tribe);
        System.out.println("포만감: " + this.fullness);
        System.out.println("친밀도: " + this.affinity);
        System.out.println("스트레스: " + this.stress);
        
    }

    @Override
    public int state(int choice, int w) {
        // 행동 영향 로직 구현
        switch (choice) {
            case 0: // 기본 조정 (턴 경과)
                this.fullness -= (2 + random.nextInt(3));
                this.affinity += (1 + random.nextInt(2));
                this.stress -= (2 + random.nextInt(2));
                break;

            case 1: // 놀아주기
                this.fullness -= (3 + random.nextInt(3));
                this.affinity += (6 + random.nextInt(4));
                this.stress -= (3 + random.nextInt(3)); // 스트레스 감소
                break;

            case 2: // 먹이주기
                this.fullness += (15 + random.nextInt(10));
                this.affinity += (1 + random.nextInt(3));
                this.stress -= (2 + random.nextInt(2)); // 스트레스 감소
                break;

            case 3: // 훈련하기
                this.stress += (5 + random.nextInt(5)); // 스트레스 증가
                this.fullness -= (15 + random.nextInt(5)); // 포만감 감소
                this.affinity += (random.nextInt(5) - 2); // 친밀도 랜덤 증감
                break;

            case 4: // 방치하기
                this.stress += (15 + random.nextInt(10)); // 스트레스 매우 증가
                this.affinity -= (3 + random.nextInt(3)); // 친밀도 살짝 감소
                this.fullness -= (12 + random.nextInt(5)); // 포만감 매우 감소
                break;

            case 5: // 씻기기
                this.affinity += (5 + random.nextInt(5));
                this.stress -= (12 + random.nextInt(5)); // 스트레스 매우 감소
                // 50% 확률로 포만감이 감소하지 않도록 난수 제어
                if (random.nextBoolean()) {
                    this.fullness -= (1 + random.nextInt(3));
                }
                break;

            default:
                return 1; // 예외 처리
        }

        // 스탯 상하한선 방어 코드
        this.fullness = Math.max(0, Math.min(100, this.fullness));
        this.affinity = Math.max(0, Math.min(100, this.affinity));
        this.stress = Math.max(0, this.stress);

        return 0;
    }

    @Override
    public int calEv(int w, int f) {
        // 매 턴 엔진으로부터 전달받는 날씨와 필드 인덱스를 배열에 기록
        if (w >= 0 && w < expWeather.length) expWeather[w] = true;
        if (f >= 0 && f < expFields.length) expFields[f] = true;

        // 모든 날씨를 한 번씩 경험했는지 검사
        boolean allWeather = true;
        for (boolean weather : expWeather) {
            if (!weather) {
                allWeather = false;
                break;
            }
        }

        // 모든 필드를 한 번씩 경험했는지 검사
        boolean allFields = true;
        for (boolean field : expFields) {
            if (!field) {
                allFields = false;
                break;
            }
        }


        // 0. 데미갓: 모든 날씨와 필드를 전부 경험한 경우
        if (allWeather && allFields) {
            return 0;
        }
        // 1. 슬라임킹: 데미갓 조건을 만족 못 하고, 모든 날씨만 경험한 경우
        if (allWeather) {
            return 1;
        }
        // 2. 슬라임퀸: 데미갓 조건을 만족 못 하고, 모든 필드만 경험한 경우
        if (allFields) {
            return 2;
        }

        // [가중치 확률 진화 분기 처리] (확정 개체 조건을 만족하지 못한 경우)
        // 3: 원소슬라임, 4: 다크슬라임
        int[] weights = {0, 0, 0, 50, 1};

        // 3. 원소슬라임
        if (w >= 2 && w <= 7) {
            weights[3] += 15;
        }

        // 4. 다크슬라임 (스트레스 수치에 비례하여 가중치 증가)
        weights[4] += (this.stress / 5); // 스트레스 100일 때 최대 +20점 보정

        // 일반 확률 연산
        int total = weights[3] + weights[4];
        if (total == 0) return 3; // 예외 발생 시 원소슬라임으로 수렴

        int r = random.nextInt(total);
        if (r < weights[3]) {
            return 3;
        } else {
            return 4;
        }
    }
    
    

    @Override
    protected int getStaminaCost(int choice) {
        return (choice >= 1 && choice <= 5) ? 15 : 0;
    }

    @Override
    protected void applyTrainingStats(int choice) {
        switch (choice) {
            case 1: this.hp += 11; System.out.println(this.tribe + "이(가) 탄력을 받아 체력이 11 증가했습니다."); break;
            case 2: this.speed += 11; System.out.println(this.tribe + "이(가) 매끄러워지며 스피드가 11 증가했습니다."); break;
            case 3: this.attack += 11; System.out.println(this.tribe + "의 밀도가 높아져 공격력이 11 증가했습니다."); break;
            case 4: this.defense += 11; System.out.println(this.tribe + "이(가) 단단하게 뭉쳐 방어력이 11 증가했습니다."); break;
            case 5: this.magic += 11; System.out.println(this.tribe + "은(는) 마나를 흡수하여 마력이 11 증가했습니다."); break;
        }
    }

    @Override
    protected String[] getComments() {
        return new String[] {
            this.tribe + "이(가) 젤리처럼 투명하게 반짝이며 탄력을 과시합니다.",
            this.tribe + "은(는) 형태를 자유자재로 바꾸며 주변을 탐색하고 있습니다.",
            this.tribe + "이(가) 바닥의 수분을 흡수하며 몸집을 미세하게 부풀립니다.",
            this.tribe + "은(는) 퐁퐁거리는 경쾌한 소리를 내며 제자리를 맴돕니다.",
            this.tribe + "이(가) 마스터님의 손길에 반응하여 부드럽게 떨립니다.",
            this.tribe + "은(는) 외부의 충격을 흡수할 수 있도록 표면장력을 높이고 있습니다.",
            this.tribe + "이(가) 기분 좋은 듯 둥근 물방울 모양으로 변했습니다."
        };
    }
    
    public int getCode() {
    	return 3;
    }
}