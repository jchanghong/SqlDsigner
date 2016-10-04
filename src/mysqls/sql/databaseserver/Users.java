package mysqls.sql.databaseserver;

public class Users {
    String servername;
    String name;
    String pas;

    public String getServername() {
        return servername;
    }

    public Users(String servername, String name, String pas) {
        super();
        this.servername = servername;
        this.name = name;
        this.pas = pas;
    }

    public void setServername(String servername) {
        this.servername = servername;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPas() {
        return pas;
    }

    public void setPas(String pas) {
        this.pas = pas;
    }


}
