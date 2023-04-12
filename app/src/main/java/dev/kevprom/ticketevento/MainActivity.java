package dev.kevprom.ticketevento;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
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

public class MainActivity extends AppCompatActivity {

    ImageView btnLeer, btnLista, btnUpdate;
    private DatabaseReference mDatabase;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        setTheme(R.style.Theme_BarcodeQR);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //agregue aqui kevin el primero ---------------------------------->
        mAuth = FirebaseAuth.getInstance();
        //agregue aqui kevin el primero ---------------------------------->
        btnLeer = findViewById(R.id.btnLeer);

        btnLeer.setOnClickListener(v -> {
            scanCode();
        });

        btnLista = findViewById(R.id.btnLista);

        btnUpdate = findViewById(R.id.btnUpdate);

        btnLista.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ListaCodigosActivity.class));
        });

        btnUpdate.setOnClickListener(v -> {
            startActivity( new Intent(getApplicationContext(), UpdateActivity.class) );
//            myMsg(getApplicationContext(), "DESHABILITADO");
        });
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
        mDatabase = FirebaseDatabase.getInstance().getReference("codigos");

        try {
            if (result.getContents() != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(result.getContents());
                builder.setCancelable(true);
                builder.setPositiveButton("REGISTRAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        // TODO: 11/04/2023 : aqui es donde se crashea revisar
                        mDatabase.child(result.getContents()).child("estado").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                //agregue aqui kevin una linea------------------------------------>
                                FirebaseUser user = mAuth.getCurrentUser();
                                //agregue una linea de codigo aqui kevin------->

                                if (!task.isSuccessful()) {
                                    Log.e("firebase", "Error al obtener la información", task.getException());
                                }
                                else {
                                    try {
                                        if ( Integer.parseInt(task.getResult().getValue().toString() ) == 1 ){
                                            mDatabase.child(result.getContents()).child("estado").setValue(0);
                                            //agregue aqui kevin una linea-------------------------------------------->
                                            mDatabase.child(result.getContents()).child("usuario").setValue(user.getEmail());
                                            //agregue aqui kevin una linea-------------------------------------------->
                                            NoRegistradoFragment f = new NoRegistradoFragment();
                                            f.show(getSupportFragmentManager(), "Nuevo registro");
                                        }else {
                                            YaRegistradoFragment f = new YaRegistradoFragment();
                                            f.show(getSupportFragmentManager(), "Ya está registrado");
                                        }
                                    }catch (Exception e){
                                        Log.e("ERROR----->", e.getMessage());
                                        Toast.makeText(getApplicationContext(), "EL CODIGO NO EXISTE", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        });
                    }
                });

                builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        }catch (Exception e){
            Log.i("error",e.getMessage());
        }


    });

    private static void myMsg(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }
}