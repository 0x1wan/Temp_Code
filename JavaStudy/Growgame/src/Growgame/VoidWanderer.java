package Growgame;

public class VoidWanderer extends BabyVoid implements Adult {

    private int stem = 100; 
    
    @Override
    public int getAdultId() {
        return 703;
    }

    public VoidWanderer(int fullness, int affinity, int stress, int hp, int speed, int attack, int defense, int magic) {
        super(fullness, affinity, stress);
        this.tribe = "공허유랑자"; 
        
        // 최종 진화 스탯 추가 (모든 스탯 +1221)
        this.hp = hp + 1221;
        this.speed = speed + 1221;
        this.attack = attack + 1221;
        this.defense = defense + 1221;
        this.magic = magic + 1221;
        
        System.out.println("\n[ 최종 진화 ] 당신은 먹은 저녁을 생각합니다만... 도저히 기억이 나지 않습니다!");
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
        System.out.println("✨ 공허유랑자의 발걸음이 머무는 자리가 [ 공허 ] 필드로 침식됩니다.");
    }

    @Override
    public int WeatherEffect() {
        return -1; // 없음
    }
    
    @Override
    public int FieldEffect() {
        return 4; // 공허 = 4
    }
    
    @Override
    public int[] TrainingEffect() {
        int[] T = new int[6];
        // 모든 스탯 +12 버프 (1~5)
        for (int i = 1; i <= 5; i++) {
            T[i] = 12;
        }
        return T;
    }
    
    @Override public int getHp() { return this.hp; }
    @Override public int getSpeed() { return this.speed; }
    @Override public int getAttack() { return this.attack; }
    @Override public int getDefense() { return this.defense; }
    @Override public int getMagic() { return this.magic; }
}
