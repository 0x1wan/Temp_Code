package Growgame;

public class AndroidModule extends BabyRobot implements Adult {

    private int stem = 100; 
    
    @Override
    public int getAdultId() {
        return 503;
    }

    public AndroidModule(int fullness, int affinity, int stress, int hp, int speed, int attack, int defense, int magic) {
        super(fullness, affinity, stress);
        this.tribe = "전투모듈-안드로이드"; 
        
        // 최종 진화 스탯 추가 (전체 스탯 +90)
        this.hp = hp + 90;
        this.speed = speed + 90;
        this.attack = attack + 90;
        this.defense = defense + 90;
        this.magic = magic + 90;
        
        System.out.println("\n[ 최종 진화 ] 차가운 전투병기로 업데이트를 시작합니다!");
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
        System.out.println("✨ 안드로이드의 동력로 코어 공진으로 인해 필드가 [ 자기장 ] 상태로 변환됩니다.");
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
        // 전스탯 +4 버프 (1~5)
        for (int i = 1; i <= 5; i++) {
            T[i] = 4;
        }
        return T;
    }
    
    @Override public int getHp() { return this.hp; }
    @Override public int getSpeed() { return this.speed; }
    @Override public int getAttack() { return this.attack; }
    @Override public int getDefense() { return this.defense; }
    @Override public int getMagic() { return this.magic; }
}
