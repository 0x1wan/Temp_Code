# 자바 기말 과제 보고서 (javafx로 Todo리스트 구현하기)
---
제출자: 2022113960 최이완

---



## 1. 개요
'Tododo'는 사용자의 일정 및 작업 관리를 돕기 위해 JavaFX를 기반으로 제작된 데스크톱 애플리케이션입니다. 이 프로그램은 할 일의 추가 및 삭제와 같은 기본 기능에 더해, 특정 작업에 대한 강조 표시, 완료 여부 및 생성 시간에 따른 3단 자동 정렬, 그리고 텍스트 파일(.txt) 입출력을 통한 데이터 영속성 유지 기능을 제공합니다. 이를 통해 사용자는 직관적인 UI 환경에서 자신의 할 일을 효율적으로 추적하고 관리할 수 있습니다.

본 프로젝트의 핵심 제작 동기는 과거 개인적인 일정 관리를 위해 파이썬(Python)으로 자체 개발하여 사용하던 Todo 프로그램을, 객체지향 프로그래밍 언어인 자바(Java) 환경에서 전면 재설계 및 구현해 보는 것에 있습니다.

* **Source Code (python.ver):** https://github.com/0x1wan/Tododo


---
## 2. 기능 설명

### 2.1. 클릭으로 할 일 체크 구현
사용자가 리스트에 등록된 할 일을 마우스로 클릭하면, 해당 항목에 취소선이 그어지고 글자색이 흐린 회색으로 변하며 직관적으로 '완료' 처리됨을 보여줍니다. 완료된 항목을 다시 클릭하면 원래의 미완료 상태로 복구됩니다.


(핵심 코드 참조: TodoMouseEventHandler 내부의 selectedItem.setChecked(!selectedItem.isChecked()); 상태 토글 로직 )

### 2.2. 강조 기능

중요한 일정을 눈에 띄게 관리할 수 있는 하이라이트 기능입니다. 목록에서 특정 항목을 선택한 뒤 하단의 '강조' 버튼을 누르면 해당 항목의 글씨가 노란색으로 변하며 중요 표시가 됩니다. 만약 목록을 선택하지 않고 하단 입력창에 텍스트만 적은 상태에서 버튼을 누르면, 텍스트의 맨 앞에 [강조] 라는 태그가 자동으로 붙거나 떨어지는 방식으로 작동합니다.


(핵심 코드 참조: Main 클래스의 btnImportant.setOnAction 이벤트 핸들러 로직 )

### 2.3. 뚜렷한 정렬 기준 (강조, 시간)

새로운 할 일이 추가되거나, 기존 할 일의 상태(체크, 강조)가 변할 때마다 목록이 즉각적으로 자동 정렬됩니다. 정렬의 1순위 기준은 '완료 여부'로, 완료된 항목은 시야에서 벗어나도록 목록 맨 아래로 내려갑니다. 2순위 기준은 '강조 여부'로, 노란색으로 강조된 중요한 할 일이 맨 위로 올라옵니다. 상태가 같은 항목들끼리는 생성된 시간 순서대로 배치되어 직관적인 우선순위 파악을 돕습니다.


(핵심 코드 참조: TodoManager 클래스의 sortTodos() 메서드 내 람다(Lambda) 다중 조건 정렬 로직 )


### 2.4. 전체 삭제 기능

누적된 할 일 데이터가 너무 많거나 새로운 계획을 세워야 할 때, 하단의 '전체 삭제' 버튼을 클릭하여 화면의 목록과 저장된 텍스트 파일 데이터베이스를 단번에 초기화하는 기능입니다.


(핵심 코드 참조: TodoManager 클래스의 clearAllTodos() 메서드 및 Main의 버튼 연결부 )

### 2.5. 다중 선택 및 일괄 삭제 (Shift & Ctrl)

데스크톱 운영체제(OS)의 기본 폴더 조작 방식과 동일한 사용자 경험(UX)을 제공합니다. 사용자는 Shift 키를 누른 채 클릭하여 연속된 범위의 항목들을 한 번에 선택하거나, Ctrl 키를 누른 채 클릭하여 띄엄띄엄 여러 항목을 동시에 선택할 수 있습니다. 이렇게 다중 선택된 항목들은 키보드의 Delete 키를 누르면 일괄적으로 삭제되어 관리의 편의성을 극대화합니다.


(핵심 코드 참조: Main 클래스의 listView.setOnKeyPressed 내부 KeyCode.DELETE 감지 로직 및 TodoMouseEventHandler의 다중 선택 로직 )

---

## 3. 코드 설명

핵심 코드들에 대한 세부적인 설명입니다.

---

### 3.1. Main 클래스

main 클래스는 본 프로그램의 화면을 구성하는 중추입니다. JavaFX의 Application 클래스를 상속받아 전체적인 화면 레이아웃(BorderPane, HBox 등)과 뷰(ListView, TextField 등)를 조립합니다. 또한, 데이터를 관리하는 매니저 객체와 입력을 감지하는 이벤트 핸들러 객체들을 생성하고 서로 연결해 줌으로써, 사용자 인터페이스와 내부 비즈니스 로직을 잇는 역할을 수행합니다


---

#### 3.1.1. 이벤트 핸들러 클래스 분리

GUI 개발 시 화면 구성을 담당하는 Main 클래스 안에 마우스나 키보드의 동작을 제어하는 로직까지 모두 작성하면 코드가 비대해지고 유지보수가 극도로 어려워집니다. 이를 해결하기 위해 수업시간에 배운 내용을 적용하여 이벤트 처리 로직을 외부 클래스로 완전히 분리했습니다.

아래는 Main 클래스 내부에서 분리된 핸들러 객체를 생성하고 연결하는 핵심 코드입니다.

```java
// Main.java 내의 이벤트 핸들러 연결부 발췌
// 1. 마우스 이벤트 핸들러 연결
TodoMouseEventHandler mouseHandler = new TodoMouseEventHandler(todoManager, listView);
listView.setOnMouseClicked(mouseHandler);

// 2. 키보드 이벤트 핸들러 연결
TodoKeyEventHandler keyHandler = new TodoKeyEventHandler(todoManager, inputField);
inputField.setOnKeyPressed(keyHandler);
```

[작동 원리]

객체 생성 및 권한 부여:
새로운 이벤트 핸들러 객체를 만들 때, 괄호 안에 todoManager와 화면 요소(listView, inputField)를 넘겨줍니다. 이로써 외부 핸들러 클래스에서도 메인 화면의 데이터와 UI를 조작할 수 있는 권한을 가지게 됩니다.

동작 위임:
setOnMouseClicked 등의 메서드를 통해 화면 요소들에게 "앞으로 입력 이벤트가 발생하면 방금 연결한 Handler 객체에게 위임해"라고 지시합니다.

결과:
Main 클래스는 오직 화면 배치에만 집중할 수 있고, 입력 시 발생하는 구체적인 동작은 핸들러 클래스가 전담하게 되어 유지보수가 훨씬 쉬워집니다.


#### 3.1.2. ListView 기능 활용


JavaFX의 ListView는 여러 개의 데이터 항목을 세로 스크롤이 가능한 목록 형태로 화면에 출력해 주는 기본 컴포넌트입니다. 본 프로젝트에서 할 일 목록을 렌더링하기 위해 이 컴포넌트를 차용한 이유는 다음과 같습니다.

[차용 목적 및 원리]

데이터 자동 동기화: ListView는 내부적으로 ObservableList와 연결되도록 설계되어 있습니다. 사용자가 할 일을 추가하거나 매니저 클래스가 데이터를 정렬할 때마다 개발자가 화면을 일일이 새로 고칠 필요 없이, 데이터의 변경 사항이 즉각 화면에 반영되는 편리한 동기화 환경을 제공합니다.

메모리 최적화 (셀 재사용): 데이터가 수천 개 누적되더라도 화면에 보이는 개수만큼의 UI 블록(Cell)만 메모리에 생성합니다. 사용자가 스크롤을 내리면 화면 위로 사라진 블록을 아래로 가져와 새로운 데이터를 입혀 재사용하므로, 시스템 리소스 낭비를 원천적으로 차단합니다.

OS 표준 다중 선택 모델 지원: 자체적인 SelectionModel을 내장하고 있어, 단 한 줄의 설정만으로 윈도우와 맥 OS 표준의 다중 선택 기능(Shift 연속 선택, Ctrl 개별 선택)을 안정적으로 구현할 수 있습니다.

아래는 메인 클래스에서 이러한 ListView를 생성하고 초기 설정하는 핵심 코드입니다.

```java
// Main.java 내의 ListView 생성 및 설정부 발췌
// 1. 매니저가 관리하는 데이터 리스트와 묶어서 ListView 객체 생성
ListView listView = new ListView<>(todoManager.getTodoList());

// 2. 다중 선택 모드 활성화 (Shift, Ctrl 지원)
listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

// 3. 데이터를 화면에 예쁘게 그리기 위한 커스텀 디자이너(셀 팩토리) 연결
listView.setCellFactory(new TodoCellFactory());
```


---
### 3.2. TodoManager 클래스

TodoManager 클래스는 할 일 데이터의 추가, 삭제, 정렬, 그리고 파일 저장 및 불러오기를 총괄하는 시스템입니다. UI 요소에 직접적으로 관여하지 않고 순수한 데이터 처리 로직과 텍스트 파일 입출력(I/O)만을 전담하며, 내부 데이터의 상태가 변경될 때마다 연결된 리스트뷰에 변경 사항을 자동으로 전파하여, 데이터의 무결성과 화면의 동기화를 책임집니다.

---

#### 3.2.1. ObservableList 사용

일반적인 자바 배열(ArrayList) 대신 JavaFX 전용 컬렉션인 ObservableList를 사용하여 할 일 데이터를 관리하도록 설계했습니다. 이를 통해 데이터가 변경될 때마다 화면이 알아서 반응하는 환경을 구축했습니다.

아래는 매니저 클래스 내부에서 해당 리스트를 선언하고 초기화하는 원본 코드입니다.

```java
// TodoManager.java 내의 ObservableList 선언 및 초기화 원본 발췌

public class TodoManager {
// JavaFX의 ListView와 자동으로 동기화되는 리스트입니다.
private ObservableList todoList;

public TodoManager() {//생성자
    todoList = FXCollections.observableArrayList();
    loadTodos();
// 프로그램 시작 시 텍스트 파일에서 데이터를 불러옵니다.
}

public ObservableList<TodoItem> getTodoList() {
    return todoList;
}
```

[작동 원리]

관찰 가능한 리스트 선언: ObservableList는 일반 리스트와 달리 내부에 있는 데이터가 추가, 삭제, 수정되는 이벤트를 실시간으로 관찰할 수 있는 특수한 자료구조입니다.

UI 자동 동기화: 앞서 Main 클래스에서 이 todoList를 ListView 화면에 묶어두었습니다(new ListView<>(todoManager.getTodoList())). 따라서 사용자가 새로운 할 일을 추가하거나 삭제하여 이 리스트의 내용이 바뀌면, 개발자가 화면을 새로고침하라는 명령을 내리지 않아도 ListView가 변화를 감지하고 즉각적으로 화면을 다시 그립니다.

결과:
데이터와 화면 간의 연결이 자동화되어 코드가 훨씬 간결해집니다.


#### 3.2.2. 정렬 로직 (논리 연산)

할 일 목록의 가독성을 극대화하기 위해, 자바의 내부 정렬 알고리즘에 람다식을 활용하여 직접 만든 정렬 기준을 주입했습니다.

아래는 매니저 클래스 내부의 정렬 로직 원본 코드입니다.

```java
// TodoManager.java 내의 정렬 로직 원본 발췌
// 정렬 논리 구현 (1순위 기준 - 체크 여부 / 2순위 기준 - 강조 여부/ 3순위 기준 )
public void sortTodos() {
List list = new ArrayList<>(todoList);
list.sort((a, b) -> {//람다 구현방식으로 sort방식을 내가 정의한 방식으로 기준 제공 (그럼 자바가 알아서 정렬)
if (a.isChecked() != b.isChecked()) {
return a.isChecked() ? 1 : -1;
}
if (a.isImportant() != b.isImportant()) {
return a.isImportant() ? -1
: 1;
}
return Double.compare(a.getOrderId(), b.getOrderId());
});
todoList.setAll(list);
saveTodos(); // 정렬된 최종 상태를 텍스트 파일에 동기화
}
```

[작동 원리]

독립된 환경에서 정렬 수행: 화면과 실시간으로 연결된 todoList를 직접 정렬하면 과정 내내 화면이 깜빡거리며 성능이 저하됩니다. 따라서 데이터를 일반 ArrayList로 잠시 복사하여 정렬을 마친 뒤, setAll() 메서드로 한 번에 덮어씌워 UI 렌더링 횟수를 1회로 최적화했습니다.

1순위 (완료 여부): 삼항 연산자를 사용하여 완료 상태(isChecked())가 서로 다를 경우, 완료된 항목(true)을 무조건 리스트의 하단(1)으로 보냅니다.

2순위 (강조 여부): 1순위 조건이 같을 때(둘 다 완료거나 둘 다 미완료), 강조 상태(isImportant())가 참인 항목을 리스트의 상단(-1)으로 끌어올립니다.

3순위 (생성 시간): 앞선 두 조건이 모두 동일한 항목끼리는 객체 생성 시 부여받은 고유 시간값(orderId)을 비교하여, 먼저 생성된 순서대로 오름차순 배치합니다.


#### 3.2.3. .txt 입출력(I/O) 구현

프로그램을 종료하더라도 사용자의 할 일 데이터가 영구적으로 보존될 수 있도록, 순수 자바 I/O 기능을 활용하여 텍스트 파일(todo_data.txt)과 데이터를 주고받는 로직을 구현했습니다.

아래는 매니저 클래스의 객체 데이터를 텍스트로 변환하여 저장하고, 텍스트를 다시 객체로 복원하는 핵심 코드입니다.


```java
// TodoManager.java 내의 데이터 저장(직렬화) 발췌
// 문자열 예시: "할일::false::true::16900000.0"
String line = item.getText() + DELIMITER +
item.isChecked() + DELIMITER +
item.isImportant() + DELIMITER +
item.getOrderId();
bw.write(line);
bw.newLine();

// TodoManager.java 내의 데이터 불러오기(파싱) 발췌
while ((line = br.readLine()) != null) {//빈줄 나올때 까지
String[] parts = line.split(DELIMITER);//구분자 기준으로 4칸짜리 배열에 담기

            // 구분자로 나누었을 때 정확히 4개의 속성이 나오는지 체크
            if (parts.length == 4) {
                String text = parts[0];
                boolean isChecked = Boolean.parseBoolean(parts[1]);
                boolean isImportant = Boolean.parseBoolean(parts[2]);
                double orderId = Double.parseDouble(parts[3]);

```
[작동 원리]

데이터 저장: 메모리에 입체적으로 존재하는 TodoItem 객체의 속성들(내용, 완료 여부, 강조 여부, 생성 시간)을 ::라는 명확한 구분자(Delimiter - final로 정의함)를 사이에 끼워 넣어 하나의 긴 평면적인 String으로 이어 붙입니다. 그 후 텍스트 파일에 한 줄씩 기록합니다.

데이터 파싱: 프로그램이 시작될 때 텍스트 파일에서 문자열을 한 줄씩 읽어옵니다. split("::") 메서드를 사용해 문자열을 다시 4개의 토막으로 자른 뒤, Boolean.parseBoolean과 Double.parseDouble을 통해 단순한 텍스트를 실제 논리값과 실수값으로 형변환하여 객체를 원상 복구합니다.

방어 로직: 파일이 손상되어 구분자로 잘랐을 때 4조각이 나오지 않는 비정상적인 줄은 if (parts.length == 4) 조건문으로 걸러내어 시스템 에러를 방지합니다.


#### 3.2.4. try-with-resources 문법 사용

파일 입출력(I/O) 과정에서 발생할 수 있는 치명적인 버그인 '메모리 누수'를 원천 차단하기 위해 자바 7부터 도입된 핵심 안전장치인 try-with-resources 문법을 적극 적용했습니다.

아래는 매니저 클래스의 파일 저장 및 불러오기 메서드에 적용된 해당 문법의 원본 코드입니다.

```java
// TodoManager.java 내의 try-with-resources 원본 발췌
private void saveTodos() {
// try-with-resources 구문을 사용하여 메모리 누수를 방지
try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
for (TodoItem item : todoList) {//for-each문

// (중략)

private void loadTodos() {
    File file = new File(FILE_NAME);
    if (!file.exists()) {
        return; // 파일이 없으면 불러오기를 생략합니다.
    }

    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
        String line;

```

[작동 원리 및 상세 설명]

기존 입출력 방식의 치명적 한계:
과거의 자바에서는 파일을 열어 데이터를 읽고 쓴 뒤, 에러 발생 여부와 상관없이 무조건 수동으로 .close() 메서드를 호출해 파일을 읽는 Scanner를 닫아야 했습니다. 만약 개발자의 실수로 이 .close() 호출이 누락되면, 해당 파일은 프로그램이 끝날 때까지 다른 프로그램이나 운영체제가 해당 파일을 수정하거나 지우지 못하는 상태에 빠지게 됩니다.

자동 자원 해제:
이러한 오류를 시스템 레벨에서 방지하기 위해 나온 것이 try-with-resources입니다. try 키워드 바로 뒤에 소괄호 ()를 열고 그 안에서 파일 입출력 객체(BufferedWriter 등)를 생성합니다. 이렇게 하면, try 블록 내부의 작업이 정상적으로 끝났을 때뿐만 아니라 중간에 에러(catch)가 터져서 강제로 중단되었을 때도, 자바 가상 머신(JVM)이 알아서 안전하게 .close()를 호출해 줍니다.

시스템 최적화 결과:
개발자가 자원 해제 코드를 일일이 작성할 필요가 없어 코드가 훨씬 간결해지며, 런타임 환경에서 발생할 수 있는 치명적인 리소스 누수를 방지할 수 있는 구조입니다.


---

### 3.3. TodoItem 클래스

TodoItem 클래스는 할 일 데이터 하나가 가져야 할 속성들을 정의한 데이터  객체입니다. 이 클래스는 스스로 복잡한 연산을 수행하기보다는, 하나의 할 일 항목이 가지는 '내용', '완료 여부', '중요도', '생성 시간'이라는 4가지 상태 값을 안전하게 보관하고 외부의 요청에 따라 값을 내어주거나 수정하는 수동적인 데이터 역할을 수행합니다.

---

#### 3.3.1. 자료구조 정의

객체지향의 핵심 원칙인 '캡슐화'를 적용하여, 외부 클래스가 할 일 데이터에 함부로 접근하여 값을 훼손하지 못하도록 모든 필드를 은닉했습니다.

아래는 TodoItem 클래스의 캡슐화 로직이 담긴 원본 코드입니다.

```java
// TodoItem.java 원본 발췌
package application;

public class TodoItem {
private String text;
private boolean isChecked;
private boolean isImportant;
private double orderId;

public TodoItem(String text, double orderId) {//생성자 (객체 디폴트값) 텍스트랑 ID만 파라미터로 받아옴
    this.text = text;
    this.isChecked = false;
    this.isImportant = text.contains("[강조]");//해당 키워드의 포함 여부의 boolean값
    this.orderId = orderId;//현재 시각
}

// Getter 및 Setter 메서드들
public String getText() { return text;
}
public boolean isChecked() { return isChecked;
}
public void setChecked(boolean checked) { this.isChecked = checked;
}
public boolean isImportant() { return isImportant;
}
public void setImportant(boolean important) { this.isImportant = important;
}
public double getOrderId() { return orderId; }
}
```

[작동 원리]

필드 은닉: 데이터의 실체인 문자열(text), 체크 상태(isChecked), 강조 상태(isImportant), 생성 시각 고유ID(orderId)를 모두 private으로 묶어 외부 클래스의 직접적인 변수 접근을 완벽히 차단했습니다.

생성자: 객체가 처음 생성될 때, 매개변수로 들어온 텍스트 문자열에 [강조]라는 키워드가 포함되어 있는지 contains() 메서드로 팩트 체크를 수행합니다. 그 결과에 따라 isImportant의 초기값을 자동 세팅하여 외부 클래스(매니저)의 연산 부담을 줄였습니다.

Getter/Setter: 은닉된 데이터에 접근할 수 있는 유일한 공식 통로로 public 메서드들을 열어두었습니다. 이를 통해 데이터의 읽기와 쓰기가 허용한 규격 내에서만 이루어집니다.

---

### 3.4. TodoCellFactory 클래스

TodoCellFactory 클래스는 눈에 보이지 않는 데이터(TodoItem)를 화면에 보이는 디자인 블록(ListCell)으로 변환해 주는 시각화 역할을 수행합니다. JavaFX 프레임워크가 리스트뷰의 빈칸을 채울 때마다 이 클래스에 작업을 의뢰하며, 이 클래스는 데이터의 상태(체크 여부, 강조 여부)를 분석하여 알맞은 색상과 스타일이 입혀진 맞춤형 셀을 생산해 화면에 공급합니다.

---

#### 3.4.1. Callback 인터페이스 및 익명 클래스

JavaFX 리스트뷰의 기본 셀 디자인을 덮어쓰기 위해, 입력을 받아 출력을 돌려주는 Callback 인터페이스가 있습니다.

아래는 콜백 인터페이스 implement와 익명 클래스를 통한 셀 객체 생성 원본 코드입니다.

```java
// TodoCellFactory.java 내 Callback 및 익명 클래스 발췌

public class TodoCellFactory implements Callback<ListView, ListCell> {
//callback 인터페이스: 입출력을 제네릭으로 정의해서 입력된 값을 포장해서 출력해줌.
@Override
public ListCell call(ListView param) {
//call 함수가 ListCell을 던져줄거임.
//param은 인터페이스에서 구현하라고 했는데 실제론 안 사용되는 매개변수
return new ListCell() {//익명 클래스
```

[작동 원리]

Callback 인터페이스: <입력, 출력> 형태의 제네릭 구조를 가집니다. ListView를 입력받아 그 안에 들어갈 ListCell을 출력(반환)하겠다는 규격을 시스템에 약속합니다.

익명 클래스 (Anonymous Class): 수업시간에 배웠듯이 call 메서드 내부에서 new ListCell<TodoItem>() { ... } 형태로 이름 없는 일회용 자식 클래스를 즉시 생성하여 반환합니다.


#### 3.4.2. setStyle 메서드와 CSS 문법

데이터의 논리적 상태를 시각적으로 표현하기 위해, 웹 표준 디자인 문법인 CSS를 자바 코드 내부에서 직접 주입했습니다.

아래는 상태별로 CSS 문자열을 조립하여 적용하는 원본 코드입니다.

```java
// TodoCellFactory.java 내 CSS 스타일링 발췌
// 스타일을 동적으로 적용하는 유틸리티 메서드
private void updateStyles(TodoItem item, boolean isSelected) {
if (item == null) {//아이템이 비워졌을 때, 투명으로 바꾸기.
setStyle("-fx-background-color: transparent;");
return;
}

            // 선택 여부에 따라 배경색을 결정 (선택되면 #3d3d3d 적용)
            String bgStyle = isSelected ?
            "-fx-background-color: #3d3d3d;" : "-fx-background-color: transparent;";
            
            if (item.isChecked()) {
                setStyle(bgStyle + " -fx-text-fill: #666666; -fx-strikethrough: true;");
            } else if (item.isImportant()) {
                setStyle(bgStyle + " -fx-text-fill: #FFD700; -fx-strikethrough: false;");
            } else {
                setStyle(bgStyle + " -fx-text-fill: #ffffff; -fx-strikethrough: false;");
            }
        }

```
[작동 원리]

상태 판별: 셀에 들어온 item 객체가 현재 체크되었는지(isChecked()), 강조되었는지(isImportant()), 그리고 사용자가 마우스로 선택했는지(isSelected)를 조건문으로 판별합니다.

CSS 조립 및 주입: 조건에 따라 삼항 연산자를 활용하여 배경색 문자열을 먼저 정하고, 글자색(-fx-text-fill)과 취소선(-fx-strikethrough) CSS 문자열을 덧붙여 완성합니다. 이 최종 문자열을 setStyle() 메서드에 던져주면 자바FX 엔진이 이를 해석하여 화면 픽셀의 디자인을 동적으로 변경합니다.


#### 3.4.3. Cell 리사이클링 제어


데이터가 많아졌을 때 메모리가 터지는 것을 막기 위해 작동하는 '셀 재사용' 메커니즘을 통제하는 매우 중요한 방어 로직입니다.

아래는 셀이 화면에 나타나거나 재사용될 때 호출되는 updateItem 원본 코드입니다.


```java
// TodoCellFactory.java 내 updateItem 발췌
@Override //한번에 로딩되는 cell갯수를 제한하고 스크롤을 내릴때 마다 안보이는 cell을 비워서 재사용로직이 이미 기본적으로 돌아감
//따라서 거기에 맞춰서 해당 cell을 어떻게 재사용 할지 컨트롤하기 위한 오버라이드
protected void updateItem(TodoItem item, boolean empty) {//스크롤 될 때나 데이터가 변하면 그때마다 알아서 호출

            super.updateItem(item, empty);//일단 원본 클래스가 처리하던 일 그대로 하기. (오류 방지)
            if (empty || item == null) {//그 자리가 비어있고, 동시에 item이 널값이면 초기화
                setText(null);
                updateStyles(null, false);//위에서 강조나 취소 같은걸 적용한 cell이 내려왔을때 UI초기화 해주기
            } else {
                setText(item.getText());//정상적인 재사용 텍스트 박아 넣기
                updateStyles(item, isSelected());//나머지 스타일 추가
            }
      
        }

```

[작동 원리]

콜백 시점: 사용자가 스크롤을 내려서 화면 밖으로 사라진 셀(UI 껍데기)이 다시 화면 밑에서 나타날 때, 프레임워크는 이 껍데기에 새로운 데이터를 담아 updateItem()을 자동으로 호출합니다.

시각적 잔재 초기화 (초기화 방어): 스크롤 되어 내려온 빈 셀은 과거에 취소선이 그어져 있었거나 배경색이 칠해져 있던 '헌 껍데기'일 수 있습니다. 따라서 조건문을 통해 셀이 비어있다면(empty || item == null), 반드시 setText(null)과 투명 배경 적용(updateStyles(null, false))을 수행하여 과거의 낡은 UI 흔적을 깨끗하게 지우고 초기화해야 합니다. 이 로직이 없으면 스크롤을 내릴 때 화면 그래픽이 심각하게 깨지게 됩니다.


---

### 3.5. TodoMouseEventHandler 클래스

TodoMouseEventHandler는 리스트뷰에서 발생하는 사용자의 마우스 클릭 이벤트를 전담하여 처리하는 handler 입니다. 마우스 클릭이라는 물리적 자극을 감지하고, 그에 맞춰 할 일 데이터의 완료 상태를 업데이트하거나, 다중 선택과 같은 OS 기본 기능과 코드가 충돌하지 않도록 이벤트를 조율하는 핵심적인 방어 역할을 수행합니다.

---

#### 3.5.1. 마우스 이벤트 핸들러 외부 클래스화

마우스 클릭 시 발생하는 복잡한 분기 처리를 메인 클래스에서 분리하여 응집도를 높였습니다.

아래는 EventHandler 인터페이스를 구현하여 마우스 이벤트를 전담하는 원본 코드입니다.

```java
// TodoMouseEventHandler.java 내 외부 클래스화 발췌
package application;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ListView;

public class TodoMouseEventHandler implements EventHandler {
private TodoManager todoManager;
private ListView listView;

public TodoMouseEventHandler(TodoManager manager, ListView<TodoItem> listView) {//생성자: 매니저와 리스트 연결
    this.todoManager = manager;
    this.listView = listView;
}
```

[작동 원리]
수업 내용과 똑같이 객체 생성 시점에 TodoManager와 ListView의 참조를 넘겨받아 내부 변수에 저장합니다. 이를 통해 외부 클래스임에도 불구하고 메인 화면의 리스트뷰 상태를 읽고 매니저에게 정렬을 요청할 수 있는 권한을 얻게 됩니다.


#### 3.5.2. OS 제어권 양보 (방어 로직)

본 프로젝트의 커스텀 기능인 '취소선 긋기(단일 클릭)'가 OS의 기본 기능인 '다중 선택(Shift/Ctrl 클릭)'을 방해하지 않도록 통제합니다.

아래는 클릭 이벤트를 감지하고 방어하는 handle 메서드의 원본 코드입니다.


```java
// TodoMouseEventHandler.java 내 방어 로직 및 토글 발췌
@Override
public void handle(MouseEvent event) {
// Shift(연속 선택) 또는 Ctrl(개별 다중 선택) 키가 눌려있다면
// 내가 짠 취소선 토글 로직을 무시하고 OS 기본 다중 선택 기능만 작동하도록 넘기기
if (event.isShiftDown() || event.isControlDown()) {
return;
}

    TodoItem selectedItem = listView.getSelectionModel().getSelectedItem();
    if (selectedItem != null) {
        // 일반 클릭일 경우에만 취소선(완료) 상태를 토글합니다.
        selectedItem.setChecked(!selectedItem.isChecked());
        listView.getSelectionModel().clearSelection();
        todoManager.sortTodos();
        
        listView.refresh(); 
    }
}
```

[작동 원리]

코드 꼬임 방어: 마우스 클릭이 발생했을 때, 사용자가 Shift나 Ctrl 키를 누르고 있었는지 체크(isShiftDown(), isControlDown())를 수행합니다. 만약 눌려있다면 즉시 return하여 코드를 종료시킴으로써, 자바FX가 자체적으로 제공하는 다중 선택 기능만 온전히 작동하도록 제어권을 양보합니다.

상태 동기화: 일반 클릭일 경우 완료 상태를 반전(!)시키고, 매니저에게 정렬(sortTodos())을 요청한 뒤, 화면을 새로고침(refresh())하여 변경된 데이터를 UI에 즉각 반영합니다.



---
### 3.6. TodoKeyEventHandler 클래스
TodoKeyEventHandler는 텍스트 입력창(TextField)에서 발생하는 키보드 이벤트를 전담하는 클래스입니다. 특히 사용자가 텍스트를 입력하고 '엔터(Enter)' 키를 누르는 순간을 정확히 포착하여, 입력된 데이터를 정제하고 매니저에게 전달하여 새로운 할 일로 등록하는 과정을 통제합니다.

---

#### 3.6.1. 키보드 이벤트 핸들러 외부 클래스화
(마우스 이벤트 핸들러와 동일한 원리로 외부 클래스로 분리되었으므로 코드 및 중복 설명은 생략합니다.)
#### 3.6.2. .trim() 메서드 활용

사용자가 무의식적으로 입력한 쓰레기 데이터(공백)가 시스템에 등록되는 것을 차단하는 필수적인 데이터 전처리 로직입니다.

아래는 엔터 키 입력을 감지하고 문자열을 정제하는 handle 메서드의 원본 코드입니다.

```java
// TodoKeyEventHandler.java 내 .trim() 활용 발췌
@Override
public void handle(KeyEvent event) {

    switch (event.getCode()) {
        case ENTER:
            String text = inputField.getText().trim();//양쪽 여백 날리기
            if (!text.isEmpty()) {
                todoManager.addTodo(text);
                inputField.clear(); // 입력 후 텍스트 필드 초기화
            }
            break;
        default:
            break;
    }
}
```

[작동 원리]

여백 제거: 입력창에서 가져온 문자열 데이터에 .trim() 메서드를 적용하여, 문자열의 시작과 끝에 붙어있는 불필요한 스페이스바 공백이나 탭 등의 여백을 말끔히 잘라냅니다.

빈 데이터 방어: 만약 사용자가 스페이스바만 여러 번 누르고 엔터를 쳤을 경우, .trim()을 거치면 완전히 빈 문자열("")이 됩니다. 이후 !text.isEmpty() 조건문이 이를 거짓(False)으로 판별하여 빈 데이터가 매니저에게 전달되는 것을 안전하게 차단합니다.

---
이상으로 보고서를 읽어주셔서 감사합니다.