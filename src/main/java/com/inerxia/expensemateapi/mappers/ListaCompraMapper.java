package com.inerxia.expensemateapi.mappers;

import com.inerxia.expensemateapi.dtos.ListaCompraDto;
import com.inerxia.expensemateapi.entities.ListaCompra;
import com.inerxia.expensemateapi.utils.EntityMapper;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true), uses = {UsuarioMapper.class})
public interface ListaCompraMapper extends EntityMapper<ListaCompraDto, ListaCompra> {

}
