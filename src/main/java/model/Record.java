package model;

public class Record {
    public int id;
    public String user_id;
    public int xp;

    public Record(String user_id, int xp) {
        this.user_id = user_id;
        this.xp = xp;
    }

    public Record(int id, String user_id, int xp) {
        this.id = id;
        this.user_id = user_id;
        this.xp = xp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }
}
