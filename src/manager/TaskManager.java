package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;

public interface TaskManager {
    // методы для task
    int addTask(Task task);

    Task getTask(int id);

    void updateTask(Task updatedTask);

    void removeTask(int taskId);

    ArrayList<Task> getTasks();

    void removeAllTasks();

    // методы для tasks.Epic
    int addEpic(Epic epic);

    Epic getEpic(int id);

    void updateEpic(Epic updatedEpic);

    void removeEpic(int epicId);

    ArrayList<Epic> getAllEpics();

    void removeAllEpics();

    // методы для сабтасков
    int addSubtask(Subtask subtask);

    Subtask getSubtask(int id);

    void updateSubtask(Subtask updatedSubtask);

    void removeSubtask(int subtaskId);

    ArrayList<Task> getAllSubTasks();

    void removeAllSubtasks();

}
