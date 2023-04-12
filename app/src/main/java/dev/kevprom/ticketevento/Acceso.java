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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;


public class Acceso extends AppCompatActivity {
    Button login, btnInRegistro;
    EditText user,contrasena;
    FirebaseFirestore mFireStore;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceso);
        mFireStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        user = findViewById(R.id.txtUsuario);
        contrasena = findViewById(R.id.txtContrasena);


        /*btnInRegistro = findViewById(R.id.btnInRegistro);*/



        login =  findViewById(R.id.btnLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameUser = user.getText().toString().trim();
                String contraUser = contrasena.getText().toString().trim();
                System.out.println(nameUser);
                System.out.println(contraUser);
                //String dateUser = date.getText().toString().trim();
                if (nameUser.isEmpty() && contraUser.isEmpty()){
                    Toast.makeText(Acceso.this, "Complete los datos", Toast.LENGTH_SHORT).show();
                }else {

                    //registerUser(nameUser,contraUser);
                    /*System.out.println(mAuth.getCurrentUser().getEmail());
                    System.out.println(mAuth.getUid());
                    startActivity(new Intent(MainActivity.this, homeInit.class));

                    String id = mAuth.getCurrentUser().getUid();
                    Map<String, Object> map = new HashMap<>();
                    if(mAuth.getCurrentUser() != null) {
                        mAuth.getCurrentUser().getUid();
                    }

                    map.put("id",id);
                    map.put("user",nameUser);
                    map.put("password",contraUser);*/
                    loginUser(nameUser,contraUser);

                }

            }
        });

        /*btnInRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Acceso.this,RegistroUsuario.class));
            }
        });*/

    }
    private void loginUser(String nameUser, String contraUser) {
        mAuth.signInWithEmailAndPassword(nameUser,contraUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()){
                    finish();
                    startActivity(new Intent(Acceso.this,MainActivity.class));
                    FirebaseUser user = mAuth.getCurrentUser();
                    Toast.makeText(Acceso.this, "Bienvenido "+user.getEmail(), Toast.LENGTH_SHORT).show();
                    System.out.println(user.getEmail());

                }else {
                    Toast.makeText(Acceso.this, "Contraseña y/o Usuario Invalido", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(Acceso.this, "Usuario No Registrado", Toast.LENGTH_SHORT).show();

            }
        });
    }
}