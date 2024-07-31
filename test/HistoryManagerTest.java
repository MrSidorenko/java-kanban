import manager.HistoryManager;
import manager.InMemoryHistoryManager;
import tasks.Task;

import java.util.List;

public class HistoryManagerTest {
    public static void main(String[] args) {
        HistoryManager historyManager = new InMemoryHistoryManager();

        Task task1 = new Task("Task 1", "Description 1");
        task1.setId(1);
        Task task2 = new Task("Task 2", "Description 2");
        task2.setId(2);
        Task task3 = new Task("Task 3", "Description 3");
        task3.setId(3);

        // Test adding tasks
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        System.out.println("History after adding tasks: " + historyManager.getHistory().size()); // Expected: 3

        // Test removing task
        historyManager.remove(2);
        System.out.println("History after removing task 2: " + historyManager.getHistory().size()); // Expected: 2

        // Test re-adding task
        historyManager.add(task2);
        System.out.println("History after re-adding task 2: " + historyManager.getHistory().size()); // Expected: 3

        // Test updating task
        historyManager.add(task1); // task1 should move to the end
        List<Task> history = historyManager.getHistory();
        System.out.println("Last task in history should be Task 1: " + history.get(history.size() - 1).getTitle()); // Expected: "Task 1"
    }
}
