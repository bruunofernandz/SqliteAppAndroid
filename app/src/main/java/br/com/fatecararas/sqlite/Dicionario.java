package br.com.fatecararas.sqlite;

import java.io.Serializable;
import java.util.Objects;

public class Dicionario implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String palavra;
    private String definicao;

    public Dicionario(int id, String palavra, String definicao) {
        this.id = id;
        this.palavra = palavra;
        this.definicao = definicao;
    }

    public Dicionario() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPalavra() {
        return palavra;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }

    public String getDefinicao() {
        return definicao;
    }

    public void setDefinicao(String definicao) {
        this.definicao = definicao;
    }

    @Override
    public String toString() {
        return "Dicionario{" +
                "id=" + id +
                ", palavra='" + palavra + '\'' +
                ", definicao='" + definicao + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dicionario)) return false;
        Dicionario that = (Dicionario) o;
        return id == that.id &&
                Objects.equals(palavra, that.palavra) &&
                Objects.equals(definicao, that.definicao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, palavra, definicao);
    }
}
