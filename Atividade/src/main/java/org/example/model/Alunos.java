package org.example.model;

public class Alunos {
    private Long matricula;
    private String nome;
    private String telefone;
    private boolean maioridade;
    private String curso;
    private String sexo;


    public Alunos() {}


    public Alunos(Long matricula, String nome, String telefone, boolean maioridade, String curso, String sexo) {
        this.matricula = matricula;
        this.nome = nome;
        this.telefone = telefone;
        this.maioridade = maioridade;
        this.curso = curso;
        this.sexo = sexo;
    }


    public Long getMatricula() {
        return matricula;
    }

    public void setMatricula(Long matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public boolean isMaioridade() {
        return maioridade;
    }

    public void setMaioridade(boolean maioridade) {
        this.maioridade = maioridade;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}