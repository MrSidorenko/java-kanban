import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Task;
import tasks.Subtask;

public class EpicTest {

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
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2",1);
        subtask2.setId(subtask1.getId());

        assertEquals(subtask1, subtask2, "Подзадачи с одинаковым ID должны быть равны.");
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
}

