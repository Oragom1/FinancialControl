package br.diogo_julia.financialcontrol.application.tabelasbanco;

import java.util.Enumeration;

public interface IEnumTabela extends Enumeration {
    public String getColuna();
    public String getTipo();
}
