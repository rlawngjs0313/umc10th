package com.example.umc10th.global.converter;

import com.example.umc10th.global.dto.GeneralDTO;

import java.util.List;

public class GeneralConverter {

    // 페이지네이션 틀 생성
    public static <T> GeneralDTO.Pagination<T> toPagination(
            List<T> data,
            Boolean hasNext,
            String nextCursor,
            Integer pageSize,
            String cursorType
    ){
        return GeneralDTO.Pagination.<T>builder()
                .data(data)
                .hasNext(hasNext)
                .nextCursor(nextCursor)
                .pageSize(pageSize)
                .build();
    }
}
