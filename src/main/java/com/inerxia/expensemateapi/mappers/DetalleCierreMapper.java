package com.inerxia.expensemateapi.mappers;

import com.inerxia.expensemateapi.dtos.DetalleCierreDto;
import com.inerxia.expensemateapi.entities.DetalleCierre;
import com.inerxia.expensemateapi.utils.EntityMapper;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true), uses = {ListaCompraMapper.class, UsuarioMapper.class})
public interface DetalleCierreMapper extends EntityMapper<DetalleCierreDto, DetalleCierre> {

}
