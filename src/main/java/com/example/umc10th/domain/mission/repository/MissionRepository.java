package com.example.umc10th.domain.mission.repository;

import com.example.umc10th.domain.mission.entity.Mission;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    @Query(
            value = "SELECT m.mission_id, m.created_at, m.deleted_at, m.updated_at, " +
                    "m.conditional, m.deadline, m.mission_point, m.store_id " +
                    "FROM mission AS m " +
                    "JOIN store ON m.store_id = store.store_id " +
                    "JOIN location ON store.location_id = location.location_id " +
                    "LEFT JOIN member_mission AS m2 ON m.mission_id = m2.mission_id " +
                    "WHERE location.location_name = :locationName AND " +
                    "(m2.member_id != :memberId OR m2.member_id IS NULL) " +
                    "ORDER BY m.mission_id DESC ",
            nativeQuery = true
    )
    Slice<Mission> findLocationMission(String locationName, Long memberId, Pageable pageable);

    @Query(
            value = "SELECT m.mission_id, m.created_at, m.deleted_at, m.updated_at, " +
                    "m.conditional, m.deadline, m.mission_point, m.store_id " +
                    "FROM mission AS m " +
                    "JOIN store ON m.store_id = store.store_id " +
                    "JOIN location ON store.location_id = location.location_id " +
                    "LEFT JOIN member_mission AS m2 ON m.mission_id = m2.mission_id " +
                    "WHERE location.location_name = :locationName AND " +
                    "(m2.member_id != :memberId OR m2.member_mission_id IS NULL) " +
                    "AND m.mission_id < :idCursor " +
                    "ORDER BY m.mission_id DESC ",
            nativeQuery = true
    )
    Slice<Mission> findLocationMissionWithCursor(String locationName, Long memberId, Long idCursor, Pageable pageable);
}
