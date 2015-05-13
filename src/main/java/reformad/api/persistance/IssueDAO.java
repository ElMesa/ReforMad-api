package reformad.api.persistance;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import reformad.api.model.Issue;

public class IssueDAO {

	private static Connection getConnection() throws URISyntaxException,
			SQLException {
		URI dbUri = new URI(System.getenv("DATABASE_URL"));

		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':'
				+ dbUri.getPort() + dbUri.getPath();

		return DriverManager.getConnection(dbUrl, username, password);
	}

	public static long create(Issue issue) {
		long newIssueId = -1;
		Connection con = null;
		PreparedStatement statement = null;
		ResultSet generatedKeys = null;
		
		String SQL_CREATE = "INSERT INTO issue (latitude, longitude, creation_date, \"user\", district, neighbourhood, address) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try {
			con = getConnection();
			// TODO Substitute with named parameters preparedStatement:
			// http://stackoverflow.com/questions/2309970/named-parameters-in-jdbc
			statement = con.prepareStatement(SQL_CREATE,
					Statement.RETURN_GENERATED_KEYS);
			statement.setFloat(1, issue.getLatitude());
			statement.setFloat(2, issue.getLongitude());
			statement.setTimestamp(3, new java.sql.Timestamp(issue.getCreation_date().getTime()));
			statement.setString(4, issue.getUser());
			statement.setString(5, issue.getDistrict());
			statement.setString(6, issue.getNeighbourhood());
			statement.setString(7, issue.getAddress());

			int affectedRows = statement.executeUpdate();
			if (affectedRows > 0) {
				generatedKeys = statement.getGeneratedKeys();
				if (generatedKeys.next()) {
					newIssueId = generatedKeys.getLong(1);
				}
			}

		} catch (SQLException | URISyntaxException e) {
			throw new RuntimeException(e.toString(), e);
		} finally {
			if (generatedKeys != null)
				try {
					generatedKeys.close();
				} catch (SQLException e) {
					throw new RuntimeException(e.toString(), e);
				}
			if (statement != null)
				try {
					statement.close();
				} catch (SQLException e) {
					throw new RuntimeException(e.toString(), e);
				}
			if (con != null)
				try {
					con.close();
				} catch (SQLException logOrIgnore) {
				}
		}

		return newIssueId;
	}

	public static List<Issue> getAll() {
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;
		ArrayList<Issue> issues = null;
		Issue issue = null;
		boolean isError = false;

		issues = new ArrayList<Issue>();
		String query = "SELECT * FROM issue;";

		try {
			con = getConnection();
			statement = con.createStatement();
			rs = statement.executeQuery(query);
			while (rs.next()) {

				issue = newIssueFromResultset(rs);

				if (issue != null) {
					issues.add(issue);
				} else {
					// logger.info("IssueDAO - Could not extract the issue from DB");
					isError = true;
					break;
				}
			}
		} catch (SQLException | URISyntaxException e) {
			isError = true;
			throw new RuntimeException(e.toString(), e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					isError = true;
					throw new RuntimeException(e.toString(), e);
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					isError = true;
					throw new RuntimeException(e.toString(), e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					isError = true;
					throw new RuntimeException(e.toString(), e);
				}
			}
		}
		
		return issues;
	}

	private static Issue newIssueFromResultset(ResultSet rs) throws SQLException {
		Issue issue = new Issue();

		issue.setId(rs.getLong("id"));
		issue.setLatitude(rs.getFloat("latitude"));
		issue.setLongitude(rs.getFloat("longitude"));

		return issue;
	}

}
