package Growgame;


public abstract class Baby extends Monster {

    
    public Baby(int fullness, int affinity, int stress) {
        super(); // 부모(Monster)의 기본 생성자를 호출하여 메모리를 먼저 세팅합니다.
        
        // 인자로 전달받은 기존 객체의 스탯으로 현재 객체의 스탯을 덮어씌웁니다.
        this.fullness = fullness;
        this.affinity = affinity;
        this.stress = stress;
    }

    //유년기(Baby) 공통 상호작용 메뉴 오버라이딩
    @Override
    public void question() {
        System.out.println("\n무엇을 하시겠습니까? (1. 놀아주기  2. 밥주기  3. 훈련하기  4. 방치하기  5. 씻기기 0 . 종료)");
        System.out.print("입력: ");
    }
    
    protected void executeTraining(int choice, Adult adultRef) {
        if (choice < 1 || choice > 5) return;

        // 자식 클래스에게 훈련 비용 받아오기.
        int cost = getStaminaCost(choice);
        
        // 스태미너 검증 로직 (공통)
        if (adultRef.getStem() < cost) {
            System.out.println("❌ 스태미너가 부족하여 훈련을 진행할 수 없습니다! (필요: " + cost + ")");
            return;
        }
        
        // 스태미너 차감 (공통)
        adultRef.setStem(adultRef.getStem() - cost);
        
        // 실제 스탯 증가 및 출력 (자식 클래스에게 던져줌)
        applyTrainingStats(choice);
    }

    // 2. 공통 코멘트 출력 로직
    protected void printComment() {
        // 자식 클래스에게 텍스트 배열을 받아옵니다.
        String[] comments = getComments();
        if (comments != null && comments.length > 0) {
            System.out.println("💬 " + comments[random.nextInt(comments.length)]);
        }
    }

    // 종족별 클래스들이 올려보내줄 값들.
    protected abstract int getStaminaCost(int choice);
    protected abstract void applyTrainingStats(int choice);
    protected abstract String[] getComments();
    
    protected abstract int getCode();//무슨 종족인지 정수값 보내는 메서드

    @Override
    public int getAdultId(int weather, int fieldEffect) {
        // 기존에 각 종족별로 짜둔 복잡한 calEv 로직을 실행하여 0, 1, 2 등의 인덱스를 얻습니다.
        int rawResult = this.calEv(weather, fieldEffect);
        
        return (getCode() * 100) + rawResult;
    }
    
}