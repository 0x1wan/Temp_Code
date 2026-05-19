package Growgame;

public class Immortal extends BabyUndead implements Adult {

    private int stem = 100; 
    
    @Override
    public int getAdultId() {
        return 601;
    }

    public Immortal(int fullness, int affinity, int stress, int hp, int speed, int attack, int defense, int magic) {
        super(fullness, affinity, stress);
        this.tribe = "영생자"; 
        
        // 최종 진화 스탯 추가 (전 스탯 +700)
        this.hp = hp + 700;
        this.speed = speed + 700;
        this.attack = attack + 700;
        this.defense = defense + 700;
        this.magic = magic + 700;
        
        System.out.println("\n[ 최종 진화 ] 순리를 벗어나며 무언가가 변태합니다...");
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
        System.out.println("✨ 영생자의 이질적인 기운에 날씨가 [ 암전 ], 필드가 [ 신비로움 ] 상태로 변경됩니다.");
    }

    @Override
    public int WeatherEffect() {
        return 10; // 암전 = 10
    }
    
    @Override
    public int FieldEffect() {
        return 3; // 신비로움 = 3
    }
    
    @Override
    public int[] TrainingEffect() {
        int[] T = new int[6];
        T[1] = 18; // 체력 훈련(1) 시 +18
        return T;
    }
    
    @Override public int getHp() { return this.hp; }
    @Override public int getSpeed() { return this.speed; }
    @Override public int getAttack() { return this.attack; }
    @Override public int getDefense() { return this.defense; }
    @Override public int getMagic() { return this.magic; }
}
