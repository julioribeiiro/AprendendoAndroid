package com.example.aprendendo;

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
        holder.description.setText(nomes.get(position).getDescricao());
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
            description = itemView.findViewById(R.id.description);

            itemView.setOnClickListener(this);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    nomes.remove(getAdapterPosition());
                    notifyDataSetChanged();

                    return true;
                }
            });
        }

        @Override
        public void onClick(View view) {
            Toast.makeText(view.getContext(), nomes.get(getAdapterPosition()).getNome(), Toast.LENGTH_LONG).show();
        }
    }
}
