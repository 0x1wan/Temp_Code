package Growgame;

public class ElderDragon extends BabyDragon implements Adult {

    private int stem = 100; 
    
    public int getAdultId(){
    	return 100;
    }

    public ElderDragon(int fullness, int affinity, int stress, int hp, int speed, int attack, int defense, int magic) {
        super(fullness, affinity, stress);
        this.tribe = "엘더드래곤"; 
        
        this.hp = hp;
        this.speed = speed;
        this.attack = attack;
        this.defense = defense;
        this.magic = magic;

        // 최종 진화 스탯 추가
        this.hp += 200;
        this.magic += 150;
        System.out.println("\n[ 최종 진화 ] 엘더드래곤이 울부짖습니다!");
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
    
    
    public void nestprint() {//둥시 안착 시 한번만 나오는 멘트.
    	System.out.println("\n[ 둥지 안착 효과 발동 ]");
        System.out.println("✨ 엘더드래곤의 거대한 포효로 날씨가 [ 종말 ] 상태로 고정됩니다!");
    }
    // 확정형 둥지 효과 로직 반영
    public int WeatherEffect() {
        return 8; //종말
    }
    
    public int FieldEffect() {
    	return -1; //필드 영향 안줌.
    }
    
    public int[] TrainingEffect() {
    	int[] T = new int[6]; //어느 값을 훈련시에 추가할지.
    	T[1] = 3; //체력 +3효과
    	
    	return T;
    }
    
    
    //위쪽 인터페이스에다가 올려줄 값들.
    public int getHp() {return this.hp;}
    public int getSpeed() {return this.speed;}
    public int getAttack() {return this.attack;}
    public int getDefense() {return this.defense;}
    public int getMagic() {return this.magic;}
}