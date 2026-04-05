package com.example.umc10th.global.dto;

import lombok.Builder;

import java.util.List;

public class GeneralDTO {

    // 페이지네이션 틀
    @Builder
    public record Pagination<T>(
            List<T> data,
            Boolean hasNext,
            String nextCursor,
            Integer pageSize
    ){}
}
