package com.example.aprendendo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ToDoDao {

    private ConectSQLite conectSQLite;
    private SQLiteDatabase database;

    public ToDoDao(Context context){
        conectSQLite = new ConectSQLite(context);//crio conexao com banco
        database = conectSQLite.getWritableDatabase();//salvo na variavel database
    }

    public long insertToDo(ToDoItem toDoItem){//-------------INSERE UM NOVO ELEMENTO NO BANCO DE DADOS
        ContentValues contentValues = new ContentValues();
        contentValues.put("nome", toDoItem.getNome());
        contentValues.put("descricao", toDoItem.getDescricao());
        return database.insert("tarefas", null, contentValues);
    }

    public long insertToDoWithId(ToDoItem toDoItem){//----FUNCAO PARA INSERIR NO BANCO DE DADOS NUMA POSICAO ESPECIFICA
        ContentValues contentValues = new ContentValues();
        contentValues.put("nome", toDoItem.getNome());
        contentValues.put("descricao", toDoItem.getDescricao());
        contentValues.put("id", toDoItem.getId());
        return database.insert("tarefas", null, contentValues);
    }


    public void removeToDo(ToDoItem item){//-----REMOVE UM ELEMENTO DO BANCO DE DADOS
        database.delete("tarefas", "id = ?", new String[]{String.valueOf(item.getId())});

    }

    public List<ToDoItem> receiveAll(){//--------RECEBE TODOS OS ELEMENTOS DO BD E SALVA NUM ARRAY DE ToDoItem
        List<ToDoItem> list = new ArrayList<>();
        Cursor cursor = database.query("tarefas", new String[]{"id", "nome", "descricao"}, null, null, null, null, null);

        while(cursor.moveToNext()){
            ToDoItem atual = new ToDoItem(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
            list.add(atual);
        }

        return list;
    }

}
