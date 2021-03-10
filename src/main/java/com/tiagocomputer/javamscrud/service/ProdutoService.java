package com.tiagocomputer.javamscrud.service;

import com.tiagocomputer.javamscrud.data.vo.ProdutoVo;
import com.tiagocomputer.javamscrud.entity.Produto;
import com.tiagocomputer.javamscrud.exception.ResourceNotFoundExcenpion;
import com.tiagocomputer.javamscrud.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public ProdutoVo create(ProdutoVo produtoVo) {
        ProdutoVo proVoRetorno = ProdutoVo.create(produtoRepository.save(Produto.create(produtoVo)));
        return proVoRetorno;
    }

    public Page<ProdutoVo> findAll(Pageable pageable) {
        var page = produtoRepository.findAll(pageable);
        return page.map(this::convertToProdutoVo);
    }

    public ProdutoVo convertToProdutoVo(Produto produto) {
        return ProdutoVo.create(produto);
    }

    public ProdutoVo findById(Long id) {
        var entity = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExcenpion("No records found for this ID"));
        return ProdutoVo.create(entity);
    }

    public ProdutoVo update(ProdutoVo produtoVo) {
        final Optional<Produto> optionalProdutoVo = produtoRepository.findById(produtoVo.getId());

        if (!optionalProdutoVo.isPresent()) {
            new ResourceNotFoundExcenpion("No records found for this ID");
        }

        return ProdutoVo.create(produtoRepository.save(Produto.create(produtoVo)));
    }

    public void delete(Long id) {
        var entity = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundExcenpion("No records found for this ID"));
        produtoRepository.delete(entity);
    }
}
