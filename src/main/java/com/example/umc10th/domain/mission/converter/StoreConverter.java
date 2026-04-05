package com.example.umc10th.domain.mission.converter;

import com.example.umc10th.domain.mission.dto.res.StoreResDTO;
import com.example.umc10th.domain.mission.entity.Store;

public class StoreConverter {

    public static StoreResDTO.GetStore toGetStore(
            Store store
    ){
        return StoreResDTO.GetStore.builder()
                .storeId(store.getId())
                .storeName(store.getName())
                .build();
    }
}
