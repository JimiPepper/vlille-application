package iagl.project.jimipepper.vlille.util;

import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Récupère toutes les informations sur une station Vlille particulière contenues à l'adresse
 * http://vlille.fr/stations/xml-station.aspx?borne=ID_station_vlille_attendue
 *
 * @author Romain Philippon
 */
public class XMLStation {

    /**
     * Récupère les informations d'une station Vlille
     *
     * @return une Map contenant l'ensemble des informations d'une station Vlille particulière
     */
    public static Map<String, String> get(int id) {
        Map<String, String> station = null;

        try {
            // Connexion
            URL url = new URL("http://vlille.fr/stations/xml-station.aspx?borne="+ id);
            URLConnection connection = url.openConnection();

            // Mis en place du parseur
            SAXBuilder sxb = new SAXBuilder();
            Element root = sxb.build(connection.getInputStream()).getRootElement();
            List<Element> stationXML = root.getChildren();

            // Parsage du XML récupéré
            station = new HashMap<String, String>();
            Iterator i = stationXML.iterator();
            while (i.hasNext()) {
                Element currentNode = (Element) i.next();
                station.put(currentNode.getName(), currentNode.getValue()); // chaque clé == le nom du noeud XML
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
           e.printStackTrace();
        }

        return station;
    }
}
