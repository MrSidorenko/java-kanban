package manager;

import tasks.Task;

public class Node {
    Task task;
    Node next;
    Node prev;

    Node(Task task) {
        this.task = task;
    }
}
