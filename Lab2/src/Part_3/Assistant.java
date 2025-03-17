package Part_3;

import java.util.Random;

class Assistant extends Thread {
    private final GradeBook gradeBook;
    private final Random random = new Random();
    private final String group;

    public Assistant(GradeBook gradeBook, String group) {
        this.gradeBook = gradeBook;
        this.group = group;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            gradeBook.addGrade(group, random.nextInt(101));
        }
    }
}
