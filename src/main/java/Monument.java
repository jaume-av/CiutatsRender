public class Monument{
    public int id;
    public String nom;
    public String historia_breu;
    public String imatge;
    public int ciutat_id;

    public Monument(){}

    public Monument(int id, String nom, String historia_breu, String imatge, int ciutat_id) {
        this.id = id;
        this.nom = nom;
        this.historia_breu = historia_breu;
        this.imatge = imatge;
        this.ciutat_id = ciutat_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getHistoria_breu() {
        return historia_breu;
    }

    public void setHistoria_breu(String historia_breu) {
        this.historia_breu = historia_breu;
    }

    public String getImatge() {
        return imatge;
    }

    public void setImatge(String imatge) {
        this.imatge = imatge;
    }

    public int getCiutat_id() {
        return ciutat_id;
    }

    public void setCiutat_id(int ciutat_id) {
        this.ciutat_id = ciutat_id;
    }

    @Override
    public String toString() {
        return "Monument{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", historia_breu='" + historia_breu + '\'' +
                ", imatge='" + imatge + '\'' +
                ", ciutat_id=" + ciutat_id +
                '}';
    }
}