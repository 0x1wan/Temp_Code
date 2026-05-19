package Growgame;

public class AntiMagicWeapon extends BabyRobot implements Adult {

    private int stem = 100; 
    
    @Override
    public int getAdultId() {
        return 502;
    }

    public AntiMagicWeapon(int fullness, int affinity, int stress, int hp, int speed, int attack, int defense, int magic) {
        super(fullness, affinity, stress);
        this.tribe = "대마법병기-안티매직"; 
        
        this.hp = hp;
        this.speed = speed;
        this.attack = attack;
        this.defense = defense;

        // 최종 진화 스탯 추가 (마법 +300)
        this.magic = magic + 300;
        
        System.out.println("\n[ 최종 진화 ] 모든 마법이 역산되기 시작합니다..!");
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
        System.out.println("✨ 안티매직의 전자기 펄스가 왜곡을 일으켜 필드가 [ 자기장 ] 상태로 고정됩니다!");
    }

    @Override
    public int WeatherEffect() {
        return -1; // 없음
    }
    
    @Override
    public int FieldEffect() {
        return 1; // 자기장 = 1
    }
    
    @Override
    public int[] TrainingEffect() {
        int[] T = new int[6];
        T[5] = 10; // 마법 집중 훈련(5) 시 +10
        return T;
    }
    
    @Override public int getHp() { return this.hp; }
    @Override public int getSpeed() { return this.speed; }
    @Override public int getAttack() { return this.attack; }
    @Override public int getDefense() { return this.defense; }
    @Override public int getMagic() { return this.magic; }
}
