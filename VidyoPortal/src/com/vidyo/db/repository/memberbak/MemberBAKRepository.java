package com.vidyo.db.repository.memberbak;

import com.vidyo.bo.memberbak.MemberBAK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
public interface MemberBAKRepository extends JpaRepository<MemberBAK, Integer> {

    public MemberBAK findByBakAndBakType(String bak, String bakType);

    @Transactional
    public void deleteByBak(String bak);

    @Modifying
    @Transactional
    @Query("delete from MemberBAK m WHERE m.creationTime <= ?1")
    public int deleteByCreationTimeBefore(Date deletionTime);

    /**
     * Derived Delete Query by MemberId
     * @return
     */
    @Modifying
    @Transactional
    @Query("delete from MemberBAK m WHERE m.memberId = ?1")
    public int deleteByMemberId(int memberId);

}