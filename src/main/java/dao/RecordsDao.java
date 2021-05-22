package dao;

import db.DB;
import model.Record;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecordsDao implements Dao<Record> {
    private ArrayList<Record> records = new ArrayList<>();

    public RecordsDao() {
        try {
            Connection con = DB.getInstance();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM xp");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " " + rs.getString("user_id") + " " + rs.getInt("xp"));
                records.add(new Record(rs.getInt("id"), rs.getString("user_id"), rs.getInt("xp")));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Record get(String id) {
        for (Record r : records) {
            if (r.getUser_id().equals(id))
                return r;
        }
        return null;
    }

    @Override
    public List<Record> getAll(int n) {
        ArrayList<Record> toReturn = new ArrayList<>();
        try {
            Connection con = DB.getInstance();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM xp ORDER BY xp DESC LIMIT " + n);
            while (rs.next()) {
                toReturn.add(new Record(rs.getInt("id"), rs.getString("user_id"), rs.getInt("xp")));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return toReturn;
    }

    @Override
    public void save(Record record) {
        try {
            Connection conn = DB.getInstance();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO xp(user_id,xp) VALUES (?,?)");
            stmt.setString(1, record.getUser_id());
            stmt.setInt(2, record.getXp());
            stmt.executeUpdate();
            records.add(record);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Record record, String[] params) {
        try {
            Connection conn = DB.getInstance();
            PreparedStatement stmt = conn.prepareStatement("UPDATE xp SET xp = ? WHERE user_id = ?");
            stmt.setInt(1, Integer.parseInt(params[0]));
            stmt.setString(2, record.getUser_id());
            stmt.executeUpdate();
            records.add(record);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Record record) {

    }
}
