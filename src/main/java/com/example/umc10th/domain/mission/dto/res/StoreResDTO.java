package com.example.umc10th.domain.mission.dto.res;

import lombok.Builder;

public class StoreResDTO {

    @Builder
    public record GetStore(
            Long storeId,
            String storeName
    ){}
}
