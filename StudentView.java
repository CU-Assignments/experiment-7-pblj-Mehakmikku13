import java.sql.SQLException;
import java.util.Scanner;

public class StudentView {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in); StudentController controller = new StudentController()) {
            while (true) {
                System.out.println("\n1. Add  \n2. View  \n3. Update  \n4. Delete  \n5. Exit");
                switch (scanner.nextInt()) {
                    case 1 -> {
                        System.out.print("ID: "); int id = scanner.nextInt();
                        System.out.print("Name: "); scanner.nextLine(); String name = scanner.nextLine();
                        System.out.print("Dept: "); String dept = scanner.nextLine();
                        System.out.print("Marks: "); double marks = scanner.nextDouble();
                        controller.addStudent(new Student(id, name, dept, marks));
                    }
                    case 2 -> controller.getAllStudents().forEach(s -> 
                        System.out.println(s.getId() + " | " + s.getName() + " | " + s.getDepartment() + " | " + s.getMarks()));
                    case 3 -> {
                        System.out.print("ID to update: "); int id = scanner.nextInt();
                        System.out.print("New Marks: "); double marks = scanner.nextDouble();
                        controller.updateStudent(id, marks);
                    }
                    case 4 -> {
                        System.out.print("ID to delete: "); int id = scanner.nextInt();
                        controller.deleteStudent(id);
                    }
                    case 5 -> System.exit(0);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
