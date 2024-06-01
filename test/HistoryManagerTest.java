import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import manager.InMemoryHistoryManager;
import tasks.Task;
import java.util.List;

public class HistoryManagerTest {

    @Test
    void testAddToHistory() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        Task task = new Task("Таска 1", "Описание 1");
        task.setId(1);
        historyManager.add(task);

        assertEquals(1, historyManager.getHistory().size(), "История должна содержать одну задачу.");
        assertEquals(task, historyManager.getHistory().get(0), "Задача в истории должна быть task.");
    }

    @Test
    void testAddNullToHistory() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        historyManager.add(null);

        assertEquals(0, historyManager.getHistory().size(), "История не должна содержать null задач.");
    }

    @Test
    void testHistoryLimit() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        for (int i = 1; i <= 11; i++) {
            Task task = new Task("Таска " + i, "Описание " + i);
            task.setId(i);
            historyManager.add(task);
        }

        assertEquals(10, historyManager.getHistory().size(), "История должна содержать не более 10 задач.");
        assertEquals(2, historyManager.getHistory().get(0).getId(), "Первая задача в истории должна быть второй добавленной.");
    }

    @Test
    void testHistoryPreservesPreviousVersions() {
        InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
        Task task = new Task("Таска 1", "Описание 1");
        task.setId(1);
        historyManager.add(task);

        Task updatedTask = new Task("Таска 1", "Обновленное описание");
        updatedTask.setId(1);
        historyManager.add(updatedTask);

        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size(), "История должна содержать обе версии задачи.");
        assertEquals("Описание 1", history.get(0).getDescription(), "Первая версия задачи должна быть сохранена.");
        assertEquals("Обновленное описание", history.get(1).getDescription(), "Обновленная версия задачи должна быть сохранена.");
    }
}
