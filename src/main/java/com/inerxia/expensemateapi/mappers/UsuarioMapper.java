package com.inerxia.expensemateapi.mappers;

import com.inerxia.expensemateapi.dtos.UsuarioDto;
import com.inerxia.expensemateapi.entities.Usuario;
import com.inerxia.expensemateapi.utils.EntityMapper;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface UsuarioMapper extends EntityMapper<UsuarioDto, Usuario> {

}
