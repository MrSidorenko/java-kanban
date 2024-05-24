package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    // ниже представлены методы для task
    int addTask(Task task) // - 1
    ;

    Task getTask(int id) // - 2
    ;

    void updateTask(Task updatedTask);

    void removeTask(int taskId) // - 4
    ;

    ArrayList<Task> getTasks() // - 5
    ;

    void removeAllTasks() // - 6
    ;

    // ниже представлены все методы для работы tasks.Epic
    int addEpic(Epic epic) // - 1
    ;

    // Получение эпика по ID
    Epic getEpic(int id) // - 2
    ;

    // Обновление эпика
    void updateEpic(Epic updatedEpic);

    // Удаление эпика
    void removeEpic(int epicId);

    ArrayList<Epic> getAllEpics() // - 5
    ;

    void removeAllEpics();

    // ниже представлены все методы для сабтасков
    int addSubtask(Subtask subtask) // - 1
    ;

    Subtask getSubtask(int id) // - 2
    ;

    // Обновление подзадачи
    void updateSubtask(Subtask updatedSubtask);    // - 3
    ;

    // Удаление подзадачи
    void removeSubtask(int subtaskId); // - 5


    // Получение всех подзадач
    ArrayList<Task> getAllSubTasks();

    // Удаление всех подзадач
    void removeAllSubtasks();

    List<Task> getHistory();
}
