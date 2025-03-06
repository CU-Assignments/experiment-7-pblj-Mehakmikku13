import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ProductCRUD {
    private static final String URL = "jdbc:mysql://localhost:3306/ProductDB";
    private static final String USER = "root"; // Change if needed
    private static final String PASSWORD = "root"; // Set your MySQL password

    public static void main(String[] args) throws SQLException {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n1. Add  \n2. View  \n3. Update  \n4. Delete  \n5. Exit");
                switch (scanner.nextInt()) {
                    case 1 -> addProduct(conn, scanner);
                    case 2 -> viewProducts(conn);
                    case 3 -> updateProduct(conn, scanner);
                    case 4 -> deleteProduct(conn, scanner);
                    case 5 -> System.exit(0);
                }
            }
        }
    }

    private static void addProduct(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Name: "); scanner.nextLine();
        String name = scanner.nextLine();
        System.out.print("Price: "); double price = scanner.nextDouble();
        System.out.print("Quantity: "); int quantity = scanner.nextInt();

        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO Product (ProductName, Price, Quantity) VALUES (?, ?, ?)")) {
            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setInt(3, quantity);
            stmt.executeUpdate();
            System.out.println("Product added!");
        }
    }

    private static void viewProducts(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM Product")) {
            while (rs.next()) System.out.println(rs.getInt(1) + " | " + rs.getString(2) + " | $" + rs.getDouble(3) + " | " + rs.getInt(4));
        }
    }

    private static void updateProduct(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("ID to update: "); int id = scanner.nextInt();
        System.out.print("New Price: "); double price = scanner.nextDouble();
        System.out.print("New Quantity: "); int quantity = scanner.nextInt();

        try (PreparedStatement stmt = conn.prepareStatement("UPDATE Product SET Price=?, Quantity=? WHERE ProductID=?")) {
            stmt.setDouble(1, price);
            stmt.setInt(2, quantity);
            stmt.setInt(3, id);
            System.out.println(stmt.executeUpdate() > 0 ? "Updated!" : "Not found!");
        }
    }

    private static void deleteProduct(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("ID to delete: "); int id = scanner.nextInt();
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Product WHERE ProductID=?")) {
            stmt.setInt(1, id);
            System.out.println(stmt.executeUpdate() > 0 ? "Deleted!" : "Not found!");
        }
    }
}
