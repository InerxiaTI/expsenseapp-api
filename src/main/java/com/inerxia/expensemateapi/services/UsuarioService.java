package com.inerxia.expensemateapi.services;

import com.inerxia.expensemateapi.entities.Usuario;
import com.inerxia.expensemateapi.exceptions.BusinessException;
import com.inerxia.expensemateapi.exceptions.DataNotFoundException;
import com.inerxia.expensemateapi.repositories.UsuarioRepository;
import com.inerxia.expensemateapi.utils.CustomUtilService;
import com.inerxia.expensemateapi.utils.MessageResponse;
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

    public void validateUsuario(Integer idUsuario) {
        repository.findById(idUsuario).orElseThrow(() ->
                new DataNotFoundException(MessageResponse.USER_NOT_FOUND_EXCEPTION));
    }

    public void validateUsuarioActivo(Integer idUsuario) {
        var usuario = repository.findById(idUsuario).orElseThrow(() ->
                new DataNotFoundException(MessageResponse.USER_NOT_FOUND_EXCEPTION));
        if(!usuario.getActivo()){
            throw new BusinessException(MessageResponse.USER_NOT_ACTIVE);
        }
    }

    public Usuario findById(Integer idUsuario) {
        return repository.findById(idUsuario).orElseThrow(() ->
                new DataNotFoundException(MessageResponse.USER_NOT_FOUND_EXCEPTION));
    }

    public Usuario findByCorreo(String email) {
        return repository.findByCorreo(email).orElseThrow(() ->
                new DataNotFoundException(MessageResponse.USER_NOT_FOUND_EXCEPTION));
    }
}
