package com.example.aprendendo;

import java.io.Serializable;

public class ToDoItem implements Serializable {
    private int id;
    private String nome;
    private String descricao;


    public ToDoItem (){
    }


    public ToDoItem (String nome, String descricao){
        this.nome = nome;
        this.descricao = descricao;
    }

    public ToDoItem (int id, String nome, String descricao){
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }



}
