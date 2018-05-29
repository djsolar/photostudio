package com.zhxy.photostudio.util;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class DataTableViewPage<T> {

    private List<T> aaData;
    private long recordsTotal;
    private long recordsFiltered;
}