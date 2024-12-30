import java.util.List;

public class RssFeed {
    private String titol;
    private String enllaç;
    private String descripcio;
    private String dataModificacio;
    private List<RssItem> items;

    public RssFeed(){}

    public RssFeed(String titol, String enllaç, String descripcio, List<RssItem> items) {
        this.titol = titol;
        this.enllaç = enllaç;
        this.descripcio = descripcio;
        this.items = items;
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

    public List<RssItem> getItems() {
        return items;
    }

    public void setItems(List<RssItem> items) {
        this.items = items;
    }
}