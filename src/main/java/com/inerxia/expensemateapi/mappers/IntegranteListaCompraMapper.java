package com.inerxia.expensemateapi.mappers;

import com.inerxia.expensemateapi.dtos.IntegranteListaCompraDto;
import com.inerxia.expensemateapi.entities.IntegranteListaCompra;
import com.inerxia.expensemateapi.utils.EntityMapper;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true), uses = {ListaCompraMapper.class, UsuarioMapper.class})
public interface IntegranteListaCompraMapper extends EntityMapper<IntegranteListaCompraDto, IntegranteListaCompra> {

}
