package Growgame;

public class BabyRobot extends Baby {

    private int software; // 초기값 0
    private int hardware; // 초기값 0
    
    // 매 턴마다 우위를 점한 스탯의 가중치를 은닉 변수
    private int rangerBonus = 0;
    private int warMachineBonus = 0;

    public BabyRobot(int fullness, int affinity, int stress) {
        super(fullness, affinity, stress);
        this.tribe = "프로토타입 전투로봇"; 


        this.hp = 180;
        this.speed = 100;
        this.attack = 120;
        this.defense = 30;
        this.magic = 70;

        this.software = 0;
        this.hardware = 0;
    }

    @Override
    protected void displayIn() {
        System.out.println("종족: " + this.tribe);
        System.out.println("친밀도: " + this.affinity);
        System.out.println("소프트웨어: " + this.software);
        System.out.println("하드웨어: " + this.hardware);
        // 포만감과 스트레스는 외부 UI에서 은닉
    }

    @Override
    public int state(int choice, int w) {
        //행동 영향 로직 구현
        switch (choice) {
            case 0: // 기본 조정
                this.affinity += (random.nextInt(5) - 2); // 살짝 랜덤 증감
                break;

            case 1: // 놀아주기
                this.affinity += (5 + random.nextInt(5));
                this.software += (8 + random.nextInt(5));
                break;

            case 2: // 먹이주기
                this.affinity += (2 + random.nextInt(3));
                this.hardware += (8 + random.nextInt(5));
                break;

            case 3: // 훈련
                this.software += (3 + random.nextInt(4));
                this.hardware += (8 + random.nextInt(5));
                break;

            case 4: // 방치
                this.affinity -= (4 + random.nextInt(4));
                this.software += (10 + random.nextInt(5));
                break;

            case 5: // 씻기기
                this.affinity += (5 + random.nextInt(5));
                this.hardware -= (5 + random.nextInt(4));
                break;

            default:
                return 1;
        }

        // 스탯 상하한선 방어 코드
        this.affinity = Math.max(0, Math.min(100, this.affinity));
        this.software = Math.max(0, Math.min(999, this.software));
        this.hardware = Math.max(0, Math.min(999, this.hardware));
        
        // 은닉 스탯도
        this.fullness = Math.max(0, Math.min(100, this.fullness));
        this.stress = Math.max(0, this.stress);

        return 0;
    }

    @Override
    public int calEv(int w, int f) {
        // 매 턴 누적 가중치 연산
        if (this.software > this.hardware) {
            this.rangerBonus += 2; // 소프트웨어 우위 시 레인저 가산
            this.warMachineBonus -= 1; // 워머신 감산
        } else if (this.hardware > this.software) {
            this.warMachineBonus += 2; // 하드웨어 우위 시 워머신 가산
            this.rangerBonus -= 1; // 레인저 감산
        }


        // 0. 기계신: 두 수치가 정확히 일치할 때
        if (this.software == this.hardware) {
            return 0; 
        }

 
        // 1: 위성머신, 2: 안티매직, 3: 안드로이드, 4: 레인저, 5: 워머신
        int[] weights = {0, 1, 1, 10, 30, 30}; 

        // 1. 전략병기-위성머신
        if (f == 5) weights[1] += 30; // 무중력 가중치 매우 증가
        if (this.hardware >= 90) weights[1] += 15;

        // 2. 대마법병기-안티매직
        if (f == 2) weights[2] += 30; // 과잉 마나 가중치 매우 증가
        if (this.software >= 90) weights[2] += 15;

        // 3. 전투모듈-안드로이드
        // 친밀도 50을 기준으로 50 초과면 가산, 미만이면 감산
        weights[3] += ((this.affinity - 50) / 2); 

        // 4. 정찰모듈-레인저 (턴마다 누적된 값 반영)
        weights[4] += this.rangerBonus;

        // 5. 화력모듈-워머신 (턴마다 누적된 값 반영)
        weights[5] += this.warMachineBonus;

        // 확률 계산 루프
        int total = 0;
        for (int i = 1; i < weights.length; i++) {
            if (weights[i] < 0) weights[i] = 0; // 음수 가중치 0으로 보정
            total += weights[i];
        }

        if (total == 0) return 3; // 예외 발생 시 안드로이드 반환

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
        return (choice >= 1 && choice <= 5) ? 10 : 0;
    }

    @Override
    protected void applyTrainingStats(int choice) {
        switch (choice) {
            case 1: this.hp += 5; System.out.println(this.tribe + "이(가) 내구도 테스트를 통과하여 체력이 5 증가했습니다."); break;
            case 2: this.speed += 10; System.out.println(this.tribe + "이(가) 모터 출력을 최적화하여 스피드가 10 증가했습니다."); break;
            case 3: this.attack += 8; System.out.println(this.tribe + "의 무장 모듈이 업그레이드되어 공격력이 8 증가했습니다."); break;
            case 4: this.defense += 8; System.out.println(this.tribe + "이(가) 장갑판 배열을 재조정하여 방어력이 8 증가했습니다."); break;
            case 5: this.magic += 5; System.out.println(this.tribe + "은(는) 동력원 효율을 개선하여 마력이 5 증가했습니다."); break;
        }
    }

    @Override
    protected String[] getComments() {
        return new String[] {
            this.tribe + "의 기계음이 규칙적인 패턴으로 울려 퍼집니다.",
            this.tribe + "이(가) 렌즈의 초점을 맞추며 주변 데이터를 스캔하고 있습니다.",
            this.tribe + "은(는) 시스템 냉각을 위해 옅은 증기를 배출합니다.",
            this.tribe + " 내부의 톱니바퀴와 모터가 정교하게 맞물려 돌아갑니다.",
            this.tribe + "이(가) 마스터님의 지시를 기다리며 대기 모드를 유지합니다.",
            this.tribe + "은(는) 관절부의 유압을 점검하며 기계적인 움직임을 보입니다.",
            this.tribe + "의 디스플레이 패널에 알 수 없는 연산 코드가 빠르게 지나갑니다."
        };
    }
    
    public int getCode() {
    	return 5;
    }
}