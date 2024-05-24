package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import tasks.Subtask;
import tasks.Epic;

public class SubtaskTest {

    @Test
    void testSubtaskEqualityByInheritedId() {
        Subtask subtask1 = new Subtask("Сабтаска 1", "Описание 1", 1);
        subtask1.setId(2);
        Subtask subtask2 = new Subtask("Сабтаска 2", "Описание 2", 1);
        subtask2.setId(2);

        assertEquals(subtask1, subtask2, "Подзадачи с одинаковым ID должны быть одинаковыми");
    }

    @Test
    void testSubtaskCannotHaveItselfAsEpic() {
        Subtask subtask = new Subtask("Сабтаска 1", "Описание", 1);
        subtask.setId(1);

        assertThrows(IllegalArgumentException.class, () -> {
            subtask.setEpicId(1);
        });
    }



}
