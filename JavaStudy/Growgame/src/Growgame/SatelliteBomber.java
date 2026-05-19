package Growgame;

public class SatelliteBomber extends BabyRobot implements Adult {

    private int stem = 100; 
    
    @Override
    public int getAdultId() {
        return 501;
    }

    public SatelliteBomber(int fullness, int affinity, int stress, int hp, int speed, int attack, int defense, int magic) {
        super(fullness, affinity, stress);
        this.tribe = "전략병기-위성폭격기"; 
        
        this.hp = hp;
        this.defense = defense;
        this.magic = magic;

        // 최종 진화 스탯 추가 (스피드 +80, 공격력 +300)
        this.speed = speed + 80;
        this.attack = attack + 300;
        
        System.out.println("\n[ 최종 진화 ] 상공에 거대한 무언가가 강림합니다!");
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
        System.out.println("✨ 위성폭격기의 난기류와 궤도 진입 영향으로 날씨가 [ 바람 ], 필드가 [ 무중력 ] 상태로 연동됩니다.");
    }

    @Override
    public int WeatherEffect() {
        return 3; // 바람 = 3
    }
    
    @Override
    public int FieldEffect() {
        return 5; // 무중력 = 5
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
