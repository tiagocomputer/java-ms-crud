package com.tiagocomputer.javamscrud.entity;

import com.tiagocomputer.javamscrud.data.vo.ProdutoVo;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "produto")
@Data
public class Produto implements Serializable {
    private static final long serialVersionUID = -5930904419691645745L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "estoque", nullable = false, length = 10)
    private Integer estoque;

    @Column(name = "preco", nullable = false, length = 10)
    private Double preco;

    public static Produto create(ProdutoVo produtoVo){
        return new ModelMapper().map(produtoVo, Produto.class);
    }
}
