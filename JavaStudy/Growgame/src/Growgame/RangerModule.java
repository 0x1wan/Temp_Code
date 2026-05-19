package Growgame;

public class RangerModule extends BabyRobot implements Adult {

    private int stem = 100; 
    
    @Override
    public int getAdultId() {
        return 504;
    }

    public RangerModule(int fullness, int affinity, int stress, int hp, int speed, int attack, int defense, int magic) {
        super(fullness, affinity, stress);
        this.tribe = "정찰모듈-레인저"; 
        
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.magic = magic;

        // 최종 진화 스탯 추가 (스피드 +120)
        this.speed = speed + 120;
        
        System.out.println("\n[ 최종 진화 ] 전략적 정찰 모드를 실행합니다!");
    }

    @Override
    protected void displayIn() {
        System.out.println("종족: " + this.tribe);
        System.out.println("[ 스탯 ]");
        System.out.println("HP: " + this.hp);
        System.out.println("SPEED: " + this.speed);
        System.out.println("ATTACK: " + this.attack);
        System.out.println("DEFENSE: " + this.defense);
        System.out.println("MAGIC: " + this.magic);
    }

    @Override
    public int getStem() { return this.stem; }

    @Override
    public void setStem(int stem) { this.stem = stem; }

    @Override
    public void ate(int choice) {
        this.executeTraining(choice, this);
    }

    @Override
    public void prc() {
        this.printComment();
    }

    @Override
    public int calEv(int w, int f) {
        return -1; 
    }

    @Override
    public void question() {
        this.qt();
    }

    @Override
    public int state(int choice, int w) {
        this.dt(choice);
        return 0; 
    }
    
    @Override
    public void nestprint() {
        System.out.println("\n[ 둥지 안착 효과 발동 ]");
        System.out.println("✨ 레인저의 가벼운 구동 시스템이 전역 스피드 훈련의 데이터 수집을 보조합니다.");
    }

    @Override
    public int WeatherEffect() {
        return -1; // 없음
    }
    
    @Override
    public int FieldEffect() {
        return -1; // 없음
    }
    
    @Override
    public int[] TrainingEffect() {
        int[] T = new int[6];
        T[2] = 5; // 스피드 훈련(2) 시 +5
        return T;
    }
    
    @Override public int getHp() { return this.hp; }
    @Override public int getSpeed() { return this.speed; }
    @Override public int getAttack() { return this.attack; }
    @Override public int getDefense() { return this.defense; }
    @Override public int getMagic() { return this.magic; }
}
