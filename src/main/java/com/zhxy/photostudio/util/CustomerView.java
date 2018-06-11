package com.zhxy.photostudio.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerView {

    private Integer id;

    private String manName;

    private String womenName;

    private String phone;

    private String event;
}
