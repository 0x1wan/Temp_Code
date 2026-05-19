package Growgame;

public class ColdIronGolem extends BabyGolem implements Adult {

    private int stem = 100; 
    
    @Override
    public int getAdultId() {
        return 404;
    }

    public ColdIronGolem(int fullness, int affinity, int stress, int hp, int speed, int attack, int defense, int magic) {
        super(fullness, affinity, stress);
        this.tribe = "한철골렘"; 
        
        this.hp = hp;
        this.speed = speed;
        this.attack = attack;
        this.defense = defense;
        this.magic = magic;

        // 최종 진화 스탯 추가 (공격력 +80, 방어력 +150)
        this.attack += 80;
        this.defense += 150;
        
        System.out.println("\n[ 최종 진화 ] 차가운 쇳덩이가 의지를 품고 깨어납니다!");
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
        System.out.println("✨ 한철골렘의 지독한 한기가 서리며 날씨가 [ 한파 ] 상태로 변경됩니다!");
    }

    @Override
    public int WeatherEffect() {
        return 7; // 한파 = 7
    }
    
    @Override
    public int FieldEffect() {
        return -1; // 없음
    }
    
    @Override
    public int[] TrainingEffect() {
        int[] T = new int[6];
        T[3] = 3; // 공격 훈련(3) 시 +3
        T[4] = 3; // 방어 훈련(4) 시 +3
        return T;
    }
    
    @Override public int getHp() { return this.hp; }
    @Override public int getSpeed() { return this.speed; }
    @Override public int getAttack() { return this.attack; }
    @Override public int getDefense() { return this.defense; }
    @Override public int getMagic() { return this.magic; }
}