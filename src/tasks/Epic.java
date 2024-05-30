package tasks;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtaskIds = new ArrayList<>();

    public Epic(String title, String description) {
        super(title, description);
        this.status = TaskStatus.NEW;
    }

    @Override
    public TaskType getType() {
        return TaskType.EPIC;
    }

    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    public void addSubtask(int subtaskId) {
        if (subtaskId == this.getId()) {
            throw new IllegalArgumentException("Эпик не может содержать в себе подзадачу.");
        }
        subtaskIds.add(subtaskId);
    }

}
