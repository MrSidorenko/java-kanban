package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



public class InMemoryTaskManager implements TaskManager {
    private HashMap<Integer, Task> tasks = new HashMap<>();
    private HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private LinkedList<Task> history = new LinkedList<>();
    private int currentId = 1;

    private static final int HISTORY_LIMIT = 10;

    // ниже представлены методы для task
    @Override
    public int addTask(Task task) {
        int id = currentId++;
        task.setId(id);
        tasks.put(id, task);
        return id;
    } // - 1

    @Override
    public Task getTask(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            addToHistory(task);
        }
        return task;
    }

    @Override
    public void updateTask(Task updatedTask) {
        if (tasks.containsKey(updatedTask.getId())) {
            tasks.put(updatedTask.getId(), updatedTask);
        }
    }
    // - 3


    @Override
    public void removeTask(int taskId) {
        tasks.remove(taskId);
    } // - 4

    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    } // - 5

    @Override
    public void removeAllTasks() {
        tasks.clear();
    } // - 6


    // ниже представлены все методы для работы tasks.Epic
    @Override
    public int addEpic(Epic epic) {
        int id = currentId++;
        epic.setId(id);
        epics.put(id, epic);
        return id;
    } // - 1

    // Получение эпика по ID
    @Override
    public Epic getEpic(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            addToHistory(epic);
        }
        return epic;
    }

    // Обновление эпика
    @Override
    public void updateEpic(Epic updatedEpic) {
        Epic existingEpic = epics.get(updatedEpic.getId());
        if (existingEpic == null) {
            System.out.println("Эпик с ID " + updatedEpic.getId() + " не найден.");
            return;
        }

        existingEpic.setTitle(updatedEpic.getTitle());
        existingEpic.setDescription(updatedEpic.getDescription());


        System.out.println("Информация эпика успешно обновлена.");
    }
// - 3


    // Удаление эпика
    @Override
    public void removeEpic(int epicId) {
        Epic epic = epics.remove(epicId);
        if (epic != null) {
            // Удаляем все подзадачи эпика
            ArrayList<Integer> subtaskIds = new ArrayList<>(epic.getSubtaskIds()); // потом удали если не понадобться
            for (Integer subtaskId : epic.getSubtaskIds()) {
                subtasks.remove(subtaskId);  // Используем метод removeSubtask для каждой подзадачи
            }
        }
    }
    // - 4


    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    } // - 5

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


    @Override
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
    @Override
    public int addSubtask(Subtask subtask) {
        int id = currentId++;
        subtask.setId(id);
        subtasks.put(id, subtask);
        Epic epic = epics.get(subtask.getEpicId());
        if (epic != null) {
            epic.addSubtask(id);
            updateEpicStatus(subtask.getEpicId());
        }

        return id;
    } // - 1

    @Override
    public Subtask getSubtask(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            addToHistory(subtask);
        }
        return subtask;
    }

    // Обновление подзадачи
    @Override
    public void updateSubtask(Subtask updatedSubtask) {
        if (subtasks.containsKey(updatedSubtask.getId())) {
            subtasks.put(updatedSubtask.getId(), updatedSubtask);
            updateEpicStatus(updatedSubtask.getEpicId());
        }
    }    // - 3


    // Удаление подзадачи
    @Override
    public void removeSubtask(int subtaskId) {
        Subtask subtask = subtasks.remove(subtaskId);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null) {
                epic.getSubtaskIds().remove(Integer.valueOf(subtaskId));
                updateEpicStatus(subtask.getEpicId());
            }
        }
    } // - 5

    // Получение всех подзадач
    @Override
    public ArrayList<Task> getAllSubTasks() {
        return new ArrayList<>(subtasks.values());
    }


    // Удаление всех подзадач
    @Override
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

    private void addToHistory(Task task) {
        history.remove(task);
        if (history.size() >= HISTORY_LIMIT) {
            history.removeFirst();
        }
        history.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return new LinkedList<>(history);
    }

}
