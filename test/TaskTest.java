import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tasks.Task;
import manager.TaskManager;
import manager.InMemoryTaskManager;

public class TaskTest {

    @Test
    void testTaskEqualityById() {
        Task task1 = new Task("Таска 1", "Описание 1");
        task1.setId(1);
        Task task2 = new Task("Таска 2", "Описание 2");
        task2.setId(1);

        // Убедитесь, что метод equals правильно реализован в классе Task
        assertEquals(task1, task2, "Задачи с одинаковым ID должны быть одинаковыми.");
    }

    @Test
    void testTaskImmutabilityOnAdd() {
        TaskManager manager = new InMemoryTaskManager();
        Task task = new Task("Таска", "Описание");
        task.setId(1);  // Ensure ID is set

        // Сохраняем копию задачи перед изменением
        Task savedTaskBeforeChange = new Task(task);

        manager.addTask(task);

        // Изменяем поля задачи после добавления в менеджер задач
        task.setTitle("Изменено название");
        task.setDescription("Измененное описание");

        // Извлекаем задачу из менеджера и проверяем, что она не изменилась
        Task retrievedTask = manager.getTask(task.getId());
        assertEquals(savedTaskBeforeChange, retrievedTask, "Задание не должно меняться после добавления");
    }

}
