package org.example.model;

public class Cursos {
    private Long codigo;
    private String nome;
    private String sigla;
    private Area area;
    private int areaId;


    public Cursos() {}


    public Cursos(Long codigo, String nome, String sigla, Area area) {
        this.codigo = codigo;
        this.nome = nome;
        this.sigla = sigla;
        this.area = area;
    }


    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
        this.areaId = area.ordinal(); // Define o areaId baseado na posição da enum
    }
}
