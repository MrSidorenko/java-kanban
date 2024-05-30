import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Task;
import manager.HistoryManager;
import manager.InMemoryHistoryManager;
import java.util.List;

public class HistoryManagerTest {

    private HistoryManager historyManager;
    private Task task1;
    private Task task2;

    @BeforeEach
    void setUp() {
        historyManager = new InMemoryHistoryManager();
        task1 = new Task("Таска 1", "Описание 1");
        task1.setId(1);
        task2 = new Task("Таска 2", "Описание 2");
        task2.setId(2);
    }

    @Test
    void testAddToHistory() {
        historyManager.add(task1);

        assertEquals(1, historyManager.getHistory().size(), "История должна содержать одну задачу.");
        assertEquals(task1, historyManager.getHistory().get(0), "Задача в истории должна быть task1.");
    }

    @Test
    void testHistoryLimit() {
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
        historyManager.add(task1);

        Task updatedTask = new Task("Таска 1", "Обновленное описание");
        updatedTask.setId(1);
        historyManager.add(updatedTask);

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "История должна содержать одну задачу.");  // Обновляем проверку на одну задачу
        assertEquals("Обновленное описание", history.get(0).getDescription(), "Описание задачи должно быть обновлено.");
    }

    @Test
    void testHistoryManagement() {
        historyManager.add(task1);
        historyManager.add(task2);

        List<Task> history = historyManager.getHistory();
        assertTrue(history.contains(task1) && history.contains(task2), "История должна содержать просмотренные задачи.");
        assertEquals(2, history.size(), "История должна содержать ровно два элемента.");
    }
}
