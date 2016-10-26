package it.luigi.acquedotto.app.progettotesi.vista;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import it.luigi.acquedotto.app.progettotesi.Applicazione;
import it.luigi.acquedotto.app.progettotesi.R;

public class LoginFragment extends Fragment {
    private final String TAG = LoginFragment.class.getName();

    private EditText testoUsername;
    private EditText testoPassword;
    private Button bottoneLogin;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Creazione del Fragment caricando le informazioni dal file xml
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        //Creazioen e disposizione degli elementi del fragment attraverso i loro ID contenuti nel file xml
        testoUsername = (EditText) view.findViewById(R.id.textUsername);
        Log.d(TAG, "inizializzato username");
        testoPassword = (EditText) view.findViewById(R.id.textPassword);
        Log.d(TAG, "inizializzato password");
        bottoneLogin = (Button) view.findViewById(R.id.bottoneLogin);
        Log.d(TAG, "inizializzato bottone Login");
        //Setting dell'azione associata al bottone sull'Azione Click Singolo
        bottoneLogin.setOnClickListener(Applicazione.getIstance().getControlloLogin().getAzioneLogin());
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    public String getUsername(){
        return testoUsername.getText().toString();
    }

    public String getPassword(){
        return testoPassword.getText().toString();
    }
}
