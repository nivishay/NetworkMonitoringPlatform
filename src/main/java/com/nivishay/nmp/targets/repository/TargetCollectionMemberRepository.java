package com.nivishay.nmp.targets.repository;

import com.nivishay.nmp.targets.api.dto.TargetCollectionResponse;
import com.nivishay.nmp.targets.api.dto.TargetCollectionResponse;
import com.nivishay.nmp.targets.api.dto.TargetInCollectionResponse;
import com.nivishay.nmp.targets.domain.TargetCollectionMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface TargetCollectionMemberRepository
        extends JpaRepository<TargetCollectionMember, TargetCollectionMember.Id> {

    @Query("""
        select new com.nivishay.nmp.targets.api.dto.TargetInCollectionResponse(t.id, t.url)
        from TargetCollectionMember m
        join Target t on t.id = m.id.targetId
        where m.id.collectionId = :collectionId
    """)
    List<TargetInCollectionResponse> findTargetsByCollectionId(UUID collectionId);

    void deleteByIdCollectionIdAndIdTargetId(UUID collectionId, UUID targetId);
}