package dev.kevprom.ticketevento;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Codigo> codigos;

    public MyAdapter(Context context, ArrayList<Codigo> codigos) {
        this.context = context;
        this.codigos = codigos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(v);
    }

    //este método setea los views del diseño de item para el recyclerView
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Codigo codigo = codigos.get(position);

        holder.codigo.setText(codigo.getCodigo());
        holder.estado.setText("" + codigo.getEstado());
        holder.tipo.setText(codigo.getTipo());

    }

    @Override
    public int getItemCount() {
        return codigos.size();
    }



//    en esta clase se enlaza los views del diseño de item
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView codigo, estado, tipo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            codigo = itemView.findViewById(R.id.txtCodigo);
            estado = itemView.findViewById(R.id.txtEstado);
            tipo = itemView.findViewById(R.id.txtTipo);
        }
    }
}
