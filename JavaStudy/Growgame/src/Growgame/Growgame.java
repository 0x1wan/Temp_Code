package Growgame;

import java.security.SecureRandom;

import java.util.ArrayList;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.io.IOException;


public class Growgame {
    // 1. 게임 환경 변수
    protected int day;
    protected int turn; // 0: 아침, 1: 점심, 2: 저녁
    protected int weather; //(임의의 정수로 매핑)
    protected int fieldEffect; //(임의의 정수로 매핑)
    protected int currentEv; //현재 몬스터의 진화 상태

    // 2. 게임 객체 관리
    private Monster currentMonster; // 현재 플레이어가 육성 중인 몬스터
    private Monster[] nest; // 둥지 배열 (기획하신 대로 수용 개수 10마리 고정)
    
    private Scanner input;
    private boolean isRunning; // 게임 루프 제어 변수
    
 // 도감 시스템 전역 변수
    private boolean[] unlockedDict = new boolean[800]; // 100~705 ID 해금 여부 저장

    //도감 마스터 데이터 (종족 이름과 ID 배열)
    private final String[] TRIBE_NAMES = {"드래곤", "정령", "슬라임", "골렘", "로봇", "언데드", "공허생명체"};
    
    private final int[][] DICT_IDS = {
        {100, 101, 102, 103, 104}, // 드래곤
        {200, 201, 202, 203},      // 정령
        {300, 301, 302, 303, 304}, // 슬라임
        {400, 401, 402, 403, 404}, // 골렘
        {500, 501, 502, 503, 504, 505}, // 로봇
        {600, 601, 602, 603, 604}, // 언데드
        {700, 701, 702, 703, 704, 705}  // 공허
    };

    private final String[][] DICT_NAMES = {
        {"엘더드래곤", "원소드래곤", "환상룡", "토룡", "흑염룡"},
        {"미정령", "정령왕", "암흑정령", "원소정령"},
        {"데미갓", "슬라임킹", "슬라임퀸", "원소슬라임", "다크슬라임"},
        {"거대골램", "키네틱 골렘", "가드골렘", "원소골렘", "한철골렘"},
        {"기계신-데우스엑스마키나", "전략병기-위성폭격기", "대마법병기-안티매직", "전투모듈-안드로이드", "정찰모듈-레인저", "화력모듈-워머신"},
        {"사신", "영생자", "좀비", "스켈레톤", "포식자"},
        {"공허여왕", "공허포식자", "공허군주", "공허유랑자", "공허생산자", "공허수확자"}
    };
    
    

    public void init() {
        System.out.println("게임 시스템을 초기화합니다...");
        this.day = 1;
        this.turn = 0; 
        this.weather = 0; 
        this.fieldEffect = 0; 
        this.currentEv = 0; 
        
        this.nest = new Monster[10]; 
        this.currentMonster = new Egg(); 
        this.input = new Scanner(System.in);
        this.isRunning = true;
        this.loadEncyclopediaData(); //도감 로딩
        
    }

    public void run() {
        System.out.println("Grow Game을 시작합니다.");

        // 메인 게임 루프
        while (isRunning) {
        	System.out.println("\n==================================================");
            System.out.println(" ⌨️ [시스템 메뉴] N: 둥지 보기 | E: 도감 보기| F: 데이터 로드 | 0: 게임 종료");
            System.out.println("==================================================");
            if(currentEv != 2) {
            	currentMonster.display(day, turn, weather, fieldEffect); //기본 UI
            }
            else {
            	currentMonster.display();//훈련용 UI 오버로딩.
            }
            
            boolean isActionValided = false; // 턴 진행 여부를 제어하는 변수
            
            do {
                currentMonster.question();
                //문자형 단축키 수용을 위해 문자열로 입력을 선제 수집
                String inputStr = input.next(); 
                
                //둥지 보기 단축키 검사 (대소문자 허용)
                if (inputStr.equalsIgnoreCase("n")) {
                    showNest();
                    continue; // 턴을 소모하지 않고 다시 현재 상황의 입력 창으로 복귀
                }
                
                //도감 보기 단축키 검사 (대소문자 허용)
                if (inputStr.equalsIgnoreCase("e")) {
                    showEncyclopedia();
                    continue; // 턴을 소모하지 않고 복귀
                }
                //파일 불러오기
                if (inputStr.equalsIgnoreCase("f")) {
                    System.out.println("\n==================================================");
                    System.out.println("[ 승천 데이터 호출 ] 불러올 개체의 이름을 입력하십시오.");
                    System.out.print("이름 입력 (취소하려면 '취소' 입력): ");
                    
                    input.nextLine(); // 이전 입력의 버퍼(엔터키) 클리어
                    String loadName = input.nextLine();

                    if (!loadName.equals("취소")) {
                        loadMonster(loadName); // 파일 로드 메서드
                    } else {
                        System.out.println("-> 로딩을 취소하고 돌아갑니다.");
                    }
                    continue; // 턴을 소모하지 않고 메인 화면으로 복귀
                }
                
                //기존 숫자 입력 처리 (훈련 명령어 및 종료)
                try {
                    int choice = Integer.parseInt(inputStr); // 문자열을 정수로 안전하게 파싱
                    
                    if (choice == 0) { // 게임 종료
                        isRunning = false;
                        break; 
                    }
                    
                    int error = currentMonster.state(choice, this.weather);
                    if (error == 0) {
                        advanceTurn(); // 정상 수행 시에만 내부 시계 작동
                        isActionValided = true; // 무한 루프 탈출 조건 충족
                    } else {
                        System.out.println("잘못된 입력입니다. 다시 선택해 주십시오.");
                    }
                    
                } catch (NumberFormatException e) {//예외 핸들링
                    // 숫자가 아닌 엉뚱한 문자열이 들어왔을 때 예외처리.
                    System.out.println("⚠️ 올바른 명령 번호나 단축키(n: 둥지, e: 도감, F: 파일로드)를 입력해 주십시오.");
                }
                
            } while (!isActionValided && isRunning);
        }
        input.close();
    }

    //둥지 보기
    private void showNest() {
        System.out.println("\n==================================================");
        System.out.println("🏠 [ 둥지(Nest) ]");
        System.out.println("==================================================");
        
        boolean isEmpty = true;
        int cnt = 0;
        
        for (int i = 0; i < nest.length; i++) {
            if (nest[i] != null) {
                isEmpty = false;
                cnt++;
                
                Adult adultNest = (Adult) nest[i]; //형 변환
                System.out.print("[" + (i+1) + "번 울타리] 종족: " + nest[i].tribe);
                // 몬스터 스팩 출력.
                System.out.println(" | HP: " + adultNest.getHp() + 
                        " | SPD: " + adultNest.getSpeed() + 
                        " | ATK: " + adultNest.getAttack() + 
                        " | DEF: " + adultNest.getDefense() + 
                        " | MAG: " + adultNest.getMagic());
            }
        }
        
        if (isEmpty) {
            System.out.println("❌ 현재 둥지가 비어 있습니다. 성체를 안착시켜 시스템을 활성화하십시오.");
        } else {
            System.out.println("-> 총 " + cnt + "마리의 성체가 둥지에 안착해있습니다.");
        }
        System.out.println("==================================================\n");
    }

    // 도감 시스템
 //도감 시스템 인터페이스 
    private void showEncyclopedia() {
        System.out.println("\n==================================================");
        System.out.println("[ 몬스터 도감 ]");
        System.out.println("==================================================");

        for (int i = 0; i < TRIBE_NAMES.length; i++) {
            System.out.println("\n===" + TRIBE_NAMES[i] + "===");
            
            for (int j = 0; j < DICT_IDS[i].length; j++) {
                int currentId = DICT_IDS[i][j];
                
                // 해당 ID가 해금(true)되어 있다면 이름을, 아니면 (미확인)을 출력
                if (unlockedDict[currentId]) {
                    System.out.println((j + 1) + ". " + DICT_NAMES[i][j]);
                } else {
                    System.out.println((j + 1) + ". (미확인)");
                }
            }
        }
        System.out.println("==================================================\n");
    }
    
    
    // 턴 진행 및 날짜 변경 로직
    private void advanceTurn() {
    	
    	if (this.currentEv == 2) {
            this.day++; // 성체 훈련 카운트 증가 (1~10)
            int remaining = 10 - this.day; // 남은 훈련 횟수 역산
            
            System.out.println("\n=== [ 집중 훈련 ] 훈련 " + this.day + "일차 ===");
            System.out.println("⏳ 남은 훈련 횟수: " + remaining + " / 10");
            
            // 10번의 훈련이 끝나면 즉시 엔딩
            if (this.day >= 10) {
                System.out.println("\n==================================================");
                System.out.println("✨ 모든 훈련이 종료되었습니다. 엔딩으로 진입합니다...");
                System.out.println("==================================================");
                checkEnding(); // 엔딩 메서드 호출
            }
            
            
            return; 
        }
    
        this.turn++;
        currentMonster.calEv(this.weather, this.fieldEffect);
        if (this.turn > 2) {
            this.turn = 0; // 다시 아침으로
            this.day++;
            currentMonster.state(0,this.weather); // 하루가 지나면 내부 스탯이 조금 변화함.
            System.out.println("\n=== 새로운 하루가 밝았습니다! (현재 " + this.day + "일차) ===");
            
            // 1. 날짜가 바뀔 때마다 날씨/필드 효과 업데이트 (둥지의 영향을 받을 수 있음)
            updateEnvironment();
            
            if(this.day == 15 && this.currentEv == 0) {
            	checkEv1(); // 1차 진화
            	this.currentEv = 1;
            	System.out.println("\n==================================================");
                System.out.println("✨ 축하합니다! 알이 깨어나 [ " + currentMonster.tribe + " ](으)로 진화했습니다! ✨");
                System.out.println("==================================================");
            }
            else if(this.day == 30 && this.currentEv==1) {
            	checkEv2(); //2차 진화
            	this.currentEv = 2;
            	this.day = 0; 
                
                System.out.println("\n==================================================");
                System.out.println("[시스템] 최종 진화 완료!");
                System.out.println("10번의 단독 훈련으로 스탯을 극한으로 끌어올리십시오.");
                System.out.println("==================================================");
            
            }
        }
    }

    
    
 // 환경 변수 업데이트 (둥지 시스템 연계)
    private void updateEnvironment() {
        SecureRandom random = new SecureRandom();
        
        //날씨(Weather) 구현 ---
        ArrayList<Integer> weatherPool = new ArrayList<>();
        
        // 둥지를 순회하며 Adult 규격을 갖춘 개체의 날씨 효과만 추출
        for (int i = 0; i < nest.length; i++) {
            if (nest[i] != null && nest[i] instanceof Adult) {
                int wEffect = ((Adult) nest[i]).WeatherEffect();
                if (wEffect != -1) {
                    weatherPool.add(wEffect); // 유효한 날씨 효과 수집
                }
            }
        }
        
        if (!weatherPool.isEmpty()) {
            // 간섭 있음: 둥지 몬스터들의 효과 중 하나를 무작위로 채택하여 고정
            int selectedIndex = random.nextInt(weatherPool.size());
            this.weather = weatherPool.get(selectedIndex);
        } else {
            // 간섭 없음: 기본 확률에 따라 무작위 결정
            int rand = random.nextInt(100); // 0 ~ 99 사이의 난수 추출
            if (rand < 30) { this.weather = 0; } // 맑음
            else if (rand < 45) { this.weather = 1; } // 흐림
            else if (rand < 60) { this.weather = 2; } // 비
            else if (rand < 69) { this.weather = 3; } // 바람
            else if (rand < 78) { this.weather = 4; } // 폭염
            else if (rand < 85) { this.weather = 5; } // 천둥
            else if (rand < 92) { this.weather = 6; } // 황사
            else if (rand < 99) { this.weather = 7; } // 한파
            else { this.weather = 8; } // 종말
        }
        
        //필드(Field) 구현 ---
        int[] fieldProbs = {90, 2, 2, 2, 2, 2};

        // 둥지를 순회하며 필드 확률 지분 뺏어오기 로직 적용
        for (int i = 0; i < nest.length; i++) {
            if (nest[i] != null && nest[i] instanceof Adult) {
                int boostField = ((Adult) nest[i]).FieldEffect();
                
                // 1~5번 사이의 유효한 필드 번호일 경우 지분 강탈
                if (boostField >= 1 && boostField <= 5) {
                    fieldProbs[0] -= 9;
                    fieldProbs[boostField] += 9;
                }
            }
        }

        // 누적 확률 기반 난수 추출
        int fieldRand = random.nextInt(100); // 0 ~ 99
        int cumulativeProb = 0;

        for (int i = 0; i < fieldProbs.length; i++) {
            cumulativeProb += fieldProbs[i]; // 확률 누적
            if (fieldRand < cumulativeProb) {
                this.fieldEffect = i;
                break;
            }
        }
    }
    
    

    // 날짜 경과에 따른 진화 검사
    private void checkEv1() {
    	//1차 진화
    	int babytype = currentMonster.calEv(this.weather, this.fieldEffect);
    	
        int f = currentMonster.fullness;
        int a = currentMonster.affinity;
        int s = currentMonster.stress;
        
        switch (babytype) {
            case 0:
                this.currentMonster = new BabyDragon(f, a, s);
                break;
            case 1:
                this.currentMonster = new BabySpirit(f, a, s);
                break;
            case 2:
                this.currentMonster = new BabySlime(f, a, s);
                break;
            case 3:
                this.currentMonster = new BabyGolem(f, a, s);
                break;
            case 4:
                this.currentMonster = new BabyRobot(f, a, s);
                break;
            case 5:
                this.currentMonster = new BabyUndead(f, a, s);
                break;
            case 6:
                this.currentMonster = new BabyVoid(f, a, s);
                break;
            default:
                // 예외적인 인덱스가
                System.out.println("[시스템 오류] 존재하지 않는 진화 인덱스입니다. 기본 슬라임으로 대체합니다.");
                this.currentMonster = new BabySlime(f, a, s);
                break;
        }
    	
    }
    private void checkEv2() {
    	//추후 구현. (드디어 완료)
    	
    	// 진화할 성체의 고유 ID 추출 
        // 각 Baby 클래스의 calEv()가 조건에 맞는 100~705 사이의 성체 ID를 반환
        int adultId = currentMonster.getAdultId(this.weather , this.fieldEffect);

        //현재 유년기 개체의 데이터 백업
        int f = currentMonster.fullness;
        int a = currentMonster.affinity;
        int s = currentMonster.stress;


        int hp = currentMonster.getHp();
        int spd = currentMonster.getSpeed();
        int atk = currentMonster.getAttack();
        int def = currentMonster.getDefense();
        int mag = currentMonster.getMagic();

        System.out.println("\n==================================================");

        this.currentMonster = factory(adultId, f, a, s, hp, spd, atk, def, mag);
    }
    
 // 팩토리 메서드: 진화 ID와 스탯을 입력받아 알맞은 성체 객체를 반환 (총 36종)
    private Monster factory(int adultId, int f, int a, int s, int hp, int spd, int atk, int def, int mag) {
        switch (adultId) {
            // --- 드래곤 계통 (100) ---
            case 100: return new ElderDragon(f, a, s, hp, spd, atk, def, mag);
            case 101: return new ElementDragon(f, a, s, hp, spd, atk, def, mag);
            case 102: return new FantasyDragon(f, a, s, hp, spd, atk, def, mag);
            case 103: return new EarthDragon(f, a, s, hp, spd, atk, def, mag);
            case 104: return new BlackFlameDragon(f, a, s, hp, spd, atk, def, mag);

            // --- 정령 계통 (200) ---
            case 200: return new MinorSpirit(f, a, s, hp, spd, atk, def, mag);
            case 201: return new SpiritKing(f, a, s, hp, spd, atk, def, mag);
            case 202: return new DarkSpirit(f, a, s, hp, spd, atk, def, mag);
            case 203: return new ElementalSpirit(f, a, s, hp, spd, atk, def, mag);

            // --- 슬라임 계통 (300) ---
            case 300: return new Demigod(f, a, s, hp, spd, atk, def, mag);
            case 301: return new SlimeKing(f, a, s, hp, spd, atk, def, mag);
            case 302: return new SlimeQueen(f, a, s, hp, spd, atk, def, mag);
            case 303: return new ElementalSlime(f, a, s, hp, spd, atk, def, mag);
            case 304: return new DarkSlime(f, a, s, hp, spd, atk, def, mag);

            // --- 골렘 계통 (400) ---
            case 400: return new GiantGolem(f, a, s, hp, spd, atk, def, mag);
            case 401: return new KineticGolem(f, a, s, hp, spd, atk, def, mag);
            case 402: return new GuardGolem(f, a, s, hp, spd, atk, def, mag);
            case 403: return new ElementalGolem(f, a, s, hp, spd, atk, def, mag);
            case 404: return new ColdIronGolem(f, a, s, hp, spd, atk, def, mag);

            // --- 로봇 계통 (500) ---
            case 500: return new DeusExMachina(f, a, s, hp, spd, atk, def, mag);
            case 501: return new SatelliteBomber(f, a, s, hp, spd, atk, def, mag);
            case 502: return new AntiMagicWeapon(f, a, s, hp, spd, atk, def, mag);
            case 503: return new AndroidModule(f, a, s, hp, spd, atk, def, mag);
            case 504: return new RangerModule(f, a, s, hp, spd, atk, def, mag);
            case 505: return new WarMachine(f, a, s, hp, spd, atk, def, mag);

            // --- 언데드 계통 (600) ---
            case 600: return new GrimReaper(f, a, s, hp, spd, atk, def, mag);
            case 601: return new Immortal(f, a, s, hp, spd, atk, def, mag);
            case 602: return new Zombie(f, a, s, hp, spd, atk, def, mag);
            case 603: return new Skeleton(f, a, s, hp, spd, atk, def, mag);
            case 604: return new Predator(f, a, s, hp, spd, atk, def, mag);

            // --- 공허 계통 (700) ---
            case 700: return new VoidQueen(f, a, s, hp, spd, atk, def, mag);
            case 701: return new VoidPredator(f, a, s, hp, spd, atk, def, mag);
            case 702: return new VoidLord(f, a, s, hp, spd, atk, def, mag);
            case 703: return new VoidWanderer(f, a, s, hp, spd, atk, def, mag);
            case 704: return new VoidProducer(f, a, s, hp, spd, atk, def, mag);
            case 705: return new VoidHarvester(f, a, s, hp, spd, atk, def, mag);

            default:
                System.out.println("🚨 [시스템 경고] 알 수 없는 진화 조건(" + adultId + ") 감지.");
                System.out.println("✨ 불안정한 에너지가 뭉쳐 [ 원소슬라임 ]으로 강제 변이되었습니다!");
                return new ElementalSlime(f, a, s, hp, spd, atk, def, mag);
        }
    }

    
    
    
    //둥지 포화 상태 검증 로직
    // Adult 인터페이스의 qe() 메서드에 넘겨줄 boolean 값을 산출
    private boolean isNestFull() {
        for (int i = 0; i < nest.length; i++) {
            if (nest[i] == null) {
                return false;
            }
        }
        return true; // 10칸이 모두 꽉 찼음
    }

    //둥지 배열에 성체 객체 삽입 로직
    private void sendToNest() {
        // 안전장치: 꽉 찬 상태에서 호출될 경우 강제 종료 처리
        if (isNestFull()) {
            System.out.println("[시스템] 둥지 용량이 초과되어 삽입할 수 없습니다. 개체를 강제 승천 시킵니다.");
            return;
        }

        // 배열을 순회하며 가장 빠른 빈자리(null)에 현재 성체 객체 할당
        for (int i = 0; i < nest.length; i++) {
            if (nest[i] == null) {
                nest[i] = currentMonster;
                System.out.println("[알림] " + currentMonster.tribe + "이(가) 둥지의 [" + (i+1) + "]번 구역에 안착했습니다.");
                //내일부터는 이 개체는 둥지 안에 있음.
                break;
            }
        }
    }
    
    
    private void checkEnding() {
    	
    	int finalAdultId = currentMonster.getAdultId();
        unlockAndSave(finalAdultId); // 도감 저장
        
        Adult adultRef = (Adult) currentMonster;
        boolean nestStatus = isNestFull();
        
        
        // 엔딩 UI 출력 및 입력 수신
        adultRef.qe(nestStatus);
        int choice = input.nextInt();

        // 엔딩 연출 텍스트 실행
        adultRef.de(choice, nestStatus);

        // 실제 둥지 저장 혹은 승천 처리
        if (!nestStatus && choice == 2) {
            sendToNest(); 
        } else {
            // 승천 분기 
        	ascendMonster(adultRef);
        }
        
        // 리겜
        startNewLoop();
    }
    
    private void startNewLoop() {
        System.out.println("\n==================================================");
        System.out.println("🔄 [ 시스템 ] 세대교체가 완료되었습니다.");
        System.out.println("✨ 이전 세대의 업적을 품은 채, 새로운 알이 부화장에 안착했습니다. ✨");
        System.out.println("==================================================\n");

        // 팩트: 둥지(nest) 배열은 절대 건드리지 않고 그대로 유지하여 차세대 날씨/필드 확률에 누적 적용시킵니다.
        this.day = 1;
        this.turn = 0;
        this.weather = 0;
        this.fieldEffect = 0;
        this.currentEv = 0;
        
        // 다시 1일 차 아침의 알 객체로 리포지셔닝
        this.currentMonster = new Egg(); 
    }
    
    
 // 몬스터 승천 기능 구현 (파일 저장 기능)
    private void ascendMonster(Adult adultRef) {
        System.out.println("\n==================================================");
        System.out.println("✨ [ 승천 ] 개체의 이름을 명명해 주십시오.");
        System.out.print("이름 입력: ");
        
        input.nextLine(); // 입력 버퍼 클리어
        String monsterName = input.nextLine();

        try {
            // 폴더가 있는지 찾기
            Path dirPath = Path.of("Heaven");
            if (Files.notExists(dirPath)) {
                Files.createDirectories(dirPath); 
            }

            // 최종 파일 경로 설정 (Heaven/몬스터이름.txt)
            Path filePath = Path.of("Heaven", monsterName + ".txt");
            
            // 인터페이스에서 str형태의 정보 추출
            String infoData = adultRef.makeinfo();
            
            //단일 라인 파일 쓰기
            Files.writeString(filePath, infoData);
            
            System.out.println("\n💾 [시스템] \"" + monsterName + ".txt\" 파일이 Heaven 폴더에 성공적으로 저장되었습니다.");
            
        } catch (IOException e) {
            System.out.println("❌ [시스템 오류] 승천 데이터 파일 저장 중 입출력 예외가 발생했습니다.");
            e.printStackTrace();
        }
    }
    
 // 파일 로드하기 및 둥지 안착 로직
    private void loadMonster(String monsterName) {
        
        // 둥지 포화 상태 검사
        if (isNestFull()) {
            System.out.println("[시스템] 현재 둥지가 가득 차 있어 새로운 개체를 수용할 수 없습니다.");
            return; // 둥지가 꽉 찼으면 파일 I/O 작업 자체를 시작하지 않고 메서드를 즉시 종료.
        }

        Path filePath = Path.of("Heaven", monsterName + ".txt");

        // 파일 존재 유무 검증
        if (Files.notExists(filePath)) {
            System.out.println("[시스템] 개체를 찾을 수 없습니다.");
            return;
        }

        // Scanner에 Path 객체를 전달하여 파일 읽기 (try-with-resources로 안전하게 닫기)
        try (Scanner fileInput = new Scanner(filePath)) {
            
            // 스탯 순차적 파싱 
            int adultId = fileInput.nextInt();
            int hp = fileInput.nextInt();
            int speed = fileInput.nextInt();
            int attack = fileInput.nextInt();
            int defense = fileInput.nextInt();
            int magic = fileInput.nextInt();
            
            // 보안 코드(SCode) 추출
            String codeLine = fileInput.next(); // "CODE:123" 형태의 문자열 추출
            String[] splitCode = codeLine.split(":");
            int loadedSCode = Integer.parseInt(splitCode[1]); //보안 코드 int형으로 추출 완료.

            // 무결성 검증 (해시 역산)
            long hash = (adultId * 3L) + (hp * 5L) + (speed * 7L) + (attack * 11L) + (defense * 13L) + (magic * 17L);
            int validSCode = (int) (hash % 997);

            if (loadedSCode == validSCode) {
                System.out.println("✅ [시스템] 올바른 개체입니다. 데이터를 정상적으로 불러옵니다.");
                
                // TODO: adultId를 기반으로 switch문을 돌려 해당 성체 객체를 생성하고,
                // sendToNest() 등의 메서드를 호출하여 둥지(nest) 배열의 빈칸에 삽입하는 로직 전개
                
                Monster loadedMonster = factory(adultId, 0, 0, 0, hp, speed, attack, defense, magic);
                
                //둥지(nest) 배열을 순회하여 null인 빈칸에 객체를 넣기
                boolean isSettled = false;
                for (int i = 0; i < nest.length; i++) {
                    if (nest[i] == null) {
                        nest[i] = loadedMonster;
                        isSettled = true;
                        
                        //다운캐스팅을 통해 성체 고유의 둥지 안착 효과(nestprint)를 발동
                        if (loadedMonster instanceof Adult) {
                            ((Adult) loadedMonster).nestprint();
                        }
                        
                        System.out.println("-> 🏠 [안착 완료] \"" + monsterName + "\" 개체가 둥지 [" + (i+1) + "번 울타리]에 안전하게 등록되었습니다.\n");
                        break; // 중복 안착을 방지 (if문 안쪽으로 이동)
                    }
                } // for문 종료
                
            } else {
                System.out.println("❌ [시스템 오류] 파일이 위변조되었습니다. 로딩을 거부합니다.");
            }
            
        } catch (Exception e) {
            System.out.println("❌ [시스템 오류] 파일 읽기 중 치명적 예외가 발생했습니다. 파일 형식이 훼손되었을 수 있습니다.");
            e.printStackTrace(); // 어떤 에러인지 정확히 추적하기 위한 로그
        }
    }

    
    
    
 // 도감 데이터 로드
    private void loadEncyclopediaData() {
        try {
            Path path = Path.of("encyclopedia.txt");
            if (Files.exists(path)) {
                Scanner sc = new Scanner(path);
                while (sc.hasNextInt()) {
                    int id = sc.nextInt();
                    if(id >= 0 && id < 800) unlockedDict[id] = true;
                }
                sc.close();
            }
        } catch (Exception e) {
            System.out.println("[시스템] 도감 데이터를 불러오는 중 오류가 발생했습니다.");
        }
    }

    // 도감 데이터 갱신 및 저장
    private void unlockAndSave(int adultId) {
        if (adultId >= 0 && adultId < 800 && !unlockedDict[adultId]) {
            unlockedDict[adultId] = true; // 도감 뜷기
            try {
            	//파일에다가 도감 진척상황 기록
                Files.writeString(Path.of("encyclopedia.txt"), adultId + "\n", 
                                  StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } catch (Exception e) {
                System.out.println("🚨 [시스템] 도감 기록 저장 실패.");
            }
        }
    }
}