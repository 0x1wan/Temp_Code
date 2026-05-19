package Growgame;

public class SlimeQueen extends BabySlime implements Adult {

    private int stem = 100; 
    
    @Override
    public int getAdultId() {
        return 302;
    }

    public SlimeQueen(int fullness, int affinity, int stress, int hp, int speed, int attack, int defense, int magic) {
        super(fullness, affinity, stress);
        this.tribe = "슬라임퀸"; 
        
        // 최종 진화 스탯 추가 (모든 스탯 +200)
        this.hp = hp + 200;
        this.speed = speed + 200;
        this.attack = attack + 200;
        this.defense = defense + 200;
        this.magic = magic + 200;
        
        System.out.println("\n[ 최종 진화 ] 슬라임의 여왕이 튀어오릅니다!");
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
        System.out.println("✨ 슬라임퀸의 자애로운 점성이 둥지 전역의 기운을 복돋웁니다.");
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
        // 모든 스탯 +3 버프 적용
        for (int i = 1; i <= 5; i++) {
            T[i] = 3;
        }
        return T;
    }
    
    @Override public int getHp() { return this.hp; }
    @Override public int getSpeed() { return this.speed; }
    @Override public int getAttack() { return this.attack; }
    @Override public int getDefense() { return this.defense; }
    @Override public int getMagic() { return this.magic; }
}
