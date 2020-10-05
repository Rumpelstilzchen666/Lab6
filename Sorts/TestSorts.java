package Sorts;

import java.util.Random;

public class TestSorts {
    private static final String[] maleNames =
            {"Николай", "Алексей", "Ярослав", "Дмитрий", "Олег"};
    private static final String[] femaleNames =
            {"Милена", "Диана", "Оксана", "Карина", "Ольга"};
    private static final String[] subjects =
            {"ООП", "Физ-ра", "Мат. анализ", "Java", "СиАОД"};
    private static Random rand;

    public static void main(String[] args) {
        System.out.println(TestInsertionSort(true));
        System.out.println(TestQuickSort(true));
    }

    public static String TestInsertionSort(boolean print) {
        int len = 10;
        rand = new Random();
        Student1[] students = new Student1[len];
        boolean gender;
        if(print)
            System.out.println(
                    "Алгоритм сортировки вставками\nИсходный массив:");
        for(int i = 0; i < len; i++) {
            gender = rand.nextBoolean();
            students[i] = new Student1(
                    (gender ? maleNames : femaleNames)[Math.abs(rand.nextInt() %
                            (gender ? maleNames : femaleNames).length)], gender,
                    Math.abs(rand.nextInt()));
            if(print)
                System.out.println(students[i]);
        }

        InsertionSort.InsertionSort(students);
        if(print)
            System.out.println("\nРезультат сортировки:");
        return testArray(students, len, false);
    }

    public static String TestQuickSort(boolean print) {
        int len = 10;
        rand = new Random();
        Student2[] students = new Student2[len];
        boolean gender;
        if(print)
            System.out.println(
                    "Алгоритм быстрой сортировки (Quick Sort)\nИсходный массив:");
        for(int i = 0; i < len; i++) {
            gender = rand.nextBoolean();
            students[i] = new Student2(
                    (gender ? maleNames : femaleNames)[Math.abs(rand.nextInt() %
                            (gender ? maleNames : femaleNames).length)], gender,
                    Math.abs(rand.nextInt()));
            for(String subject : subjects) {
                students[i].addGrade(subject, Math.abs(rand.nextInt()) % 4 + 2);
            }
            if(print)
                System.out.println(students[i]);
        }

        QuickSort.QuickSort(students, 0, len - 1);
        if(print)
            System.out.println("\nРезультат сортировки:");
        return testArray(students, len, true);
    }

    private static <T extends Comparable<? super T>> String testArray(T[] arr,
            int len, boolean reverse) {
        StringBuilder result = new StringBuilder("\n");
        if(arr.length < len) {
            result.append("Полученный массив короче исходного!\n");
        } else
            if(arr.length > len) {
                result.append("Полученный массив длиннее исходного!\n");
            }

        for(int i = 0; i < arr.length; i++) {
            result.append("Строка ").append(i).append(" = ");
            if(arr[i] == null) {
                result.append("null;\n");
            } else {
                result.append(arr[i].toString()).append(";\n");
            }
            if(i != 0 && arr[i] != null && arr[i - 1] != null) {
                if(reverse ? arr[i - 1].compareTo(arr[i]) < 0 :
                        arr[i - 1].compareTo(arr[i]) > 0) {
                    result.append("Строка ").append(i).append(
                            reverse ? " больше" : " меньше").append(
                            " предыдущей!\n");
                }
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