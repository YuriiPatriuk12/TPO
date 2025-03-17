package Part_3;

import java.util.*;
import java.util.concurrent.locks.*;

public class GradeBook {
    private final Map<String, List<Integer>> grades = new HashMap<>();
    private final Lock lock = new ReentrantLock();

    public GradeBook() {
        grades.put("Group A", new ArrayList<>());
        grades.put("Group B", new ArrayList<>());
        grades.put("Group C", new ArrayList<>());
    }

    public void addGrade(String group, int grade) {
        lock.lock();
        try {
            if (grades.containsKey(group)) {
                grades.get(group).add(grade);
                System.out.println(Thread.currentThread().getName() + " added grade " + grade + " to " + group);
            }
        } finally {
            lock.unlock();
        }
    }

    public void printGrades() {
        grades.forEach((group, gradeList) -> {
            System.out.println(group + " Grades: " + gradeList);
        });
    }
}