package Part_3;

public class MainClass {
    public static void main(String[] args) {
        GradeBook gradeBook = new GradeBook();

        Thread lecturer = new Lecturer(gradeBook);
        Thread assistant1 = new Assistant(gradeBook, "Group A");
        Thread assistant2 = new Assistant(gradeBook, "Group B");
        Thread assistant3 = new Assistant(gradeBook, "Group C");

        lecturer.start();
        assistant1.start();
        assistant2.start();
        assistant3.start();

        try {
            lecturer.join();
            assistant1.join();
            assistant2.join();
            assistant3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        gradeBook.printGrades();
    }
}
