package iagl.project.jimipepper.vlille.task;

import android.os.AsyncTask;

import java.util.Map;

import iagl.project.jimipepper.vlille.util.XMLStation;

/**
 * Lance dans un thread à part la récupération des données XML d'une station Vlille particulière
 *
 * @author Romain Philippon
 */
public class LoadStationTask extends AsyncTask<Void, Integer, Map<String, String>> {
    private int stationID;

    public LoadStationTask(int id) {
        super();
        this.stationID = id;
    }

    @Override
    protected Map<String, String> doInBackground(Void... params) {
        return XMLStation.get(this.stationID);
    }

    @Override
    protected void onPostExecute(Map<String, String> result) {
        super.onPostExecute(result);
    }
}
