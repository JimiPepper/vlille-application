package iagl.project.jimipepper.vlille.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import iagl.project.jimipepper.vlille.R;
import iagl.project.jimipepper.vlille.ihm.DetailStationDialog;
import iagl.project.jimipepper.vlille.task.LoadListStationTask;
import iagl.project.jimipepper.vlille.task.LoadStationTask;

/**
 * Représente le fragment qui affiche la liste des stations vlilles disponibles
 *
 * @author Romain Philippon
 */
public class StationListFragment extends Fragment implements OnItemClickListener {
    private OnStationItemListener listener;

    public StationListFragment() {

    }

    public static StationListFragment newInstance() {
        return new StationListFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_station_list, container, false);

        /* AJOUT DES STATIONS DANS LA LISTE */
        ListView list = (ListView) view.findViewById(R.id.station_listview);

        SimpleAdapter adapter = null;

        try {
            adapter = new SimpleAdapter(
                    getActivity(),
                    new LoadListStationTask().execute().get(),
                    R.layout.item_list_station_layout,
                    new String[]{"name"},
                    new int[]{R.id.titleDetailDialog}
            );
        }
        catch (InterruptedException ie) {
            ie.printStackTrace();
        }
        catch (ExecutionException ee) {
           ee.printStackTrace();
        }

        list.setOnItemClickListener(this);
        list.setAdapter(adapter);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (OnStationItemListener)activity;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnStationItemListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    /* LISTENER */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Déclenche l'affichage d'une fenêtre de dialogue renseignant l'utilisateur sur l'état d'une station vlille

        HashMap<String, String> map = (HashMap<String, String>) parent.getItemAtPosition(position);

        try {
            Map<String, String> station = new LoadStationTask(Integer.parseInt(map.get("id"))).execute().get();

            DetailStationDialog dialog = new DetailStationDialog(
                    getActivity(),
                    map.get("name"),
                    station.get("adress"),
                    Integer.parseInt(station.get("bikes")),
                    Integer.parseInt(station.get("attachs")),
                    Boolean.getBoolean(station.get("status"))
            );

            dialog.show();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public interface OnStationItemListener {

    }
}