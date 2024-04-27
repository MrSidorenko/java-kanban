package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.util.HashMap;
import java.util.ArrayList;

public class TaskManager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private int currentId = 1;

    // ниже представлены методы для task
    public int addTask(Task task) {
        int id = currentId++;
        task.setId(id);
        tasks.put(id, task);
        return id;
    } // - 1

    public Task getTask(int id) {
        return tasks.get(id);
    } // - 2

    public void updateTask(Task updatedTask) {
        if (tasks.containsKey(updatedTask.getId())) {
            tasks.put(updatedTask.getId(), updatedTask);
        }
    }
    // - 3


    public void removeTask(int taskId) {
        tasks.remove(taskId);
    } // - 4

    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    } // - 5

    public void removeAllTasks() {
        tasks.clear();
    } // - 6


    // ниже представлены все методы для работы tasks.Epic
    public int addEpic(Epic epic) {
        int id = currentId++;
        epic.setId(id);
        epics.put(id, epic);
        return id;
    } // - 1

    // Получение эпика по ID
    public Epic getEpic(int id) {
        return epics.get(id);
    } // - 2

    // Обновление эпика
    public void updateEpic(Epic updatedEpic) {
        Epic existingEpic = epics.get(updatedEpic.getId());
        if (existingEpic == null) {
            return;
        }

        // Создаем новый эпик с переданными параметрами
        Epic newEpic = new Epic(updatedEpic.getTitle(), updatedEpic.getDescription());
        newEpic.setId(updatedEpic.getId()); // Устанавливаем тот же ID


        // копируем список подзадач из существующего эпика
        newEpic.getSubtaskIds().addAll(existingEpic.getSubtaskIds());

        // Заменяем старый эпик на новый в мапе
        epics.put(updatedEpic.getId(), newEpic);
    }
// - 3


    // Удаление эпика
    public void removeEpic(int epicId) {
        Epic epic = epics.remove(epicId);
        if (epic != null) {
            // Удаляем все подзадачи эпика
            ArrayList<Integer> subtaskIds = new ArrayList<>(epic.getSubtaskIds());
            for (Integer subtaskId : subtaskIds) {
                subtasks.remove(subtaskId);  // Используем метод removeSubtask для каждой подзадачи
            }
        }
    }
    // - 4


    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    } // - 5


    public void removeAllEpics() {

        for (Epic epic : new ArrayList<>(epics.values())) {
            ArrayList<Integer> subtaskIds = new ArrayList<>(epic.getSubtaskIds());
            for (Integer subtaskId : subtaskIds) {
                removeSubtask(subtaskId);
            }
        }

        epics.clear();
    }
    // - 6


    // ниже представлены все методы для сабтасков
    public int addSubtask(Subtask subtask) {
        int id = currentId++;
        subtask.setId(id);
        subtasks.put(id, subtask);
        Epic epic = epics.get(subtask.getEpicId());
        if (epic != null) {
            epic.addSubtask(id);
            updateEpic(epic);
        }

        return id;
    } // - 1

    public Subtask getSubtask(int id) {
        return subtasks.get(id);
    } // - 2

    // Обновление подзадачи
    public void updateSubtask(Subtask updatedSubtask) {
        if (subtasks.containsKey(updatedSubtask.getId())) {
            subtasks.put(updatedSubtask.getId(), updatedSubtask);
            updateEpicStatus(updatedSubtask.getEpicId());
        }
    }    // - 3

    private void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) {
            return;
        }

        boolean allDone = true;
        boolean anyInProgress = false;

        for (Integer subtaskId : epic.getSubtaskIds()) {
            Subtask subtask = subtasks.get(subtaskId);
            if (subtask != null) {
                if (subtask.getStatus() != TaskStatus.DONE) {
                    allDone = false;
                }
                if (subtask.getStatus() != TaskStatus.NEW) {
                    anyInProgress = true;
                }
            }
        }

        if (allDone) {
            epic.setStatus(TaskStatus.DONE);
        } else if (anyInProgress) {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        } else {
            epic.setStatus(TaskStatus.NEW);
        }
    } // - 4


    // Удаление подзадачи
    public void removeSubtask(int subtaskId) {
        Subtask subtask = subtasks.remove(subtaskId);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null) {
                epic.getSubtaskIds().remove(Integer.valueOf(subtaskId));
                updateEpic(epic);
            }
        }
    } // - 5

    // Получение всех подзадач
    public ArrayList<Task> getAllSubTasks() {
        ArrayList<Task> returnSub = new ArrayList<>();
        returnSub.addAll(subtasks.values());
        return returnSub;
    } // - 6


    // Удаление всех подзадач
    public void removeAllSubtasks() {
        for (Epic epic : epics.values()) {
            epic.getSubtaskIds().clear();
            epic.setStatus(TaskStatus.NEW);
        }
        subtasks.clear();
    }


    // доп методы
    public ArrayList<Subtask> getSubtasksOfEpic(int epicId) {
        ArrayList<Subtask> subtaskList = new ArrayList<>();
        Epic epic = epics.get(epicId);
        if (epic != null) {
            for (Integer subtaskId : epic.getSubtaskIds()) {
                Subtask subtask = subtasks.get(subtaskId);
                if (subtask != null) {
                    subtaskList.add(subtask);
                }
            }
        }
        return subtaskList;
    }
    public ArrayList<Task> getAllItems() {
        ArrayList<Task> allItems = new ArrayList<>();
        allItems.addAll(tasks.values());
        allItems.addAll(epics.values());
        allItems.addAll(subtasks.values());
        return allItems;
    }


}
