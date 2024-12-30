import java.util.ArrayList;

public class CiutatsMonuments {
    public ArrayList<Ciutat> ciutats;
    public ArrayList<Monument> monuments;

    public CiutatsMonuments(){}
    public CiutatsMonuments(ArrayList<Ciutat> ciutats, ArrayList<Monument> monuments) {
        this.ciutats = ciutats;
        this.monuments = monuments;
    }

    public ArrayList<Ciutat> getCiutats() {
        return ciutats;
    }

    public void setCiutats(ArrayList<Ciutat> ciutats) {
        this.ciutats = ciutats;
    }

    public ArrayList<Monument> getMonuments() {
        return monuments;
    }

    public void setMonuments(ArrayList<Monument> monuments) {
        this.monuments = monuments;
    }
}
