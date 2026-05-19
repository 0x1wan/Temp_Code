package Growgame;

import java.security.SecureRandom;

//1. 최상위 추상 클래스: Monster 
public abstract class Monster {
 // 공통 필드 정의 
	protected int fullness;//포만감
	protected int affinity;//친밀도
	protected int stress;//스트레스
	protected String tribe; //종족명
	
	protected int hp;
    protected int speed;
    protected int attack;
    protected int defense;
    protected int magic;

    // 5대 스탯 Getter 메서드
    
    public int getHp() { return this.hp; }
    public int getSpeed() { return this.speed; }
    public int getAttack() { return this.attack; }
    public int getDefense() { return this.defense; }
    public int getMagic() { return this.magic; }

    // getAdultId() 
    // 알(Egg)이나 유년기(Baby) 상태에서는 성체 ID가 존재하지 않으므로, 
    // 기본값으로 -1을 반환하게 고정하여 다른 자식 클래스들이 깨지는 것을 방지
    public int getAdultId() {
        return -1; 
    }
    
	protected SecureRandom random = new SecureRandom();

	public Monster() {
		this.fullness = 50; // 초기 스탯 기본값
		this.affinity = 0;
		this.stress = 0;
	}
	
	// 1. 공통 외부 UI
	public void display(int d, int t, int w, int f) {
		String[] turnNames = {"아침", "점심", "저녁"};
		String[] weatherName = {"맑음", "흐림", "비", "바람", "폭염", "천둥", "황사", "한파", "종말", "광명", "암전"};
		String[] fieldName = {"평화로움", "자기장", "과잉마나", "신비로움", "공허", "무중력"};
			
		System.out.println("\n--------------------------------------------------");
		System.out.println("[ " + d + "일차 " + turnNames[t] + " ] | 날씨: " + weatherName[w] + " | 필드: " + fieldName[f]);
			
		// 내부 데이터 출력은 subclass에서 알아서 
		this.displayIn(); 
			
		System.out.println("--------------------------------------------------");
	}
	public void display() {//최종 훈련시 출력 될 UI
		System.out.println("\n--------------------------------------------------");
		this.displayIn();
		System.out.println("--------------------------------------------------");
	}

 
	public abstract int calEv(int w, int f); //진화 조건 계산 함수.
	public abstract void question();//플레이어 행동 체크.
	public abstract int state(int choice, int w); //수치 입력 받고 반영
	protected abstract void displayIn(); //내부 상세 UI
	
	public int getAdultId(int weather, int fieldEffect) {
        return -1;
    }
 

}