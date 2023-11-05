package com.inerxia.expensemateapi.services;

import com.inerxia.expensemateapi.repositories.UsuarioRepository;
import com.inerxia.expensemateapi.utils.CustomUtilService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {
    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public boolean usuarioExists(Integer idUsuario){
        CustomUtilService.ValidateRequired(idUsuario);
        var usuario = repository.findById(idUsuario);
        return usuario.isPresent();
    }
}
