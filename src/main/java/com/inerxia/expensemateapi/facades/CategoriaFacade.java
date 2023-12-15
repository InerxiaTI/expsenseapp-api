package com.inerxia.expensemateapi.facades;

import com.inerxia.expensemateapi.dtos.CategoriaDto;
import com.inerxia.expensemateapi.dtos.requests.CrearCategoriaRequest;
import com.inerxia.expensemateapi.dtos.requests.EditarCategoriaRequest;
import com.inerxia.expensemateapi.dtos.requests.FiltroCategoriaRequest;
import com.inerxia.expensemateapi.dtos.responses.ConsultaCategoriaResponse;
import com.inerxia.expensemateapi.entities.Categoria;
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
import java.util.List;
import java.util.Objects;

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

    public List<CategoriaDto> consultarCategoriasDelCreadorConFiltro(Integer usuarioCreadorId){
        CustomUtilService.ValidateRequired(usuarioCreadorId);

        usuarioService.validateUsuario(usuarioCreadorId);

        return categoriaMapper.toDto(categoriaService.consultarCategoriasDelCreadorConFiltro(usuarioCreadorId));
    }

    public CategoriaDto crearCategoria(CrearCategoriaRequest request){
        CustomUtilService.ValidateRequired(request);
        CustomUtilService.ValidateRequired(request.getIdUsuarioCreador());
        CustomUtilService.ValidateRequired(request.getEsPrivada());
        CustomUtilService.ValidateRequired(request.getNombre());

        usuarioService.validateUsuario(request.getIdUsuarioCreador());

        var categoria = new Categoria();
        categoria.setNombre(request.getNombre());
        categoria.setEsPrivada(request.getEsPrivada());
        categoria.setUsuarioCreadorId(request.getIdUsuarioCreador());
        Categoria categoriaSaved = categoriaService.save(categoria);
        return categoriaMapper.toDto(categoriaSaved);
    }

    public CategoriaDto editarCategoria(EditarCategoriaRequest request){
        CustomUtilService.ValidateRequired(request);
        CustomUtilService.ValidateRequired(request.getIdCategoria());

        Categoria categoria = categoriaService.findById(request.getIdCategoria());

        if (Objects.nonNull(request.getNombre())) {
            categoria.setNombre(request.getNombre());
        }
        if (Objects.nonNull(request.getEsPrivada())) {
            categoria.setEsPrivada(request.getEsPrivada());
        }

        Categoria categoriaSaved = categoriaService.update(categoria);
        return categoriaMapper.toDto(categoriaSaved);
    }
}
