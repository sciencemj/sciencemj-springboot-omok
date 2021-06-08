package com.sciencemj.omok.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OMapService {
    private final MapList mapList = new MapList();
    public Map findById(Long id){
        for (Map map: mapList.getList()) {
            if (map.getId().equals(id))
                return map;
        }
        return mapList.add(new Map(id, new int[21][21]));
    }
    public Long add(Map map){
        return mapList.add(map).getId();
    }
}
