package Growgame;

public class SpiritKing extends BabySpirit implements Adult {

    private int stem = 100; 
    
    @Override
    public int getAdultId() {
        return 201;
    }

    public SpiritKing(int fullness, int affinity, int stress, int hp, int speed, int attack, int defense, int magic) {
        super(fullness, affinity, stress);
        this.tribe = "정령왕"; 
        
        // 최종 진화 스탯 추가 (모든 스탯 +80)
        this.hp = hp + 80;
        this.speed = speed + 80;
        this.attack = attack + 80;
        this.defense = defense; // 누락 방지: 모든 스탯 +80 명세 준수
        this.defense = defense + 80;
        this.magic = magic + 80;
        
        System.out.println("\n[ 최종 진화 ] 정령왕이 물질계에 현현합니다!");
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
        System.out.println("✨ 정령왕의 압도적인 기운이 필드를 [ 마나과잉 ] 상태로 뒤덮습니다.");
    }

    @Override
    public int WeatherEffect() {
        return -1; // 없음
    }
    
    @Override
    public int FieldEffect() {
        return 2; // 마나과잉 = 2
    }
    
    @Override
    public int[] TrainingEffect() {
        int[] T = new int[6];
        T[5] = 8; // 마법 집중 훈련(5) 시 +8
        return T;
    }
    
    @Override public int getHp() { return this.hp; }
    @Override public int getSpeed() { return this.speed; }
    @Override public int getAttack() { return this.attack; }
    @Override public int getDefense() { return this.defense; }
    @Override public int getMagic() { return this.magic; }
}
