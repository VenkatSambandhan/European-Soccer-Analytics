import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlayerDao {
	public static List<Player> getTypes(String type) throws SQLException, ClassNotFoundException {
		String attribute = type;
		String sql = "select distinct(Player.player_name), Player.weight, Player.height from Player,Player_Attributes where Player.player_api_id == Player_Attributes.player_api_id ORDER By Player_Attributes."
				+ attribute + " DESC Limit 10";
		Class.forName("org.sqlite.JDBC");
		String url = "jdbc:sqlite:C://Users/Abhi/Downloads/soccer/database.sqlite";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		List<Player> list = new ArrayList();

		System.out.println("Player Name || " + "Player Weight || " + "Player Height || ");
		// System.out.println("Player Height");
		// System.out.println("Player Height");

		while (rs.next()) {
			Player player = new Player();
			player.setName(rs.getString(1));
			// System.out.println("rs.getString(1)"+rs.getString(1));
			player.setHeight(rs.getString(2));
			// System.out.println("rs.getString(2)"+rs.getString(2));
			player.setWeight(rs.getString(3));
			// System.out.println("rs.getString(3)"+rs.getString(3));
			System.out.println(rs.getString(1) + " || " + rs.getString(2) + " || " + rs.getString(3));
			list.add(player);

		}
		rs.close();
		conn.close();
		return list;

	}

	public static List<Player> getTeams(String country) throws SQLException, ClassNotFoundException {
		// xString attribute=type;
		String sql = "Select distinct(Team.team_long_name) from Match,Team,Country where Match.home_team_api_id=Team.team_api_id and Match.country_id= (select id from Country where name =='"
				+ country + "')";
		Class.forName("org.sqlite.JDBC");
		String url = "jdbc:sqlite:C://Users/Abhi/Downloads/soccer/database.sqlite";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		List<Player> list = new ArrayList();

		// System.out.println("Teams ");
		// System.out.println("Player Height");
		// System.out.println("Player Height");

		while (rs.next()) {
			System.out.println(rs.getString(1));

		}
		rs.close();
		conn.close();
		return list;

	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		PlayerDao dao = new PlayerDao();
		do {

			System.out.println("Choose one of the options to know about data you are interested in");

			System.out.println("1. Teams in the league");
			System.out.println("2. Top 10 players based on Attribute");
			System.out.println("3. End");
			System.out.println();
			System.out.println();
			Scanner in = new Scanner(System.in);
			int choice = in.nextInt();

			if (choice == 3) {
				System.out.println("Exiting application");
				break;
			}

			if (choice == 1) {
				do {
					System.out.println("Choose from one of the following countries");
					System.out.println("1.England");
					System.out.println("2.Spain");
					System.out.println("3.Portugal");
					System.out.println("4.Germany");
					System.out.println("5.France");
					System.out.println("6.Back to Main Menu");
					System.out.println();
					Scanner in0 = new Scanner(System.in);
					int num = in0.nextInt();

					System.out.println();
					System.out.println("Top Teams in that country");
					System.out.println("----------------------------");
					if (num == 1) {
						PlayerDao.getTeams("England");

					} else if (num == 2) {
						PlayerDao.getTeams("Spain");

					}

					else if (num == 3) {
						PlayerDao.getTeams("Portugal");
					} else if (num == 4) {
						PlayerDao.getTeams("Germany");
					} else if (num == 5) {
						PlayerDao.getTeams("France");
					}

					else if (num == 6) {
						System.out.println("Back to Main Menu");
						break;
					} else {
						System.out.println("Wrong input");
					}
					// dao.getTeams("England");
					System.out.println("----------------------------");
					System.out.println("");
				} while (true);
			}

			if (choice == 2) {
				do {
					System.out.println("Choose from one of the following attributes");
					// System.out.println("Top players based on Attribute");
					System.out.println("1.Overall");
					System.out.println("2.Attacking");
					System.out.println("3.Free Kick accuracy");
					System.out.println("4.Finishing accuracy");
					System.out.println("5.Speed");
					System.out.println("6.Ball Control");
					System.out.println("7.Stamina");
					System.out.println("8.Dribbling");

					System.out.println("9.Back to Main Menu");
					System.out.println();
					Scanner in1 = new Scanner(System.in);
					int num = in1.nextInt();
					System.out.println();
					System.out.println("Top players based on choosen attribute are");
					System.out.println("----------------------------");
					if (num == 1) {
						PlayerDao.getTypes("overall_rating");

					} else if (num == 2) {
						PlayerDao.getTypes("attacking_work_rate");

					}

					else if (num == 3) {
						PlayerDao.getTypes("free_kick_accuracy");
					} else if (num == 4) {
						PlayerDao.getTypes("finishing");
					} else if (num == 5) {
						PlayerDao.getTypes("sprint_speed");
					} else if (num == 6) {
						PlayerDao.getTypes("ball_control");
					} else if (num == 7) {
						PlayerDao.getTypes("stamina");
					} else if (num == 8) {
						PlayerDao.getTypes("dribbling");
					} else if (num == 9) {
						System.out.println("Back to Main Menu");
						break;
					} else {
						System.out.println("Wrong input");
					}
					System.out.println("----------------------------");
					System.out.println();
				} while (true);
			}
		} while (true);
	}

}
