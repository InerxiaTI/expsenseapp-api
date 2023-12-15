package com.inerxia.expensemateapi.facades;

import com.inerxia.expensemateapi.dtos.requests.FiltroCategoriaRequest;
import com.inerxia.expensemateapi.dtos.responses.ConsultaCategoriaResponse;
import com.inerxia.expensemateapi.mappers.CategoriaMapper;
import com.inerxia.expensemateapi.services.CategoriaService;
import com.inerxia.expensemateapi.services.UsuarioService;
import com.inerxia.expensemateapi.utils.CustomUtilService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CategoriaFacade {
    private final Logger log = LoggerFactory.getLogger(CategoriaFacade.class);
    private final CategoriaMapper categoriaMapper;
    private final CategoriaService categoriaService;
    private final UsuarioService usuarioService;

    public CategoriaFacade(CategoriaMapper categoriaMapper, CategoriaService categoriaService, UsuarioService usuarioService) {
        this.categoriaMapper = categoriaMapper;
        this.categoriaService = categoriaService;
        this.usuarioService = usuarioService;
    }

    public Page<ConsultaCategoriaResponse> consultarCategoriasConFiltro(@Param("filtro") FiltroCategoriaRequest filtro, Pageable pageable){
        CustomUtilService.ValidateRequired(filtro);
        CustomUtilService.ValidateRequired(filtro.getIdUsuarioCreador());

        usuarioService.validateUsuario(filtro.getIdUsuarioCreador());

        return categoriaService.consultarCategoriasConFiltro(filtro, pageable);
    }
}
