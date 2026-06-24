package application;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ListView;

public class TodoMouseEventHandler implements EventHandler<MouseEvent> {
    private TodoManager todoManager;
    private ListView<TodoItem> listView;

    public TodoMouseEventHandler(TodoManager manager, ListView<TodoItem> listView) {//생성자: 매니저와 리스트 연결
        this.todoManager = manager;
        this.listView = listView;
    }

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
}