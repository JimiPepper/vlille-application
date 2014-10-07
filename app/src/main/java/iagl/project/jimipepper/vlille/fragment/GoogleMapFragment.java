package iagl.project.jimipepper.vlille.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import iagl.project.jimipepper.vlille.R;
import iagl.project.jimipepper.vlille.task.LoadListStationTask;
import iagl.project.jimipepper.vlille.task.LoadStationTask;

/**
 * Représente un fragment de l'application comportant une carte Google Map
 * marquée par toutes les stations Vlille disponibles
 *
 * @author Philippon Romain
 */
public class GoogleMapFragment extends Fragment {
    private MapView vmap;

    public GoogleMapFragment() {

    }

    public static GoogleMapFragment newInstance() {
        return new GoogleMapFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        this.vmap = (MapView) view.findViewById(R.id.map);
        this.vmap.onCreate(savedInstanceState);
        MapsInitializer.initialize(this.getActivity());

        this.drawBikeStationOnMap();

        return view;
    }
    @Override
    public void onResume() {
        this.vmap.onResume();
        super.onResume();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.vmap.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        this.vmap.onLowMemory();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * Dessine un marker avec les informations d'une station Vlille sur la GoogleMap passée en paramètre
     *
     * @param map
     * @param station
     */
    private void drawMarker(GoogleMap map, Station station) {
        String label = station.getNbBikes() +" "+ getString(R.string.bikeMarkerGoogleMap) +" & "+ station.getNbAttachs() +" "+ getString(R.string.attachMarkGoogleMap);

        map.addMarker(
                new MarkerOptions()
                    .title(station.getName())
                    .snippet(label)
                    .position(station.getPosition())

        );
    }

    /**
     * Dessine tous les markers sur la GoogleMap du fragment
     */
    private void drawBikeStationOnMap() {
        GoogleMap gmap = this.vmap.getMap();

        try {
            List<Map<String, String>> stationList = new LoadListStationTask().execute().get();

            for(Map<String, String> station : stationList) {
                Map<String, String> stationDetails = new LoadStationTask(Integer.parseInt(station.get("id"))).execute().get();

                drawMarker(
                        gmap,
                        new Station(
                                station.get("name"),
                                Integer.parseInt(stationDetails.get("bikes")),
                                Integer.parseInt(stationDetails.get("attachs")),
                                Double.valueOf(station.get("latitude")),
                                Double.valueOf(station.get("longitude"))
                        )
                );
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Symbolise et rassemble toutes les informations essentielles d'une station de Vlille
     * N'a d'utilité que pour ce fragment afin de faciliter le dessin des markers sur la
     * GoogleMap
     */
    private static class Station {
        private String name;
        private int nbBikes;
        private int nbAttachs;
        private LatLng position;

        public Station(String name, int nbBikes, int nbAttachs, double latitude, double longitude) {
            this.name = name;
            this.nbBikes = nbBikes;
            this.nbAttachs = nbAttachs;
            this.position = new LatLng(latitude, longitude);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNbBikes() {
            return nbBikes;
        }

        public void setNbBikes(int nbBikes) {
            this.nbBikes = nbBikes;
        }

        public int getNbAttachs() {
            return nbAttachs;
        }

        public void setNbAttachs(int nbAttachs) {
            this.nbAttachs = nbAttachs;
        }

        public LatLng getPosition() {
            return position;
        }

        public void setPosition(LatLng position) {
            this.position = position;
        }
    }
}
