import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tasks.Task;
import manager.HistoryManager;
import java.util.List;

public class HistoryManagerTest {

    @Test
    void testAddToHistory() {
        HistoryManager historyManager = new HistoryManager();
        Task task = new Task("Таска 1", "Описание 1");
        task.setId(1);
        historyManager.add(task);

        assertEquals(1, historyManager.getHistory().size(), "История должна содержать одну задачу.");
        assertEquals(task, historyManager.getHistory().get(0), "Задача в истории должна быть task.");
    }

    @Test
    void testHistoryLimit() {
        HistoryManager historyManager = new HistoryManager();
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
        HistoryManager historyManager = new HistoryManager();
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

    @Test
    void testHistoryManagement() {
        HistoryManager historyManager = new HistoryManager();
        Task task1 = new Task("Задача 1", "Описание задачи 1");
        Task task2 = new Task("Задача 2", "Описание задачи 2");
        task1.setId(1);
        task2.setId(2);
        historyManager.add(task1);
        historyManager.add(task2);

        List<Task> history = historyManager.getHistory();
        assertTrue(history.contains(task1) && history.contains(task2), "История должна содержать просмотренные задачи.");
        assertEquals(2, history.size(), "История должна содержать ровно два элемента.");
    }
}
