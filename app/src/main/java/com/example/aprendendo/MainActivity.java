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
import android.widget.Toast;

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
    ToDoDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        entryNome = findViewById((R.id.entryNome));
        entryDescricao = findViewById(R.id.entryDescription);
        recyclerView = findViewById(R.id.recyclerView);
        nomes = new ArrayList<>();
        dao = new ToDoDao(this);

        nomes = dao.receiveAll();//-- PEGA TODOS OS ToDos DO BANCO DE DADOS

        recyclerAdapter = new RecyclerAdapter(nomes);//----ATUALIZA O NO RECYCLERVIEW COM OS DADOS RECEBIDOS

       // recyclerView.setLayoutManager(new LinearLayoutManager(this)); nao eh necessario pq ja ta declarado no xml

        recyclerView.setAdapter(recyclerAdapter); //CRIANDO O ADAPTER DO RECYCLERVIEW

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);//COLOCA LINHAS DE SEPARACAO
        recyclerView.addItemDecoration(dividerItemDecoration);//----------------------------------------------------------------NO RECYCLERVIEW

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);//CRIANDO O ITEMTOUCHHELPER PARA O SWIPE
        itemTouchHelper.attachToRecyclerView(recyclerView);//CONECTANDO O ITEM TOUCH HELPER COM O RECYCLERVIEW

    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            if (direction == ItemTouchHelper.LEFT){//-------------------------------------SE FIZER SWIPE PRA ESQUERDA
                final ToDoItem removedItem = nomes.get(position);//----------------------SALVA OBJETO QUE VAI SER REMOVIDO NUMA VARIAVEL AUXILIAR
                nomes.remove(position);//----------------------------------------------REMOVE O OBJETO DA LISTA
                recyclerAdapter.notifyDataSetChanged();//----------------------------------FAZ A ATUALIZACAO DO RECYCLERVIEW NO APP
                dao.removeToDo(removedItem);
                Snackbar.make(recyclerView, removedItem.getNome(), Snackbar.LENGTH_LONG)//COMECA PROPRIEDADE DE DESFAZER A REMOCAO
                        .setAction("UNDO", new View.OnClickListener() {//-----------ACAO UNDO
                            @Override
                            public void onClick(View view) {//-----------------------ACAO DE QUANDO EH CLICADO
                                nomes.add(position, removedItem);//-----------------ADICIONA O ITEM QUE FOI REMOVIDO NA MESMA POSICAO DE ONDE FOI RETIRADO
                                recyclerAdapter.notifyDataSetChanged();//----------ATUALIZA O RECYCLERVIEW DO APP
                                dao.insertToDoWithId(removedItem);//------RECRIA NO BANCO DE DADOS NA MESMA POSUCAO QUE ESTAVA
                            }
                        }).show();

            }



        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorAccent))//COLOCA COR DE FUNDO NO SWIPE
                    .addSwipeLeftActionIcon(R.drawable.delete)//----COLOCA ICONE DE LIXEIRA DA PASTA DRAWABLE
                    .create()
                    .decorate();


            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

    };


    public void onCLickItem(ToDoItem item){
        Toast.makeText(this, item.getDescricao(), Toast.LENGTH_LONG).show();
    }




    public void InsereTarefa(View view) {
        ToDoItem novo = new ToDoItem(entryNome.getText().toString(), entryDescricao.getText().toString());//CRIA UMA NOVA TAREFA
        nomes.add(novo);// INSERE NO ARRAY NOMES
        entryNome.setText("");//---------------------RESETA O CAMPO DE TEXTO NOME
        entryDescricao.setText("");//----------------RESETA O CAMPO DE TEXTO DESCRICAO
        recyclerAdapter.notifyDataSetChanged();//-----FAZ A ALTERACAO NO RECYCLERVIEW DO APP
        long id = dao.insertToDo(novo);
        Toast.makeText(this, "ToDoInserido id: "+id, Toast.LENGTH_LONG).show();
    }
}
