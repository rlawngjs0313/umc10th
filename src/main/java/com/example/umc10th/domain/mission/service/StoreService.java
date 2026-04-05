package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.mission.converter.StoreConverter;
import com.example.umc10th.domain.mission.dto.res.StoreResDTO;
import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.domain.mission.exception.StoreException;
import com.example.umc10th.domain.mission.exception.code.StoreErrorCode;
import com.example.umc10th.domain.mission.repository.StoreRepository;
import com.example.umc10th.global.converter.GeneralConverter;
import com.example.umc10th.global.dto.GeneralDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    // 가게 조회
    public GeneralDTO.Pagination<StoreResDTO.GetStore> getStore(
            String query,
            Integer pageSize,
            String cursor
    ) {

        // 커서 분해
        Slice<Store> storeList;
        Long idCursor;
        if (!(cursor.equals("-1"))){
            try {
                idCursor = Long.parseLong(cursor);
                storeList = storeRepository
                        .findAllByLikeNameForCursor(query, idCursor, pageSize);
            } catch (NumberFormatException e) {
                throw new StoreException(StoreErrorCode.NOT_VALID_CURSOR);
            }
        } else {
            // 커서 없으면 제외
            storeList = storeRepository
                    .findAllByLikeName(query, pageSize);
        }

        // 조회했을때 결과가 없는 경우
        if (!storeList.hasContent()){
            return GeneralConverter.toPagination(
                    List.of(),
                    false,
                    "-1",
                    pageSize,
                    "id"
            );
        }

        // 다음 커서 설정
        String nextCursor = storeList.getContent().getLast().getId().toString();

        return GeneralConverter.toPagination(
                storeList.stream()
                        .map(StoreConverter::toGetStore)
                        .toList(),
                storeList.hasNext(),
                nextCursor,
                storeList.getSize(),
                "id"
        );
    }
}
