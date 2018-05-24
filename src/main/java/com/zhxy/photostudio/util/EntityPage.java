package com.zhxy.photostudio.util;

import lombok.Data;

import java.util.List;

@Data
public class EntityPage<T> {

    private int draw;

    private int recordsTotal;

    private int recordsFiltered;

    private List<T> data;
}
