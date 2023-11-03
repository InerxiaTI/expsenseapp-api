package com.inerxia.expensemateapi.mappers;

import com.inerxia.expensemateapi.dtos.CompraDto;
import com.inerxia.expensemateapi.entities.Compra;
import com.inerxia.expensemateapi.utils.EntityMapper;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true), uses = {ListaCompraMapper.class, CategoriaMapper.class, UsuarioMapper.class})
public interface CompraMapper extends EntityMapper<CompraDto, Compra> {

}
