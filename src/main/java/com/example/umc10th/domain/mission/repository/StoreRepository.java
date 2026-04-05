package com.example.umc10th.domain.mission.repository;

import com.example.umc10th.domain.mission.entity.Store;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query(
            value = "SELECT * " +
                    "FROM store " +
                    "WHERE store_name LIKE CONCAT('%',:query,'%') AND store_id < :id " +
                    "ORDER BY store_id DESC " +
                    "LIMIT :pageSize ",
            nativeQuery = true
    )
    Slice<Store> findAllByLikeNameForCursor(String query, Long id, Integer pageSize);

    @Query(
            value = "SELECT * " +
                    "FROM store " +
                    "WHERE store_name LIKE CONCAT('%',:query,'%') " +
                    "ORDER BY store_id DESC " +
                    "LIMIT :pageSize ",
            nativeQuery = true
    )
    Slice<Store> findAllByLikeName(String query, Integer pageSize);
}
