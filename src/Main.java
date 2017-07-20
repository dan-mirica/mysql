import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class Main {
	public static void main(String[] args) throws SQLException {

		String url = "jdbc:mysql://localhost:3306/db1";
		String username = "root";
		String password = "root";

		Connection conn = connectToDB(url, username, password);

		String sql = "SELECT * FROM db1.employee";

		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			System.out.println("id: 		" + rs.getString(1));
			System.out.println("firstname: 	" + rs.getString(2));
			System.out.println("lastname: 	" + rs.getString(3));
		}
		
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		
		rs = stmt.executeQuery(sql);
		
		rs.moveToInsertRow();
		rs.updateString(2, "lorena");
		rs.updateString(3, "pugescu");
		rs.insertRow();

		conn.close();
	}

	private static Connection connectToDB(String url, String username, String password) {
		System.out.println("will connecting to database...");
		try {
			Connection conn = DriverManager.getConnection(url, username, password);
			System.out.println("Database connected!");
			return conn;
		} catch (SQLException e) {
			throw new IllegalStateException("Cannot connect the database!", e);
		}

	}
}
