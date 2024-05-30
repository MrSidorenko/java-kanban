package manager;

import tasks.Task;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    private LinkedList<Task> history = new LinkedList<>();
    private static final int HISTORY_LIMIT = 10;

    @Override
    public void add(Task task) {
        history.removeIf(t -> t.getId() == task.getId()); // Удаляем задачу с таким же ID
        if (history.size() >= HISTORY_LIMIT) {
            history.removeFirst();
        }
        history.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history);
    }
}
