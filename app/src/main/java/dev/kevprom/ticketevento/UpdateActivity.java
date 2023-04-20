package dev.kevprom.ticketevento;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class UpdateActivity extends AppCompatActivity {

    Button btnBuscarCodigo, btnBuscarPorQR;
    EditText txtBuscarCod;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);


        txtBuscarCod = findViewById(R.id.txtBuscar);
        btnBuscarCodigo = findViewById(R.id.btnBuscarCodigo);
        btnBuscarPorQR = findViewById(R.id.btnBuscarQR);


        btnBuscarCodigo.setOnClickListener(v -> {
//            Toast.makeText(getApplicationContext(), getTxtCodigo(), Toast.LENGTH_LONG).show();
//            getFirebase();
            Log.i("codigo barras ----->",txtBuscarCod.getText().toString().trim().length()+"");
            if (txtBuscarCod.getText().toString().trim().length() <= 4){

                Toast.makeText(getApplicationContext(), "Ingrese un código válido", Toast.LENGTH_SHORT).show();
            }else{
                launchFragment(txtBuscarCod.getText().toString());
            }
        });

        btnBuscarPorQR.setOnClickListener( v->{
            scanCode();
        });

    }

    private String getTxtCodigo() {
        return txtBuscarCod.getText().toString();
    }

    private void scanCode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Leer codigo QR de entrada");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
//        mDatabase = FirebaseDatabase.getInstance().getReference("codigos");

        if (result.getContents() != null) {
            txtBuscarCod.setText(result.getContents());
        }
    });


    private void launchFragment(String code_) {
        mDatabase = FirebaseDatabase.getInstance().getReference("codigos");
        mDatabase.child( code_ ).child("estado").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error al obtener la información", task.getException());
                } else {
                    try {
                        if ( Integer.parseInt(task.getResult().getValue().toString() ) == 0 ){
//                            mDatabase.child(code_).child("estado").setValue(1);
                            //                pasando data al fragment
                            Bundle bundle = new Bundle();
                            bundle.putString("code", code_);
                            bundle.putString("state", task.getResult().getValue().toString() );
                            UpdateFragment uf = new UpdateFragment();
                            uf.setArguments(bundle);

                            uf.show(getSupportFragmentManager(), "Actualizar código");
                        }else {
                            Toast.makeText( getApplicationContext(), "El ticket "+ code_ +" aún no se consume", Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        Log.e("ERROR----->", e.getMessage());
                        Toast.makeText( getApplicationContext(), "EL CODIGO NO EXISTE", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}