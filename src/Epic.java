import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtaskIds = new ArrayList<>();

    public Epic(String title, String description, TaskStatus status) {
        super(title, description, status);
    }

    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds; // Возвращаем копию для защиты данных
    }

    public void addSubtask(int subtaskId) {
        subtaskIds.add(subtaskId);
    }
}
