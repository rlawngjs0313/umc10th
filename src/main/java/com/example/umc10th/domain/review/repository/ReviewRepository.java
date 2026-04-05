package com.example.umc10th.domain.review.repository;

import com.example.umc10th.domain.review.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Slice<Review> findAllByStore_IdAndIdLessThanOrderByIdDesc(Long storeId, Long id, Pageable pageable);

    Slice<Review> findAllByStore_IdOrderByIdDesc(Long storeId, Pageable pageable);

    @Query(
            value = "select r1 " +
                    "from Review r1 " +
                    "join Member m on r1.member.id = m.id " +
                    "join Store s on r1.store.id = s.id " +
                    "left join Reply r2 on r1.reply.id = r2.id " +
                    "where m.id = :memberId and s.id = :storeId " +
                    "order by r1.id desc "
    )
    Slice<Review> findMyReviewsOrderById(Long memberId, Long storeId, Pageable pageable);

    @Query(
            value = "select r1 " +
                    "from Review r1 " +
                    "join Member m on r1.member.id = m.id " +
                    "join Store s on r1.store.id = s.id " +
                    "left join Reply r2 on r1.reply.id = r2.id " +
                    "where m.id = :memberId " +
                    "order by r1.id desc "
    )
    Slice<Review> findMyReviewsOrderById(Long memberId, Pageable pageable);

    @Query(
            value = "select r1 " +
                    "from Review r1 " +
                    "join Member m on r1.member.id = m.id " +
                    "join Store s on r1.store.id = s.id " +
                    "left join Reply r2 on r1.reply.id = r2.id " +
                    "where m.id = :memberId and s.id = :storeId and r1.id < :idCursor " +
                    "order by r1.id desc "
    )
    Slice<Review> findMyReviewsWithId(Long memberId, Long storeId, Long idCursor, Pageable pageable);

    @Query(
            value = "select r1 " +
                    "from Review r1 " +
                    "join Member m on r1.member.id = m.id " +
                    "join Store s on r1.store.id = s.id " +
                    "left join Reply r2 on r1.reply.id = r2.id " +
                    "where m.id = :memberId and r1.id < :idCursor " +
                    "order by r1.id desc "
    )
    Slice<Review> findMyReviewsWithId(Long memberId, Long idCursor, Pageable pageable);

    @Query(
            value = "select r1 " +
                    "from Review r1 " +
                    "join Member m on r1.member.id = m.id " +
                    "join Store s on r1.store.id = s.id " +
                    "left join Reply r2 on r1.reply.id = r2.id " +
                    "where m.id = :memberId and s.id = :storeId " +
                    "order by r1.star desc "
    )
    Slice<Review> findMyReviewsOrderByStar(Long memberId, Long storeId, Pageable pageable);

    @Query(
            value = "select r1 " +
                    "from Review r1 " +
                    "join Member m on r1.member.id = m.id " +
                    "join Store s on r1.store.id = s.id " +
                    "left join Reply r2 on r1.reply.id = r2.id " +
                    "where m.id = :memberId " +
                    "order by r1.star desc "
    )
    Slice<Review> findMyReviewsOrderByStar(Long memberId, Pageable pageable);

    @Query(
            value = "select r1 " +
                    "from Review r1 " +
                    "join Member m on r1.member.id = m.id " +
                    "join Store s on r1.store.id = s.id " +
                    "left join Reply r2 on r1.reply.id = r2.id " +
                    "where m.id = :memberId and s.id = :storeId " +
                    "and (r1.star < :prevCursor or (r1.star = :prevCursor and r1.id < :idCursor)) " +
                    "order by r1.star desc, r1.id desc "
    )
    Slice<Review> findMyReviewsWithStar(Long memberId, Long storeId, BigDecimal prevCursor, Long idCursor, Pageable pageable);

    @Query(
            value = "select r1 " +
                    "from Review r1 " +
                    "join Member m on r1.member.id = m.id " +
                    "join Store s on r1.store.id = s.id " +
                    "left join Reply r2 on r1.reply.id = r2.id " +
                    "where m.id = :memberId " +
                    "and (r1.star < :prevCursor or (r1.star = :prevCursor and r1.id < :idCursor)) " +
                    "order by r1.star desc, r1.id desc "
    )
    Slice<Review> findMyReviewsWithStar(Long memberId, BigDecimal prevCursor, Long idCursor, Pageable pageable);
}