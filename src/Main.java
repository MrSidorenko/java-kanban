import manager.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();

        // Добавляем обычную задачу
        Task task1 = new Task("Переезд", "Переезд в новую квартиру");
        int task1Id = manager.addTask(task1);

        // Добавляем эпик
        Epic epic1 = new Epic("Разработка проекта", "Разработка нового софта");
        int epic1Id = manager.addEpic(epic1);

        // Добавляем подзадачи к эпику
        Subtask subtask1 = new Subtask("Дизайн интерфейса", "Создать дизайн главного окна", epic1Id);
        int subtask1Id = manager.addSubtask(subtask1);

        Subtask subtask2 = new Subtask("Тестировка", "Написать тесты для проекта", epic1Id);
        int subtask2Id = manager.addSubtask(subtask2);

        // Создаём новые объекты с обновлёнными данными для обновления
        Task updatedTask = new Task("Переезд обновлён", "Переезд в новую квартиру завершён");
        updatedTask.setId(task1Id);
        updatedTask.setStatus(TaskStatus.DONE);

        Epic updatedEpic = new Epic("Разработка проекта обновлён", "Разработка софта завершена");
        updatedEpic.setId(epic1Id);
        updatedEpic.setStatus(TaskStatus.IN_PROGRESS);

        Subtask updatedSubtask1 = new Subtask("Дизайн интерфейса обновлён", "Дизайн главного окна обновлён", epic1Id);
        updatedSubtask1.setId(subtask1Id);
        updatedSubtask1.setStatus(TaskStatus.IN_PROGRESS);

        Subtask updatedSubtask2 = new Subtask("Тестировка обновлён", "Тестирование проекта завершено", epic1Id);
        updatedSubtask2.setId(subtask2Id);
        updatedSubtask2.setStatus(TaskStatus.DONE);

        // Обновляем задачи в менеджере
        manager.updateTask(updatedTask);
        manager.updateEpic(updatedEpic);
        manager.updateSubtask(updatedSubtask1);
        manager.updateSubtask(updatedSubtask2);

        // Вывод всех задач
        System.out.println("Все задачи после обновления:");
        ArrayList<Task> allTasks = new ArrayList<>();
        allTasks.addAll(manager.getTasks());
        allTasks.addAll(manager.getAllEpics());
        allTasks.addAll(manager.getAllSubTasks());

        for (Task task : allTasks) {
            System.out.println(task.getType() + " " + task.getTitle() + " - " + task.getStatus());
        }

    }
}
