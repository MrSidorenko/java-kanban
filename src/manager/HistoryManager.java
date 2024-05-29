package manager;

import tasks.Task;
import java.util.LinkedList;
import java.util.List;

public class HistoryManager {
    private LinkedList<Task> history = new LinkedList<>();
    private static final int HISTORY_LIMIT = 10;

    public void add(Task task) {
        // Удалим проверку на существование задачи с таким же ID в истории
        if (history.size() >= HISTORY_LIMIT) {
            history.removeFirst();
        }
        history.add(task);
    }

    public List<Task> getHistory() {
        return history;
    }
}