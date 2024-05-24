package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tasks.Subtask;
import tasks.Epic;
import tasks.Task;
import manager.TaskManager;
import manager.InMemoryTaskManager;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;

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
    void testTaskIdUniqueness() {
        Task task1 = new Task("Задача 1", "Описание задачи 1");
        Task task2 = new Task("Задача 2", "Описание задачи 2");
        int taskId1 = manager.addTask(task1);
        int taskId2 = manager.addTask(task2);
        assertNotEquals(taskId1, taskId2, "Каждая задача должна иметь уникальный ID.");
    }

    @Test
    void testHistoryManagement() {
        Task task1 = new Task("Задача 1", "Описание задачи 1");
        Task task2 = new Task("Задача 2", "Описание задачи 2");
        manager.addTask(task1);
        manager.addTask(task2);
        manager.getTask(task1.getId());
        manager.getTask(task2.getId());

        List<Task> history = manager.getHistory();
        assertTrue(history.contains(task1) && history.contains(task2), "История должна содержать просмотренные задачи.");
        assertEquals(2, history.size(), "История должна содержать ровно два элемента.");
    }

    @Test
    void testTaskEqualityById() {
        // тест для проверки равенства объектов Task по ID
        Task task1 = new Task("Задача 1", "Описание задачи 1");
        Task task2 = new Task("Задача 2", "Описание задачи 2");
        task2.setId(task1.getId());

        assertEquals(task1, task2, "Задачи с одинаковым ID должны быть равны.");
    }

    @Test
    void testSubtaskEqualityById() {
        // тест для проверки равенства объектов Subtask по ID
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", 1);
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", 1);
        subtask2.setId(subtask1.getId());

        assertEquals(subtask1, subtask2, "Подзадачи с одинаковым ID должны быть равны.");
    }

    @Test
    void testEpicEqualityById() {
        // тест для проверки равенства объектов Epic по ID
        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2");
        epic2.setId(epic1.getId());

        assertEquals(epic1, epic2, "Эпики с одинаковым ID должны быть равны.");
    }

    @Test
    void testEpicNotSelfSubtask() {
        // тест для проверки, что объект Epic нельзя добавить в самого себя в виде подзадачи
        Epic epic = new Epic("Эпик", "Описание");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            epic.addSubtask(epic.getId());
        });

        assertEquals("Эпик не может содержать в себе подзадачу.", exception.getMessage());
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
    void testHistoryManagerPreservesTaskData() {
        // тест для проверки, что HistoryManager сохраняет данные задачи
        Task task = new Task("Задача", "Описание задачи");
        int taskId = manager.addTask(task);
        manager.getHistory().add(task);

        List<Task> history = manager.getHistory();
        assertEquals(1, history.size(), "История должна содержать одну задачу.");
        assertEquals(task, history.get(0), "История должна сохранять данные задачи.");
    }
}
