package com.sciencemj.omok.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@RequiredArgsConstructor
public class MapList {
    private final ArrayList<Map> list = new ArrayList<Map>();
    public Map add(Map map){
        list.add(map);
        return map;
    }
}
