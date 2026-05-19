package Growgame;

public class VoidProducer extends BabyVoid implements Adult {

    private int stem = 100; 
    
    @Override
    public int getAdultId() {
        return 704;
    }

    public VoidProducer(int fullness, int affinity, int stress, int hp, int speed, int attack, int defense, int magic) {
        super(fullness, affinity, stress);
        this.tribe = "공허생산자"; 
        
        // 최종 진화 스탯 추가 (모든 스탯 +1111)
        this.hp = hp + 1111;
        this.speed = speed + 1111;
        this.attack = attack + 1111;
        this.defense = defense + 1111;
        this.magic = magic + 1111;
        
        System.out.println("\n[ 최종 진화 ] 당신은 눈앞의 생물을 보고 당혹스러워합니다...");
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
        System.out.println("✨ 공허생산자의 기괴한 포자가 필드를 [ 공허 ] 상태로 고정시킵니다.");
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
        // 모든 스탯 +11 버프 (1~5)
        for (int i = 1; i <= 5; i++) {
            T[i] = 11;
        }
        return T;
    }
    
    @Override public int getHp() { return this.hp; }
    @Override public int getSpeed() { return this.speed; }
    @Override public int getAttack() { return this.attack; }
    @Override public int getDefense() { return this.defense; }
    @Override public int getMagic() { return this.magic; }
}
