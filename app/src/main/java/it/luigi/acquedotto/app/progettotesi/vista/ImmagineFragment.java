package it.luigi.acquedotto.app.progettotesi.vista;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import it.luigi.acquedotto.app.progettotesi.Applicazione;
import it.luigi.acquedotto.app.progettotesi.Costanti;
import it.luigi.acquedotto.app.progettotesi.R;
import it.luigi.acquedotto.app.progettotesi.modello.Attivita;
import it.luigi.acquedotto.app.progettotesi.modello.Modello;


public class ImmagineFragment extends Fragment {
    public static final String TAG = ImmagineFragment.class.getName();

    private ImageView imageFull;
    private ImageButton bottoneElimina;

    @Override
    public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_immagine, container, false);
        imageFull = (ImageView) view.findViewById(R.id.imageFull);
        Modello modello = Applicazione.getIstance().getModello();
        Attivita attivita = (Attivita) modello.getBean(Costanti.ATTIVITA);
        imageFull.setImageURI(Uri.parse(attivita.getIdImmagine()));
        bottoneElimina = (ImageButton) view.findViewById(R.id.bottoneElimina);
        bottoneElimina.setOnClickListener(Applicazione.getIstance().getControlloImmagine().getAzioneElimina());
        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public ImageView getImageFull() {
        return imageFull;
    }
}
