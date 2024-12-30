public class RssItem {
    private String titol;
    private String enllaç;
    private String descripcio;
    private String dataModificacio;


    // constructor, getters y setters


    public RssItem(){}
    public RssItem(String titol, String enllaç, String descripcio) {
        this.titol = titol;
        this.enllaç = enllaç;
        this.descripcio = descripcio;
    }

    public String getTitol() {
        return titol;
    }

    public void setTitol(String titol) {
        this.titol = titol;
    }

    public String getEnllaç() {
        return enllaç;
    }

    public void setEnllaç(String enllaç) {
        this.enllaç = enllaç;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public String getDataModificacio() {
        return dataModificacio;
    }

    public void setDataModificacio(String dataModificacio) {
        this.dataModificacio = dataModificacio;
    }
}