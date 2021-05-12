package com.sanshi.fileserver.vo;

import lombok.Data;

import java.util.function.Predicate;

@Data
public class Result<T> {
    private boolean ok;
    private T data;

    public Result() {

    }

    public Result(boolean ok, T data) {
        this.ok = ok;
        this.data = data;
    }

    public Result(boolean ok) {
        this(ok, null);
    }

    public Result(T data) {
        this(true, data);
    }

    public static Result OK() {
        return new Result(true);
    }

    public static Result FAIL() {
        return new Result(false);
    }

    public static Result test(Object o, Predicate<Object> p) {
        return p.test(o) ? new Result<>(o) : FAIL();
    }
}
