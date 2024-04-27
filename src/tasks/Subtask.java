package tasks;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String title, String description, int epicId) {
        super(title, description);
        this.epicId = epicId;
        this.status = TaskStatus.NEW;
        this.type = TaskType.SUBTASK;
    }



    public int getEpicId() {
        return epicId;
    }
}
