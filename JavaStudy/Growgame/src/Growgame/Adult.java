package Growgame;

public interface Adult {
	
	int getStem();
    void setStem(int stem);

    // 1. qt (Question Training) : 훈련 시작 UI
    default void qt() {
        System.out.println("\n==================================================");
        System.out.println("[ 성체 훈련 모드 ] 현재 스테미너: " + getStem() + " / 100");
        System.out.println("1. 체력 훈련  2. 스피드 훈련  3. 공격 훈련  4. 방어 훈련  5. 마력 집중  6. 휴식");
        System.out.print("입력: ");
    }

    // 2. dt (Do Training) : 훈련 흐름 제어
    default void dt(int choice) {
        if (choice == 6) {
        	int recovered = Math.min(100, getStem() + 30);
            setStem(recovered);
            System.out.println("휴식을 취합니다. 스테미너가 회복되어 " + recovered + "이(가) 되었습니다.");
            return;
        }
        
        ate(choice); // 스탯 증감 연산
        prc();       // 랜덤 코멘트 출력
    }

    // 3. qe (Question End) : 엔딩 선택 UI 출력
    // 팩트: 메인 엔진에서 둥지 배열의 길이를 확인한 후 boolean 값을 넘겨줍니다.
    default void qe(boolean isNestFull) {
        System.out.println("\n==================================================");
        System.out.println("[ 시스템 ] 40일 차에 도달하여 육성이 종료되었습니다.");
        
        if (isNestFull) {
            System.out.println("⚠️ 현재 둥지가 가득 차서 개체를 수용할 수 없습니다.");
            System.out.println("1. 승천시키기 (강제)");
        } else {
            System.out.println("개체의 종착지를 선택해 주십시오.");
            System.out.println("1. 승천시키기  2. 둥지로 보내기");
        }
        System.out.print("입력: ");
    }

    // 4. de (Do End) : 엔딩 실행 로직
    default void de(int choice, boolean isNestFull) {
        // 둥지가 꽉 찼는데 시스템의 경고를 무시하고 2번을 누르거나, 정상적으로 1번을 누른 경우 모두 강제 승천 처리
        if (isNestFull || choice == 1) {
            System.out.println("✨ [ 승천 ] 개체가 한계를 돌파하여 신화적 존재로 승천했습니다! ✨");
            // TODO: 메인 엔진 측에 승천 포인트 누적 및 게임 종료 신호 전달
        } else if (choice == 2) {
            System.out.println("💤 [ 둥지 ] 개체가 기나긴 여정을 마치고 둥지에 안착했습니다.");
            // TODO: 메인 엔진의 Nest 배열에 현재 개체 저장 및 게임 종료 신호 전달
        } else {
            System.out.println("잘못된 입력입니다. 시스템 권한으로 강제 승천을 집행합니다.");
            System.out.println("✨ [ 승천 ] 개체가 신화적 존재로 승천했습니다! ✨");
        }
    }

    // --- 성체 클래스들이 강제로 구현해야 할 추상 메서드들 ---
    void ate(int choice); // Apply Training Effect
    void prc();           // Print Random Comment
    void nestprint();	  //둥지 안착시 나오는 멘트
    int WeatherEffect();  //날씨 효과
    int FieldEffect();    //필드 효과
    int[] TrainingEffect();//훈련효과
    
    int getAdultId();
    int getHp();
    int getSpeed();
    int getAttack();
    int getDefense();
    int getMagic();
    
    default String makeinfo() {//최종 종족 클래스들의 정보를 담아 추가 암호화 후 str형태로 반환하는 메서드.
    	
    	long hash = (getAdultId() * 3L) + 
                (getHp() * 5L) + 
                (getSpeed() * 7L) + 
                (getAttack() * 11L) + 
                (getDefense() * 13L) + 
                (getMagic() * 17L);
        // 997(세 자리 수 중 가장 큰 소수)로 나눈 나머지를 사용하여,
        // SCode를 항상 0 ~ 996 사이의 예측 불가능한 난수처럼 고정 (보안식)
        int SCode = (int) (hash % 997);
        
        String info = "" + getAdultId() + "\n" + getHp() + "\n" + getSpeed() + "\n" + getAttack() + "\n" + getDefense() + "\n" + getMagic();
        info = info + "\nCODE:" + SCode;
        return info;
    }
}