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

    public void setEpicId(int epicId) {
        if (epicId == this.getId()) {
            throw new IllegalArgumentException("Сабтаска не может быть выставлена эпиком для самой себя");
        }
        this.epicId = epicId;
    }
}
