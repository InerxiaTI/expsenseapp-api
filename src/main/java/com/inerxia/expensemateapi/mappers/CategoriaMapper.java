package com.inerxia.expensemateapi.mappers;

import com.inerxia.expensemateapi.dtos.CategoriaDto;
import com.inerxia.expensemateapi.entities.Categoria;
import com.inerxia.expensemateapi.utils.EntityMapper;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true), uses = {UsuarioMapper.class})
public interface CategoriaMapper extends EntityMapper<CategoriaDto, Categoria> {

}
