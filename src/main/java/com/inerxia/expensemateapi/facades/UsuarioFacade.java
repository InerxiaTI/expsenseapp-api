package com.inerxia.expensemateapi.facades;

import com.inerxia.expensemateapi.dtos.UsuarioDto;
import com.inerxia.expensemateapi.dtos.requests.GetUsuarioRequest;
import com.inerxia.expensemateapi.entities.Usuario;
import com.inerxia.expensemateapi.exceptions.RequestErrorException;
import com.inerxia.expensemateapi.mappers.UsuarioMapper;
import com.inerxia.expensemateapi.services.UsuarioService;
import com.inerxia.expensemateapi.utils.CustomUtilService;
import com.inerxia.expensemateapi.utils.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@Transactional
public class UsuarioFacade {
    private final Logger log = LoggerFactory.getLogger(UsuarioFacade.class);
    private final UsuarioMapper usuarioMapper;
    private final UsuarioService usuarioService;

    public UsuarioFacade(UsuarioMapper usuarioMapper, UsuarioService usuarioService) {
        this.usuarioMapper = usuarioMapper;
        this.usuarioService = usuarioService;
    }

    public UsuarioDto getUsuarioByCorreoWithPass(GetUsuarioRequest request){
        CustomUtilService.ValidateRequired(request);
        CustomUtilService.ValidateRequired(request.getEmail());
        CustomUtilService.ValidateRequired(request.getPass());

        Usuario usuario = usuarioService.findByCorreo(request.getEmail());

        if (Objects.isNull(usuario) || !usuario.getContrasena().equals(request.getPass())) {
            throw new RequestErrorException(MessageResponse.USER_NOT_FOUND_EXCEPTION);
        }
        if (!usuario.getActivo()) {
            throw new RequestErrorException(MessageResponse.USER_NOT_ACTIVE);
        }

        return usuarioMapper.toDto(usuario);
    }
}
