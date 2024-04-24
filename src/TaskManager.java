import java.util.HashMap;
import java.util.ArrayList;

public class TaskManager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private int currentId = 1;

    public int addTask(Task task) {
        int id = currentId++;
        task.setId(id);
        tasks.put(id, task);

        // Если задача является подзадачей, добавляем ее ID в список подзадач эпика
        if (task instanceof Subtask) {
            Subtask subtask = (Subtask) task;
            Epic epic = (Epic) tasks.get(subtask.getEpicId());
            if (epic != null) {
                epic.addSubtask(id);
            }
        }

        return id;
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

public void updateTaskStatus(int taskId, TaskStatus newStatus) {
    Task task = tasks.get(taskId);
    if (task == null) {
        return; // Задачи с таким ID нет
    }

    // Прямое обновление статуса задачи
    task.setStatus(newStatus);

    // Если это подзадача, обновить статус соответствующего эпика
    if (task instanceof Subtask) {
        int epicId = ((Subtask) task).getEpicId();
        updateEpicStatus(epicId);
    } else if (task instanceof Epic) {
        // Если задача является эпиком, необходимо пересчитать его статус
        // на основе статусов всех его подзадач
        updateEpicStatus(task.getId());
    }
}


    private void updateEpicStatus(int epicId) {
        Epic epic = (Epic) tasks.get(epicId);
        if (epic == null) {
            return;
        }

        boolean allDone = true;
        boolean allNew = true;

        for (Integer subtaskId : epic.getSubtaskIds()) {
            Task subtask = tasks.get(subtaskId);
            if (subtask.getStatus() != TaskStatus.DONE) {
                allDone = false;
            }
            if (subtask.getStatus() != TaskStatus.NEW) {
                allNew = false;
            }
        }

        // Этот блок обеспечивает, что если хотя бы одна подзадача не новая и не выполнена,
        // то эпик переходит в статус IN_PROGRESS.
        if (allNew) {
            epic.setStatus(TaskStatus.NEW);
        } else if (allDone) {
            epic.setStatus(TaskStatus.DONE);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }

    public void removeTask(int taskId) {
        tasks.remove(taskId);
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }
}
