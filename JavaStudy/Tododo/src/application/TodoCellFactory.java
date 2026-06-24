package application;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class TodoCellFactory implements Callback<ListView<TodoItem>, ListCell<TodoItem>> {
	//callback 인터페이스: 입출력을 제네릭으로 정의해서 입력된 값을 포장해서 출력해줌.
	
    @Override
    public ListCell<TodoItem> call(ListView<TodoItem> param) {
    	//call 함수가 ListCell을 던져줄거임. param은 인터페이스에서 구현하라고 했는데 실제론 안 사용되는 매개변수
        return new ListCell<TodoItem>() {//익명 클래스
            
            // 스타일을 동적으로 적용하는 유틸리티 메서드
            private void updateStyles(TodoItem item, boolean isSelected) {
                if (item == null) {//아이템이 비워졌을 때, 투명으로 바꾸기.
                    setStyle("-fx-background-color: transparent;");
                    return;
                }
                
                // 선택 여부에 따라 배경색을 결정 (선택되면 #3d3d3d 적용)
                String bgStyle = isSelected ? "-fx-background-color: #3d3d3d;" : "-fx-background-color: transparent;";
                
                if (item.isChecked()) {
                    setStyle(bgStyle + " -fx-text-fill: #666666; -fx-strikethrough: true;");
                } else if (item.isImportant()) {
                    setStyle(bgStyle + " -fx-text-fill: #FFD700; -fx-strikethrough: false;");
                } else {
                    setStyle(bgStyle + " -fx-text-fill: #ffffff; -fx-strikethrough: false;");
                }
            }

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

            // Shift 클릭 등 선택 상태가 변할 때마다 UI를 즉각 업데이트하는 콜백
            @Override
            public void updateSelected(boolean selected) {
                super.updateSelected(selected);
                if (!isEmpty() && getItem() != null) {//cell이 존재한다면(값이 있는 유효한 셀이) 선택했을때 스타일로 바꾸기
                    updateStyles(getItem(), selected);
                }
            }
        };
    }
}