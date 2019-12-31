package com.example.aprendendo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static android.content.ContentValues.TAG;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    private static final String TAG = "Recycler Adapter";
    List<ToDoItem> nomes;

    public RecyclerAdapter(List<ToDoItem> listaNomes){
        this.nomes = listaNomes;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);



        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {//coloca dados dentro da view
        holder.name.setText(nomes.get(position).getNome());

    }


    @Override
    public int getItemCount() {//NUMERO DE LINHAS NO RECYCLER VIEW
        return nomes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageView;
        TextView name, description;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.name);

            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            ToDoItem toDoItem = nomes.get(getAdapterPosition());


            Context context = view.getContext();
            Intent intent = new Intent(context, descricao.class);

            Bundle b = new Bundle();
            b.putString("description", toDoItem.getDescricao());//PASSANDO DESCRICAO COMO PARAMETRO PARA A NOVA ACTIVITY
            b.putString("title", toDoItem.getNome());
            intent.putExtras(b);

            context.startActivity(intent);
        }
    }
}
