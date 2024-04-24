public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();

        Task task1 = new Task("Переезд", "Переезд в новую квартиру", TaskStatus.NEW);
        int task1Id = manager.addTask(task1);

        Epic epic1 = new Epic("Разработка проекта", "Разработка нового софта", TaskStatus.NEW);
        int epic1Id = manager.addTask(epic1);

        Subtask subtask1 = new Subtask("Дизайн интерфейса", "Создать дизайн главного окна", TaskStatus.NEW, epic1Id);
        int subtask1Id = manager.addTask(subtask1);

        Subtask subtask2 = new Subtask("Тестировка", "Написать тесты для проекта", TaskStatus.NEW, epic1Id);
        int subtask2Id = manager.addTask(subtask2);



        manager.updateTaskStatus(subtask1Id, TaskStatus.DONE);
        manager.updateTaskStatus(subtask2Id, TaskStatus.IN_PROGRESS);

        System.out.println("Все задачи:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task.getTitle() + " - " + task.getStatus());
        }
    }
}
