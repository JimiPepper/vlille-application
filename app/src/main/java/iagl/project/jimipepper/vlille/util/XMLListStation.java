package iagl.project.jimipepper.vlille.util;

import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.jdom2.input.sax.XMLReaders;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Récupère toutes les informations sur les stations Vlille contenues à l'adresse
 * http://vlille.fr/stations/xml-stations.aspx
 *
 * @author Romain Philippon
 */
public class XMLListStation {

    /**
     * Récupère les informations de toutes les stations Vlille
     *
     * @return une liste de Map contenant pour chaque élément l'ensemble des informations d'une station Vlille particulière
     */
    public static List<Map<String, String>> getStations() {
        List<Map<String, String>> stationList = new LinkedList<Map<String, String>>();
        Map<String, String> station;

        try {
            // Connexion
            URL url = new URL("http://vlille.fr/stations/xml-stations.aspx");
            URLConnection connection = url.openConnection();

            // Mis en place du parseur
            SAXBuilder sxb = new SAXBuilder();
            Element root = sxb.build(connection.getInputStream()).getRootElement();
            List listXMLStation = root.getChildren("marker");

            // Parsage du XML récupéré
            Iterator i = listXMLStation.iterator();
            while (i.hasNext()) {
                Element currentNode = (Element) i.next();
                station = new HashMap<String, String>();
                station.put("id", currentNode.getAttributeValue("id"));
                station.put("name", currentNode.getAttributeValue("name"));
                station.put("latitude", currentNode.getAttributeValue("lat"));
                station.put("longitude", currentNode.getAttributeValue("lng"));

                stationList.add(station);
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (JDOMException e) {
            e.printStackTrace();
        }

        return stationList;
    }
}
