package com.example.umc10th.domain.mission.service;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.repository.MemberRepository;
import com.example.umc10th.domain.mission.converter.MemberMissionConverter;
import com.example.umc10th.domain.mission.converter.MissionConverter;
import com.example.umc10th.domain.mission.dto.req.MissionReqDTO;
import com.example.umc10th.domain.mission.dto.res.MissionResDTO;
import com.example.umc10th.domain.mission.entity.Mission;
import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.domain.mission.entity.mapping.MemberMission;
import com.example.umc10th.domain.mission.enums.Address;
import com.example.umc10th.domain.mission.exception.MissionException;
import com.example.umc10th.domain.mission.exception.StoreException;
import com.example.umc10th.domain.mission.exception.code.MissionErrorCode;
import com.example.umc10th.domain.mission.exception.code.StoreErrorCode;
import com.example.umc10th.domain.mission.repository.MemberMissionRepository;
import com.example.umc10th.domain.mission.repository.MissionRepository;
import com.example.umc10th.domain.mission.repository.StoreRepository;
import com.example.umc10th.global.converter.GeneralConverter;
import com.example.umc10th.global.dto.GeneralDTO;
import com.example.umc10th.global.security.entity.AuthMember;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MissionService {

    private final StoreRepository storeRepository;
    private final MissionRepository missionRepository;
    private final MemberMissionRepository memberMissionRepository;
    private final MemberRepository memberRepository;

    // 가게 미션 생성
    @Transactional
    public Void createMission(
            Long storeId,
            MissionReqDTO.CreateMission dto
    ) {
        // 가게 찾기
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new StoreException(StoreErrorCode.NOT_FOUND));

        // 미션 생성
        Mission mission = MissionConverter.toMission(store, dto);

        // 미션 DB 저장
        missionRepository.save(mission);
        return null;
    }

    // 지역 내 도전하기 전 미션 조회
    public GeneralDTO.Pagination<MissionResDTO.GetMission> getMissions(
            Address address,
            AuthMember member,
            Integer pageSize,
            String cursor
    ) {
        // 커서 분해
        Long idCursor = null;
        if (!cursor.equals("-1")){
            try {
                idCursor = Long.parseLong(cursor);
            } catch (NumberFormatException e) {
                throw new MissionException(MissionErrorCode.NOT_VALID_CURSOR);
            }
        }

        // 결과 조회
        PageRequest pageRequest = PageRequest.ofSize(pageSize);
        Slice<Mission> missionList;
        if (idCursor != null){
            missionList = missionRepository.findLocationMissionWithCursor(address.name(), member.getMember().getId(), idCursor, pageRequest);
        } else {
            missionList = missionRepository.findLocationMission(address.name(), member.getMember().getId(), pageRequest);
        }

        if (!missionList.hasContent()){
            return GeneralConverter.toPagination(
                    List.of(),
                    false,
                    "-1",
                    pageSize,
                    "id"
            );
        }

        String nextCursor = missionList.getContent().getLast().getId().toString();
        return GeneralConverter.toPagination(
                missionList.getContent().stream()
                        .map(MissionConverter::toGetMission)
                        .toList(),
                missionList.hasNext(),
                nextCursor,
                missionList.getSize(),
                "id"
        );
    }

    // 내가 도전중인, 완료한 미션 조회
    public GeneralDTO.Pagination<MissionResDTO.GetMyMission> getMyMissions(
            AuthMember authMember,
            Boolean isCompleted,
            Integer pageSize,
            String cursor
    ) {

        Member member = authMember.getMember();

        // PageRequest 생성
        PageRequest pageRequest = PageRequest.ofSize(pageSize);

        // 커서 변환
        Long idCursor;
        if (!cursor.equals("-1")){
            try {
                idCursor = Long.parseLong(cursor);
            } catch (NumberFormatException e) {
                throw new MissionException(MissionErrorCode.NOT_VALID_CURSOR);
            }
        } else {
            idCursor = null;
        }

        // 완료된 미션 조회
        Slice<MemberMission> missionList;
        if (idCursor != null){
            missionList = memberMissionRepository
                    .findAllByIsCompleteAndMemberAndIdLessThan(isCompleted, member, idCursor, pageRequest);
        } else {
            missionList = memberMissionRepository
                    .findAllByIsCompleteAndMember(isCompleted, member, pageRequest);
        }

        if (!missionList.hasContent()){
            return GeneralConverter.toPagination(
                    List.of(),
                    false,
                    "-1",
                    pageSize,
                    "id"
            );
        }

        String nextCursor = missionList.getContent().getLast().getId().toString();
        return GeneralConverter.toPagination(
                missionList.getContent().stream()
                        .map(MemberMissionConverter::toGetMyMission)
                        .toList(),
                missionList.hasNext(),
                nextCursor,
                missionList.getSize(),
                "id"
        );
    }

    // 미션 도전하기
    @Transactional
    public MissionResDTO.TryMission tryMission(
            Long missionId,
            AuthMember authMember
    ) {
        // 미션 조회
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new MissionException(MissionErrorCode.NOT_FOUND));

        // 사용자
        Member member = authMember.getMember();

        // 이미 도전중인지 확인
        if (memberMissionRepository.existsByMissionAndMember(mission, member)){
            throw new MissionException(MissionErrorCode.ALREADY_TRY);
        }

        // 사용자미션 만들기
        MemberMission memberMission = MemberMissionConverter.toMemberMission(member, mission);

        // 미션 저장하기
        memberMissionRepository.save(memberMission);

        return MemberMissionConverter.toTryMission(memberMission.getMission());
    }

    // 미션 성공 요청 보내기 (단순 전환)
    @Transactional
    public MissionResDTO.CompleteMission completeMission(
            Long missionId,
            AuthMember authMember
    ) {
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new MissionException(MissionErrorCode.NOT_FOUND));

        Member member = memberRepository.findById(authMember.getMember().getId())
                .orElseThrow(() -> new MissionException(MissionErrorCode.NOT_FOUND));

        // 진행중인 미션인지 확인
        MemberMission memberMission = memberMissionRepository.findByMemberAndMission(member, mission)
                .orElseThrow(() -> new MissionException(MissionErrorCode.NOT_TRY));
        if (memberMission.getIsComplete()){
            throw new MissionException(MissionErrorCode.NOT_TRY);
        }

        // 진행 완료로 전환 + 포인트 추가
        memberMission.complete();
        member.updatePoint(mission.getPoint());

        return MissionConverter.toCompleteMission(mission);
    }
}
