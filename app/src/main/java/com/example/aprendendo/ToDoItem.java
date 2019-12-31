package com.example.aprendendo;

public class ToDoItem {
    private String nome;
    private String descricao;

    public ToDoItem (String nome, String descricao){
        this.nome = nome;
        this.descricao = descricao;
    }

    public ToDoItem (){
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
