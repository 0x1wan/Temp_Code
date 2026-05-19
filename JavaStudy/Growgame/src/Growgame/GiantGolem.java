package Growgame;

public class GiantGolem extends BabyGolem implements Adult {

    private int stem = 100; 
    
    @Override
    public int getAdultId() {
        return 400;
    }

    public GiantGolem(int fullness, int affinity, int stress, int hp, int speed, int attack, int defense, int magic) {
        super(fullness, affinity, stress);
        this.tribe = "거대골램"; 
        
        this.hp = hp;
        this.speed = speed;
        this.attack = attack;
        this.defense = defense;
        this.magic = magic;

        // 최종 진화 스탯 추가 (체력, 방어력 +250)
        this.hp += 250;
        this.defense += 250;
        
        System.out.println("\n[ 최종 진화 ] 거석신앙이 모습을 드러냅니다!");
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
        System.out.println("✨ 거대골램이 육중하게 자리 잡으며 주변이 자욱한 [ 황사 ] 상태로 고정됩니다!");
    }

    @Override
    public int WeatherEffect() {
        return 6; // 황사 = 6
    }
    
    @Override
    public int FieldEffect() {
        return -1; // 없음
    }
    
    @Override
    public int[] TrainingEffect() {
        int[] T = new int[6];
        T[4] = 5; // 방어 훈련(4) 시 +5
        return T;
    }
    
    @Override public int getHp() { return this.hp; }
    @Override public int getSpeed() { return this.speed; }
    @Override public int getAttack() { return this.attack; }
    @Override public int getDefense() { return this.defense; }
    @Override public int getMagic() { return this.magic; }
}
