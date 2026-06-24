package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TodoManager {
    // JavaFX의 ListView와 자동으로 동기화되는 리스트입니다.
    private ObservableList<TodoItem> todoList;
    
    // 파일명과 데이터를 나눌 구분자(Delimiter) 상수를 정의합니다.
    private final String FILE_NAME = "todo_data.txt";
    private final String DELIMITER = "::"; 

    public TodoManager() {//생성자
        todoList = FXCollections.observableArrayList();
        loadTodos(); // 프로그램 시작 시 텍스트 파일에서 데이터를 불러옵니다.
    }

    public ObservableList<TodoItem> getTodoList() {
        return todoList;
    }

    public void addTodo(String text) {
        // 고유 순서값으로 현재 밀리초 시간을 부여합니다.
        TodoItem newItem = new TodoItem(text, System.currentTimeMillis());
        todoList.add(newItem);
        sortTodos(); // 추가 후 즉시 정렬 및 저장
    }

    public void removeTodo(TodoItem item) {
        todoList.remove(item);
        saveTodos(); // 삭제 후 즉시 저장
    }

    // 정렬 논리 구현 (1순위 기준 - 체크 여부 / 2순위 기준 - 강조 여부/ 3순위 기준 )
    public void sortTodos() {
        List<TodoItem> list = new ArrayList<>(todoList);
        list.sort((a, b) -> {//람다 구현방식으로 sort방식을 내가 정의한 방식으로 기준 제공 (그럼 자바가 알아서 정렬)
            if (a.isChecked() != b.isChecked()) {
                return a.isChecked() ? 1 : -1; 
            }
            if (a.isImportant() != b.isImportant()) {
                return a.isImportant() ? -1 : 1; 
            }
            return Double.compare(a.getOrderId(), b.getOrderId());
        });
        
        todoList.setAll(list);
        saveTodos(); // 정렬된 최종 상태를 텍스트 파일에 동기화
    }

   

    private void saveTodos() {
        // try-with-resources 구문을 사용하여 메모리 누수를 방지
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (TodoItem item : todoList) {//for-each문
                // 문자열 예시: "할일::false::true::16900000.0"
                String line = item.getText() + DELIMITER + 
                              item.isChecked() + DELIMITER + 
                              item.isImportant() + DELIMITER + 
                              item.getOrderId();
                bw.write(line);
                bw.newLine(); // 다음 줄로 넘김
            }
        } catch (IOException e) {
            System.out.println("텍스트 파일 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    private void loadTodos() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return; // 파일이 없으면 불러오기를 생략합니다.
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {//빈줄 나올때 까지
                String[] parts = line.split(DELIMITER);//구분자 기준으로 4칸짜리 배열에 담기
                
                // 구분자로 나누었을 때 정확히 4개의 속성이 나오는지 체크
                if (parts.length == 4) {
                    String text = parts[0];
                    boolean isChecked = Boolean.parseBoolean(parts[1]);
                    boolean isImportant = Boolean.parseBoolean(parts[2]);
                    double orderId = Double.parseDouble(parts[3]);

                    TodoItem item = new TodoItem(text, orderId);
                    item.setChecked(isChecked);
                    item.setImportant(isImportant);
                    todoList.add(item);
                }
            }
            // 모든 데이터를 불러온 후 리스트를 한 번 정렬 (내부에서 saveTodos는 호출하지 않도록 여기서 람다 표현법으로 다시 코드 적음)
            List<TodoItem> list = new ArrayList<>(todoList);
            list.sort((a, b) -> {
                if (a.isChecked() != b.isChecked()) return a.isChecked() ? 1 : -1;
                if (a.isImportant() != b.isImportant()) return a.isImportant() ? -1 : 1;
                return Double.compare(a.getOrderId(), b.getOrderId());
            });
            todoList.setAll(list);
            
        } catch (IOException | NumberFormatException e) {
            System.out.println("텍스트 파일 읽기 중 데이터가 손상되었거나 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    public void clearAllTodos() {
        todoList.clear();
        saveTodos();
    }
    
    
}