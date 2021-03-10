package com.tiagocomputer.javamscrud.exception;

public class ResourceNotFoundExcenpion extends RuntimeException{

    private static final long serialVersionUID = -5930904419691645745L;

    public ResourceNotFoundExcenpion(String excepion) {
        super(excepion);
    }
}
