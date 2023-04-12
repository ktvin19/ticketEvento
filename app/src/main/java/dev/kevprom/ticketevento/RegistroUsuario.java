package dev.kevprom.ticketevento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistroUsuario extends AppCompatActivity {
    FirebaseFirestore mFireStore;
    FirebaseAuth mAuth;

    Button rregistrar;
    EditText rCorreo,rContra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        mFireStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        rCorreo = findViewById(R.id.txtRCorreo);
        rContra= findViewById(R.id.txtRContra);
        rregistrar =  findViewById(R.id.btnRegistrar);

        rregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameUser = rCorreo.getText().toString().trim();
                String contraUser = rContra.getText().toString().trim();
                //String dateUser = date.getText().toString().trim();
                if (nameUser.isEmpty() & contraUser.isEmpty()){
                    Toast.makeText(RegistroUsuario.this, "Complete los datos", Toast.LENGTH_SHORT).show();
                }else {
                    registerUser(nameUser,contraUser);

                }




                }


        });

    }


    private void registerUser(String nameUser, String contraUser) {
        mAuth.createUserWithEmailAndPassword(nameUser,contraUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                String id = mAuth.getCurrentUser().getUid();
                Map<String, Object> map = new HashMap<>();

                    map.put("id", id);
                    map.put("user", nameUser);
                    map.put("password", contraUser);

                    finish();
                    startActivity(new Intent(RegistroUsuario.this, Acceso.class));
                    Toast.makeText(RegistroUsuario.this, "Usuario Registrado", Toast.LENGTH_SHORT).show();
                    //map.put("date",dateUser);
                    /*el codigo de abajo es para registrar a la base de datos user la informacion*/


                    /*mFireStore.collection("user").document(id).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {


                            finish();
                            startActivity(new Intent(RegistroUsuario.this, Acceso.class));
                            Toast.makeText(RegistroUsuario.this, "uruario registrado", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            System.out.println(user.getEmail());



                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            Toast.makeText(RegistroUsuario.this, "Error al guardar", Toast.LENGTH_SHORT).show();
                        }
                    });*/


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(RegistroUsuario.this, "Error al registrar", Toast.LENGTH_SHORT).show();
            }
        });
    }



}