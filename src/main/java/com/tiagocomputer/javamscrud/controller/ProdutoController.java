package com.tiagocomputer.javamscrud.controller;

import com.tiagocomputer.javamscrud.data.vo.ProdutoVo;
import com.tiagocomputer.javamscrud.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/produto")
public class ProdutoController {
    private final ProdutoService produtoService;
    private final PagedResourcesAssembler<ProdutoVo> assembler;

    @Autowired
    public ProdutoController(ProdutoService produtoService, PagedResourcesAssembler<ProdutoVo> assembler) {
        this.produtoService = produtoService;
        this.assembler = assembler;
    }

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
    public ProdutoVo findById(@PathVariable("id") Long id) {
        ProdutoVo produtoVo = produtoService.findById(id);
        produtoVo.add(linkTo(methodOn(ProdutoController.class).findById(id)).withSelfRel());
        return produtoVo;
    }

    @GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
    public ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "limit", defaultValue = "12") int limit,
                                     @RequestParam(value = "direction", defaultValue = "asc") String direction) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "nome"));

        Page<ProdutoVo> produtos = produtoService.findAll(pageable);

        produtos.stream()
                .forEach(p -> p.add(linkTo(methodOn(ProdutoController.class).findById(p.getId())).withSelfRel()));

        PagedModel<EntityModel<ProdutoVo>> pagedModel = assembler.toModel(produtos);
        return new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }

    @PostMapping(produces = {"application/json", "application/xml", "application/x-yaml"},
                 consumes = {"application/json", "application/xml", "application/x-yaml"})
    public ProdutoVo create(@RequestBody ProdutoVo produtoVo) {
        ProdutoVo proVO = produtoService.create(produtoVo);
        proVO.add(linkTo(methodOn(ProdutoController.class).findById(proVO.getId())).withSelfRel());
        return proVO;
    }

    @PutMapping(produces = {"application/json", "application/xml", "application/x-yaml"},
            consumes = {"application/json", "application/xml", "application/x-yaml"})
    public ProdutoVo update(@RequestBody ProdutoVo produtoVo) {
        ProdutoVo proVO = produtoService.update(produtoVo);
        proVO.add(linkTo(methodOn(ProdutoController.class).findById(produtoVo.getId())).withSelfRel());
        return proVO;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
       produtoService.delete(id);
        return ResponseEntity.ok().build();
    }
}
