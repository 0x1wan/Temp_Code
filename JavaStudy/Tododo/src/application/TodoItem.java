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
    public String getText() { return text; }
    public boolean isChecked() { return isChecked; }
    public void setChecked(boolean checked) { this.isChecked = checked; }
    public boolean isImportant() { return isImportant; }
    public void setImportant(boolean important) { this.isImportant = important; }
    public double getOrderId() { return orderId; }
}