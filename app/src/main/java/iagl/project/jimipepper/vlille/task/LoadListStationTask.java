package iagl.project.jimipepper.vlille.task;

import android.os.AsyncTask;

import java.util.List;
import java.util.Map;

import iagl.project.jimipepper.vlille.util.XMLListStation;

/**
 * Lance dans un thread dédié la récupération des données XML concernant toutes les stations
 * de vélos Vlille
 *
 * @author Romain Philippon
 */
public class LoadListStationTask extends AsyncTask<Void, Integer, List<Map<String, String>>> {
    @Override
    protected  List<Map<String, String>> doInBackground(Void... params) {
        return XMLListStation.getStations();
    }

    @Override
    protected void onPostExecute(List<Map<String, String>> result) {
        super.onPostExecute(result);
    }
}