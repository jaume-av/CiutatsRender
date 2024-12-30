
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


public class Main {

    public static void main(String[] args) {

        try {


            File dadesJson = new File("src/main/resources/json/dades.json");
            File schemaJson = new File("src/main/resources/json/schema.json");



            //  Mapejem el JSON de dades

            ObjectMapper objectMapper = new ObjectMapper();
            CiutatsMonuments ciuMon = objectMapper.readValue(dadesJson, CiutatsMonuments.class);



            // ********* Validem dades.json contra schema.json

            if (jsonValid(objectMapper, dadesJson, schemaJson)) {
                System.out.println("El JSON és vàlid segons l'schema.");
            } else {
                System.out.println("El JSON no és vàlid segons l'schema.");
            }



            // *************** CONFIG INI ***************/

           //Necessitem un fileReader per llegir config.ini i Creem l'objecte Properties (java.util.Properties;)


            // Carreguem les propietats
            Properties config = new Properties();
            config.load(new FileReader("src/main/resources/json/config.ini"));

            //Accedim als valors de config.ini

            String configNom = config.getProperty("nom");
            String configDescripcio = config.getProperty("descripcio");




            //  ************  THYMELEAF ****************

            // Configuració del Resolver de les plantilles i accents
            ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
            templateResolver.setPrefix("templates/");
            templateResolver.setSuffix(".html");
            templateResolver.setCharacterEncoding("UTF-8");


            // Configuració del motor de plantilles
            TemplateEngine templateEngine = new TemplateEngine();
            templateEngine.setTemplateResolver(templateResolver);


            // Creació del context amb les dades de CIUTAT
            Context context = new Context();


            //afegim al contexte les variables de l'arxiu config.ini
            context.setVariable("titol", configNom);
            context.setVariable("descripcio", configDescripcio);

            //Afegim al contexte la llista de ciutats
            context.setVariable("ciutats", ciuMon.getCiutats());

            // Processament de la plantilla
            String contingutHTMLCiutat = templateEngine.process("plantillaCiutat", context);

           // Creem l'arxiu Index.HTML
            escriuHTML(contingutHTMLCiutat,"src/main/resources/fitxersWeb/index.html");


            // Processem els Monuments agrupant-los per ciutats

            for (Ciutat c : ciuMon.getCiutats()) {

                // Creen el contexte per a cada grup ciutat-monuments
                Context contextMonument = new Context();
                // Afegim al contexte el nom de la ciutat
                contextMonument.setVariable("ciutat", c);

                // Posem els monuments cada ciutat en un LIST

                List<Monument> agrupa = new ArrayList<>();
                for (Monument m: ciuMon.getMonuments()){

                    // filtrem per el camp IDE de Ciutat i Monument
                    if(c.getId()==m.getCiutat_id())
                        agrupa.add(m);
                    contextMonument.setVariable("monuments",agrupa);

                }
                // Processem el contexte a la plantilla
                String contingutHTMLMonument = templateEngine.process("plantillaMonument", contextMonument);

                // Creem una fitxer HTML per conjunt de monuments de cada ciutat
                String ciutatHTML ="src/main/resources/fitxersWeb/" + c.getNom() + ".html";
                escriuHTML(contingutHTMLMonument, ciutatHTML);


            } // For ciutats

            // *************  RSS Amb Jackon ****************


            // ************  Posem les dades als objectes

            RssFeed rssFeed = new RssFeed();
            rssFeed.setTitol(" RSS de Ciutats i Monuments");
            rssFeed.setEnllaç("src/main/resources/fitxersWeb/index.html");
            rssFeed.setDescripcio("Feed que mostra Ciutats i Monuments");

            // Obtén la fecha de modificación actual en formato RFC 822
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
            String dataModificacio = dateFormat.format(new Date());

            rssFeed.setDataModificacio(dataModificacio);

            // Per a que <items no es duplique creem directament la llista RssItem com part de RssFeed
            rssFeed.setItems(new ArrayList<>());

            //List<RssItem> rssItems = new ArrayList<>();

            for (Ciutat ciutat : ciuMon.getCiutats()) {
                RssItem rssItem = new RssItem();
                rssItem.setTitol(ciutat.getNom());
                rssItem.setEnllaç("../fitxersWeb/" + ciutat.getNom() + ".html");
                rssItem.setDescripcio(ciutat.getDescripcio());
                rssItem.setDataModificacio(dataModificacio);

                // rssItems.add(rssItem);
                rssFeed.getItems().add(rssItem);
            }

          //  rssFeed.setItems(rssItems); Esta llista separada no fa falta, ho assignem directament al for

            ObjectMapper rssMapper = new XmlMapper();
            String rssXml = rssMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rssFeed);


            escriuHTML(rssXml,"src/main/resources/fitxersWeb/feed.xml");



        } catch (Exception e) {
            e.printStackTrace();
        }


    } // main


    public static void escriuHTML(String HTML, String nomFitxer){

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomFitxer))) {
            // Escriure el contingut al fitxer
            writer.write(HTML);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    // Mètode per validar JSON contra JSON Schema
    private static boolean jsonValid(ObjectMapper objectMapper, File jsonDataFile, File schemaFile) {
        try {

            // Llegim el JSON i l'esquema com a nodes JSON

            JsonNode jsonData = objectMapper.readTree(jsonDataFile);
            JsonNode schemaData = objectMapper.readTree(schemaFile);

            // Configurar la fábrica de JsonSchema amb la versió per defecte
            JsonSchemaFactory factory = JsonSchemaFactory.byDefault();

            // Obtenim l'objecte JsonSchema a partir de l'esquema JSON
            JsonSchema schema = factory.getJsonSchema(schemaData);

            // Validem el JSON contra l'esquema i obtenim el "report" de validació
            ProcessingReport report = schema.validate(jsonData);

            // Retornar si la validació ha estat un èxit (true, false)

           return report.isSuccess();


        } catch (IOException | ProcessingException e) {
            e.printStackTrace();
            return false;
        }
    }

    } // classe




