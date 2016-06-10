package br.diogo_julia.financialcontrol.application.DAO.usuariodaoimpl;

import java.util.List;

import br.diogo_julia.financialcontrol.application.DAO.IDAO;
import br.diogo_julia.financialcontrol.dominio.usuario.Usuario;

public class UsuarioDAO implements IDAO<Usuario> {

    @Override
    public long insert(Usuario object) {
        return 0;
    }

    @Override
    public long delete(Usuario object) {
        return 0;
    }

    @Override
    public long update(Usuario object) {
        return 0;
    }

    @Override
    public Usuario getById(long id) {
        return null;
    }

    @Override
    public List<Usuario> getList() {
        return null;
    }
}
