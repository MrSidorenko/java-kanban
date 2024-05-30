import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tasks.Subtask;
import tasks.Epic;
import tasks.Task;
import manager.TaskManager;
import manager.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeEach;
import manager.Managers;

public class TaskManagerTest {
    TaskManager manager;

    @BeforeEach
    void setUp() {
        manager = new InMemoryTaskManager();
    }

    @Test
    void testAddFindTasks() {
        Task task = new Task("Задача", "Описание задачи");
        Epic epic = new Epic("Эпик", "Описание эпика");
        Subtask subtask = new Subtask("Подзадача", "Описание подзадачи", 1);

        // Добавление задач в менеджер
        int taskId = manager.addTask(task);
        int epicId = manager.addEpic(epic);
        int subtaskId = manager.addSubtask(subtask);

        // Проверка, что возвращаемые задачи совпадают с добавленными
        assertEquals(task, manager.getTask(taskId), "Полученная задача должна совпадать с добавленной.");
        assertEquals(epic, manager.getEpic(epicId), "Полученный эпик должен совпадать с добавленным.");
        assertEquals(subtask, manager.getSubtask(subtaskId), "Полученная подзадача должна совпадать с добавленной.");

        // Проверка на то, что задачи правильно идентифицируются по их ID
        assertEquals(taskId, task.getId(), "ID задачи должен совпадать с ID, возвращенным при добавлении.");
        assertEquals(epicId, epic.getId(), "ID эпика должен совпадать с ID, возвращенным при добавлении.");
        assertEquals(subtaskId, subtask.getId(), "ID подзадачи должен совпадать с ID, возвращенным при добавлении.");
    }

    @Test
    void testDeleteTask() {
        Task task = new Task("Новая задача", "Описание задачи");
        int taskId = manager.addTask(task);
        manager.removeTask(taskId);
        assertNull(manager.getTask(taskId), "Задача должна быть null после удаления.");
    }







    @Test
    void testTaskImmutability() {
        // тест для проверки неизменности задачи при добавлении в менеджер
        Task task = new Task("Задача", "Описание задачи");
        int taskId = manager.addTask(task);

        Task retrievedTask = manager.getTask(taskId);
        assertEquals(task, retrievedTask, "Задача должна оставаться неизменной после добавления в менеджер.");
    }

    @Test
    void testGetDefaultTaskManager() {
        TaskManager taskManager = Managers.getDefault();
        assertNotNull(taskManager, "TaskManager должен быть проинициализирован и готов к работе");
    }
    @Test
    void testTaskIdUniqueness() {
        Task task1 = new Task("Задача 1", "Описание задачи 1");
        Task task2 = new Task("Задача 2", "Описание задачи 2");
        int taskId1 = manager.addTask(task1);
        int taskId2 = manager.addTask(task2);
        assertNotEquals(taskId1, taskId2, "Каждая задача должна иметь уникальный ID.");
    }

    @Test
    void testRemoveAllTasks() {
        Task task1 = new Task("Задача 1", "Описание задачи 1");
        Task task2 = new Task("Задача 2", "Описание задачи 2");
        manager.addTask(task1);
        manager.addTask(task2);
        manager.removeAllTasks();

        // Проверим, что все задачи удалены из менеджера
        assertTrue(manager.getTasks().isEmpty(), "Все задачи должны быть удалены.");

    }

}
