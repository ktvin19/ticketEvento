package dev.kevprom.ticketevento;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListaCodigosActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapter myAdapter;

    ArrayList<Codigo> listaCodigos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_codigos);

        recyclerView = findViewById(R.id.lista_codigos);
        database = FirebaseDatabase.getInstance().getReference("codigos");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listaCodigos = new ArrayList<>();

        myAdapter = new MyAdapter(this, listaCodigos);
        recyclerView.setAdapter(myAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

//                nota: para que no haya problemas, la base de datos de firebase debe tener las reglas de escritura
//                        y lectura habilitadas (true), y se debe especificar los datos de los nodos, de acuerdo
//                        al modelo Java que se esta declarando, es decir colocar bien el tipo de dato (String, int, Long, etc)
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Codigo codigo = dataSnapshot.getValue(Codigo.class);
                    listaCodigos.add(codigo);
                }

                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}