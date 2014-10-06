package iagl.project.jimipepper.vlille.ihm;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;

import iagl.project.jimipepper.vlille.R;

/**
 * Représente la boite de dialogue affichant le détail des informations d'une station Vlille
 *
 * @author Romain Philippon
 */
public class DetailStationDialog extends Dialog implements OnClickListener {

    public DetailStationDialog(Context context) {
        super(context);
    }

    public DetailStationDialog(Context context, String stationName, String stationAddress, int availableBikes, int getAvailableAttachs, boolean status) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setCancelable(false);

        /* CHARGMENT DE LA BONNE VUE XML */
        if(!status) { // status == 0 -> la station de vélos est dispo
            this.setContentView(R.layout.station_available_dialog);
            TextView availableBikesView = (TextView)this.findViewById(R.id.bikes);
            TextView availableAttachsView = (TextView)this.findViewById(R.id.attachs);
            TextView stationAddressView = (TextView)this.findViewById(R.id.station_address);

            StringBuilder s = null;

            // remplace avec la bonne adresse
            s = new StringBuilder(context.getString(R.string.detailDialogAddressInfo));
            s.append(stationAddress);
            stationAddressView.setText(s.toString().toLowerCase());

            // remplace avec le bon nombre de vélos
            s = new StringBuilder(context.getString(R.string.detailDialogBikeInfo));
            s.append(availableBikes);
            availableBikesView.setText(s);

            // remplace avec le bon nombre d'emplacements de rangements dispo
            s = new StringBuilder(context.getString(R.string.detailDialogAttachInfo));
            s.append(getAvailableAttachs);
            availableAttachsView.setText(s);
        }
        else
            this.setContentView(R.layout.station_unavailable_dialog);

        ((TextView)this.findViewById(R.id.titleDetailDialog)).setText(stationName);

        /* AJOUT DU LISTENER */
        this.findViewById(R.id.close_dialog).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.dismiss();
    }
}