package com.vidyo.db.repository.member;

import com.vidyo.bo.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * MemberRepository for repository operations on Member DTO
 */
@Repository
public interface MemberRepository extends JpaRepository<Member, Integer> {

    Member findByMemberIDAndTenantID(Integer memberId, Integer tenantId);

    Member findByMemberID(Integer memberId);

}
