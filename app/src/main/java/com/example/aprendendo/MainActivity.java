package com.example.aprendendo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

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


        recyclerAdapter = new RecyclerAdapter(nomes);

       // recyclerView.setLayoutManager(new LinearLayoutManager(this)); nao eh necessario pq ja ta declarado no xml

        recyclerView.setAdapter(recyclerAdapter); //setando o adapter

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);




    }

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
