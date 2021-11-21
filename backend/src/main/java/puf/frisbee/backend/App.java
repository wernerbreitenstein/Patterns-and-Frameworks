package puf.frisbee.backend;

import java.sql.*;

public class App {
    public static void main(String[] args) {

        try (Connection c = DatabaseManager.getConnection()) {

            System.out.println("Deleting table '" + DatabaseManager.TeamEntity.TABLE_NAME + "' if exists.");
            try (Statement statement = c.createStatement()) {
                statement.execute(DatabaseManager.TeamEntity.DELETE_TABLE);
            }

            System.out.println("Creating table '" + DatabaseManager.TeamEntity.TABLE_NAME + "' if not exists.");
            try (Statement statement = c.createStatement()) {
                statement.execute(DatabaseManager.TeamEntity.CREATE_TABLE);
            }

            Team team = new Team("Bonny&Clyde", 1, 0);

            System.out.println("Inserting a team...");
            DatabaseManager.TeamEntity.insert(c, team);

            System.out.println("Selecting all teams...");
            String sqlGet = "SELECT * FROM " + DatabaseManager.TeamEntity.TABLE_NAME;
            try (Statement stmt = c.createStatement()) {
                ResultSet teams = stmt.executeQuery(sqlGet);
                while (teams.next()) {
                    String name = teams.getString(DatabaseManager.TeamEntity.COLUMN_NAME);
                    int level = teams.getInt(DatabaseManager.TeamEntity.COLUMN_LEVEL);
                    int score = teams.getInt(DatabaseManager.TeamEntity.COLUMN_SCORE);
                    System.out.println("Team " + name + " - " + level + " - " + score);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}