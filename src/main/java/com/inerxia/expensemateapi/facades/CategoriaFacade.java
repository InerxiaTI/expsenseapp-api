package com.inerxia.expensemateapi.facades;

import com.inerxia.expensemateapi.dtos.CategoriaDto;
import com.inerxia.expensemateapi.dtos.requests.CrearCategoriaRequest;
import com.inerxia.expensemateapi.dtos.requests.EditarCategoriaRequest;
import com.inerxia.expensemateapi.dtos.requests.FiltroCategoriaRequest;
import com.inerxia.expensemateapi.dtos.responses.ConsultaCategoriaResponse;
import com.inerxia.expensemateapi.entities.Categoria;
import com.inerxia.expensemateapi.entities.Compra;
import com.inerxia.expensemateapi.exceptions.RequestErrorException;
import com.inerxia.expensemateapi.mappers.CategoriaMapper;
import com.inerxia.expensemateapi.services.CategoriaService;
import com.inerxia.expensemateapi.services.CompraService;
import com.inerxia.expensemateapi.services.UsuarioService;
import com.inerxia.expensemateapi.utils.CustomUtilService;
import com.inerxia.expensemateapi.utils.MessageResponse;
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
    private final CompraService compraService;

    public CategoriaFacade(CategoriaMapper categoriaMapper, CategoriaService categoriaService, UsuarioService usuarioService, CompraService compraService) {
        this.categoriaMapper = categoriaMapper;
        this.categoriaService = categoriaService;
        this.usuarioService = usuarioService;
        this.compraService = compraService;
    }

    public Page<ConsultaCategoriaResponse> consultarCategoriasConFiltro(@Param("filtro") FiltroCategoriaRequest filtro, Pageable pageable) {
        CustomUtilService.ValidateRequired(filtro);
        CustomUtilService.ValidateRequired(filtro.getIdUsuarioCreador());

        usuarioService.validateUsuario(filtro.getIdUsuarioCreador());

        return categoriaService.consultarCategoriasConFiltro(filtro, pageable);
    }

    public List<CategoriaDto> consultarCategoriasDelCreadorConFiltro(Integer usuarioCreadorId) {
        CustomUtilService.ValidateRequired(usuarioCreadorId);

        usuarioService.validateUsuario(usuarioCreadorId);

        return categoriaMapper.toDto(categoriaService.consultarCategoriasDelCreadorConFiltro(usuarioCreadorId));
    }

    public CategoriaDto crearCategoria(CrearCategoriaRequest request) {
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

    public CategoriaDto editarCategoria(EditarCategoriaRequest request) {
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

    public void eliminarCategoria(Integer idCategoria) {
        CustomUtilService.ValidateRequired(idCategoria);

        Categoria categoria = categoriaService.findById(idCategoria);

        List<Compra> compras = compraService.consultarComprasByCategoria(categoria.getId());
        if (!compras.isEmpty()) {
            throw new RequestErrorException(MessageResponse.CATEGORY_HAS_ASSOCIATED_PURCHASES);
        }
        categoriaService.delete(categoria.getId());
    }
}
