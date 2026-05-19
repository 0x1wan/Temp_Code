package Growgame;

public class VoidHarvester extends BabyVoid implements Adult {

    private int stem = 100; 
    
    @Override
    public int getAdultId() {
        return 705;
    }

    public VoidHarvester(int fullness, int affinity, int stress, int hp, int speed, int attack, int defense, int magic) {
        super(fullness, affinity, stress);
        this.tribe = "공허수확자"; 
        
        // 최종 진화 스탯 추가 (모든 스탯 +2112)
        this.hp = hp + 2112;
        this.speed = speed + 2112;
        this.attack = attack + 2112;
        this.defense = defense + 2112;
        this.magic = magic + 2112;
        
        System.out.println("\n[ 최종 진화 ] 당신은 스스로의 이름을 되새깁니다... 다만 기억이 나진 않지만요..!");
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
        System.out.println("✨ 공허수확자가 둥지의 모든 에너지를 수확하여 필드를 [ 공허 ] 상태로 유지합니다.");
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