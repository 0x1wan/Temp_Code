package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    // 관리 클래스 객체 생성
    private TodoManager todoManager = new TodoManager();

    @Override
    public void start(Stage primaryStage) {// 무조건 재구현 해야함
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #1e1e1e; -fx-font-family: 'Consolas'; -fx-font-size: 14px;");

        // 가운데 영역: 할 일 목록 리스트 뷰 배치 (기본 제공 컨트롤임)
        ListView<TodoItem> listView = new ListView<>(todoManager.getTodoList());
        listView.setStyle("-fx-control-inner-background: #1e1e1e; -fx-background-color: transparent;");
        
        // 이 설정을 해주면 shift나 control + click에 대한 처리를 os표준으로 사용 가능함.
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        
        // 커스텀 셀 팩토리 및 마우스 이벤트 핸들러 연결
        listView.setCellFactory(new TodoCellFactory()); //이러면 목록에 대한 더 자유로운 UI조정이 가능.
        TodoMouseEventHandler mouseHandler = new TodoMouseEventHandler(todoManager, listView);
        listView.setOnMouseClicked(mouseHandler);
        
        //Delete 키 입력 시 단일 또는 다중 선택된 항목을 일괄 삭제하는 로직 (람다 표현법으로 구현)
        listView.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.DELETE) {//del키를 눌렀을 때 
                // 동시 수정 예외(ConcurrentModificationException) 방지를 위해 선택된 리스트를 복사하여 순회.
            	//상위 인터페이스 List로 업캐스팅해서 ArrayList 객체를 생성하고 그 안에 현재 값들을 복사해 넣음.
                List<TodoItem> selectedItems = new ArrayList<>(listView.getSelectionModel().getSelectedItems());
                for (TodoItem item : selectedItems) {//for-each문 사용해서 selctedItems만 item에 담김
                    if (item != null) {
                        todoManager.removeTodo(item);
                    }
                }
                listView.getSelectionModel().clearSelection(); // 삭제 후 선택 해제
            }
        });
        
        root.setCenter(listView);

        // 하단 영역: 텍스트 입력창 및 버튼들을 배치할 HBox 구성
        HBox bottomBox = new HBox(10);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(10));
        
        TextField inputField = new TextField();
        inputField.setPromptText("TODO 할 일을 입력하세요.");
        inputField.setStyle("-fx-background-color: #2d2d2d; -fx-text-fill: white; -fx-border-color: #555555;");
        HBox.setHgrow(inputField, Priority.ALWAYS); // 입력창이 가로 남은 공간을 전부 차지하도록 설정
        
        // 키보드 엔터 입력 이벤트 핸들러 연결
        TodoKeyEventHandler keyHandler = new TodoKeyEventHandler(todoManager, inputField);
        inputField.setOnKeyPressed(keyHandler); // 키보드 입력에 대한 이벤트를 핸들러 객체에 연결

        // 강조 버튼 조건별 동작 연결
        Button btnImportant = new Button("강조"); //버튼 객체 생성
        btnImportant.setStyle("-fx-background-color: #2d2d2d; -fx-text-fill: white; -fx-border-color: #555555;");
        
        btnImportant.setOnAction(e -> {//강조 버튼에 대한 람다식 이벤트 구현
            TodoItem selectedItem = listView.getSelectionModel().getSelectedItem();//현재 선택된 아이템에 레퍼런스 변수 달기
            if (selectedItem != null) {
                // 리스트뷰에 선택된 항목이 있다면 해당 항목의 강조 상태를 토글
                selectedItem.setImportant(!selectedItem.isImportant());//!연산자로 boolean값 반전
                todoManager.sortTodos();//재정렬
                listView.refresh();//UI 내부 값 반영해서 다시 그리기
                listView.getSelectionModel().clearSelection(); //선택 상태 (회색 배경) 풀기
            } else {
                // 입력하는 텍스트 필드 문자열 토글 로직 적용
                String currentText = inputField.getText();
                if (currentText.startsWith("[강조] ")) {//문자열에 시작점을 검사하는 메소드
                    inputField.setText(currentText.replace("[강조] ", "")); // 있으면 제거
                } else {
                    inputField.setText("[강조] " + currentText); // 없으면 추가
                }
                inputField.positionCaret(inputField.getText().length());
            }
        });


        // 전체 삭제 버튼 생성 및 매니저 로직 연결
        Button btnClear = new Button("전체 삭제");
        btnClear.setStyle("-fx-background-color: #2d2d2d; -fx-text-fill: white; -fx-border-color: #555555;");
        btnClear.setOnAction(e -> todoManager.clearAllTodos());
        
        // 하단 컨테이너에 입력 필드와 두 버튼을 순서대로 조립
        bottomBox.getChildren().addAll(inputField, btnImportant, btnClear);
        root.setBottom(bottomBox); //레이아웃에 조립

        
        Scene scene = new Scene(root, 350, 450); 
        
        primaryStage.setTitle("Tododo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}