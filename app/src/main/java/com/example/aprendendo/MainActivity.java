package com.example.aprendendo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Canvas;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;

    EditText entryNome;
    EditText entryDescricao;

    List<ToDoItem> nomes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        entryNome = findViewById((R.id.entryNome));
        entryDescricao = findViewById(R.id.entryDescription);
        recyclerView = findViewById(R.id.recyclerView);
        nomes = new ArrayList<>();
        nomes.add(new ToDoItem("Visitar Europa", "Em Janeiro"));
        nomes.add(new ToDoItem("Progamar AndroidStudio", "Para Estagio"));
        nomes.add(new ToDoItem("Going London", "Dia 17"));
        nomes.add(new ToDoItem("Planejar Viagem", "Procurar lugares para ir em Londres"));
        nomes.add(new ToDoItem("Descansar", "Aproveitar viagem parar tirar ferias"));


        recyclerAdapter = new RecyclerAdapter(nomes);

       // recyclerView.setLayoutManager(new LinearLayoutManager(this)); nao eh necessario pq ja ta declarado no xml

        recyclerView.setAdapter(recyclerAdapter); //setando o adapter

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            if (direction == ItemTouchHelper.LEFT){
                final ToDoItem removedItem = nomes.get(position);
                nomes.remove(viewHolder.getAdapterPosition());
                recyclerAdapter.notifyDataSetChanged();
                Snackbar.make(recyclerView, removedItem.getNome(), Snackbar.LENGTH_LONG)
                        .setAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                nomes.add(position, removedItem);
                                recyclerAdapter.notifyDataSetChanged();
                            }
                        }).show();

            }



        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorAccent))
                    .addSwipeLeftActionIcon(R.drawable.delete)
                    .create()
                    .decorate();


            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };




    public void InsereTarefa(View view) {
        //nomes.add(entryNome.getText().toString());
        //recyclerAdapter.notifyDataSetChanged();
        //entryNome.setText("");
        ToDoItem novo = new ToDoItem();
        novo.setNome(entryNome.getText().toString());
        novo.setDescricao(entryDescricao.getText().toString());
        nomes.add(novo);
        entryNome.setText("");
        entryDescricao.setText("");
        recyclerAdapter.notifyDataSetChanged();

    }
}
