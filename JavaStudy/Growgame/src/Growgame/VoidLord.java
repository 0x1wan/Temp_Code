package Growgame;

public class VoidLord extends BabyVoid implements Adult {

    private int stem = 100; 
    
    @Override
    public int getAdultId() {
        return 702;
    }

    public VoidLord(int fullness, int affinity, int stress, int hp, int speed, int attack, int defense, int magic) {
        super(fullness, affinity, stress);
        this.tribe = "공허군주"; 
        
        // 최종 진화 스탯 추가 (모든 스탯 +2121)
        this.hp = hp + 2121;
        this.speed = speed + 2121;
        this.attack = attack + 2121;
        this.defense = defense + 2121;
        this.magic = magic + 2121;
        
        System.out.println("\n[ 최종 진화 ] 당신은 영문도 모른채로 어디론가 걸어갑니다... 천천히.. 계속...");
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
        System.out.println("✨ 공허군주의 위압적인 존재감이 필드를 [ 공허 ] 상태로 상시 왜곡합니다.");
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
        // 모든 스탯 +21 버프 (1~5)
        for (int i = 1; i <= 5; i++) {
            T[i] = 21;
        }
        return T;
    }
    
    @Override public int getHp() { return this.hp; }
    @Override public int getSpeed() { return this.speed; }
    @Override public int getAttack() { return this.attack; }
    @Override public int getDefense() { return this.defense; }
    @Override public int getMagic() { return this.magic; }
}
