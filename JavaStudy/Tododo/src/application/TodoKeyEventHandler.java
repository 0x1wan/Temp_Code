package application;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.TextField;

public class TodoKeyEventHandler implements EventHandler<KeyEvent> {
    private TodoManager todoManager;
    private TextField inputField;

    public TodoKeyEventHandler(TodoManager manager, TextField inputField) {//생성자: 매니저,필드 연결시키기.
        this.todoManager = manager;
        this.inputField = inputField;
    }

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
}