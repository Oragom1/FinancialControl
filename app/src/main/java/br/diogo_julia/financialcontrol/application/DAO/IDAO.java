package br.diogo_julia.financialcontrol.application.DAO;

import java.util.List;

import br.diogo_julia.financialcontrol.dominio.entidadededominio.EntidadeDominio;

public interface IDAO<T extends EntidadeDominio> {

    public long insert(T object);
    public long delete(T object);
    public long update(T object);
    public T getById(long id);
    public List<T> getList();
}
