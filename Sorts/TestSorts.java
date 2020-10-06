package Sorts;

import java.util.Date;
import java.util.ArrayList;
import java.util.Random;

public class TestSorts {
    public static int len = 10000;
    public static boolean detailedReport = false;
    private static final String[] maleNames =
            {"Николай", "Алексей", "Ярослав", "Дмитрий", "Олег"};
    private static final String[] femaleNames =
            {"Милена", "Диана", "Оксана", "Карина", "Ольга"};
    private static final String[] subjects =
            {"ООП", "Физ-ра", "Мат. анализ", "Java", "СиАОД"};

    public static void main(String[] args) {
        System.out.println("Алгоритм сортировки вставками");
        System.out.println(testSort("Insertion", detailedReport));
        System.out.println("Алгоритм быстрой сортировки (Quick Sort)");
        System.out.println(testSort("Quick", detailedReport));
        System.out.println("Алгоритм сортировки слиянием (Merge Sort)");
        System.out.println(testSort("Merge", detailedReport));
    }

    public static String testSort(String type, boolean detailedReport) {
        Random rand = new Random();
        Student[] students;
        String name;
        boolean gender;
        long id;
        if(detailedReport)
            System.out.println("Исходный массив:");
        switch (type) {
            case ("Insertion") -> students = new Student1[len];
            case ("Quick") -> students = new Student2[len];
            case ("Merge") -> students = new Student[len];
            default -> students = new Student[len];
        }
        for(int i = 0; i < len; i++) {
            gender = rand.nextBoolean();
            name = (gender ? maleNames : femaleNames)[Math.abs(rand.nextInt() %
                    (gender ? maleNames : femaleNames).length)];
            id = Math.abs(rand.nextInt());
            students[i] = switch (type) {
                case ("Insertion") -> new Student1(name, gender, id);
                case ("Quick") -> new Student2(name, gender, id);
                case ("Merge") -> new Student(name, gender, id);
                default -> new Student(name, gender, id);
            };
            for(String subject : subjects) {
                students[i].addGrade(subject, Math.abs(rand.nextInt()) % 4 + 2);
            }
            if(detailedReport)
                System.out.println(students[i]);
        }

        long startTime = new Date().getTime();
        switch (type) {
            case ("Insertion") -> InsertionSort.insertionSort(students);
            case ("Quick") -> QuickSort.quickSort(students);
            case ("Merge") -> MergeSort.mergeSort(students);
        }
        long endTime = new Date().getTime();
        if(detailedReport)
            System.out.println("\nРезультат сортировки:");
        return testArray(students, type.equals("Quick"), detailedReport) +
                "Время работы = " + (endTime - startTime) + '\n';
    }

    private static <T extends Comparable<? super T>> String testArray(T[] arr,
            boolean reverse, boolean detailedReport) {
        int deltaLen = arr.length - len;
        ArrayList<Integer> emptyLines = new ArrayList<>();
        ArrayList<Integer> inverseLines = new ArrayList<>();
        StringBuilder result = new StringBuilder("\n");

        for(int i = 0; i < arr.length; i++) {
            if(detailedReport)
                result.append("Элемент ").append(i).append(" = ");
            if(arr[i] == null) {
                emptyLines.add(i);
                if(detailedReport)
                    result.append("null;\n");
            }
            else
                if(detailedReport)
                    result.append(arr[i].toString()).append(";\n");
            if(i != 0 && arr[i] != null && arr[i - 1] != null) {
                if(reverse ? arr[i - 1].compareTo(arr[i]) < 0 :
                        arr[i - 1].compareTo(arr[i]) > 0) {
                    inverseLines.add(i);
                    if(detailedReport)
                        result.append("Элемент ").append(i)
                              .append(reverse ? "бол" : "мен")
                              .append("ьше предыдущего;\n");
                }
            }
        }
        if(deltaLen == 0 && emptyLines.isEmpty() && inverseLines.isEmpty())
            return result.deleteCharAt(0).toString() + "Всё впорядке!\n";

        if(deltaLen != 0)
            result.append("Полученный массив ")
                  .append(deltaLen < 0 ? "короче" : "длиннее")
                  .append(" исходного на ").append(deltaLen).append("!\n");

        if(!emptyLines.isEmpty()) {
            if(emptyLines.size() == len)
                result.append("Все элементы = null!");
            else
                if(emptyLines.size() == 1)
                    result.append("Элемент ").append(emptyLines.get(0))
                          .append(" = null!\n");
                else {
                    result.append("Следующие элементы = null: ")
                          .append(emptyLines.get(0));
                    for(int i = 1; i < emptyLines.size(); i++)
                        result.append(", ").append(emptyLines.get(i));
                    result.append("!\n");
                }
        }

        if(!inverseLines.isEmpty()) {
            if(inverseLines.size() == len - 1)
                result.append("Все элементы ").append(reverse ? "бол" : "мен")
                      .append("ьше предыдущих; массив обратно отсортирован!\n");
            else
                if(inverseLines.size() == 1)
                    result.append("Элемент ").append(inverseLines.get(0))
                          .append(reverse ? "бол" : "мен")
                          .append("ьше предыдущего!\n");
                else {
                    result.append("Следующие элементы ")
                          .append(reverse ? "бол" : "мен")
                          .append("ьше предыдущих: ")
                          .append(inverseLines.get(0));
                    for(int i = 1; i < inverseLines.size(); i++)
                        result.append(", ").append(inverseLines.get(i));
                    result.append("!\n");
                }
        }
        return result.deleteCharAt(0).toString();
    }


    private static class Student1 extends Student {
        Student1(String name, boolean gender, long id) {
            super(name, gender, id);
        }

        @Override
        public int compareTo(Student st) {
            return Long.compare(getID(), st.getID());
        }
    }

    private static class Student2 extends Student {
        Student2(String name, boolean gender, long id) {
            super(name, gender, id);
        }

        @Override
        public int compareTo(Student st) {
            int i = Double.compare(this.getAverageGrade(),
                    st.getAverageGrade());
            if(i == 0)
                return super.compareTo(st) * -1;
            return i;
        }
    }
}