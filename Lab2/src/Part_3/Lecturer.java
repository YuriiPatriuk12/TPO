package Part_3;

import java.util.Random;

class Lecturer extends Thread {
    private final GradeBook gradeBook;
    private final Random random = new Random();

    public Lecturer(GradeBook gradeBook) {
        this.gradeBook = gradeBook;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            gradeBook.addGrade("Group A", random.nextInt(101));
            gradeBook.addGrade("Group B", random.nextInt(101));
            gradeBook.addGrade("Group C", random.nextInt(101));
        }
    }
}