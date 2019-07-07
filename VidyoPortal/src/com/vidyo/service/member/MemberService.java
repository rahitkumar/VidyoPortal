/**
 * 
 */
package com.vidyo.service.member;

import java.util.List;

import com.vidyo.bo.Language;
import com.vidyo.bo.Member;
import com.vidyo.bo.MemberFilter;
import com.vidyo.bo.member.MemberEntity;
import com.vidyo.service.exceptions.AccessRestrictedException;
import com.vidyo.service.member.response.MemberManagementResponse;

/**
 * @author Ganesh
 * 
 */
public interface MemberService {

	/**
	 * 
	 * @param memberID
	 * @return
	 */
	public Member getMember(int tenantId, int memberID);

	/**
	 * 
	 * @param memberID
	 * @param tenantID
	 * @return
	 */
	public Language getMemberLang(int memberID, int tenantID);
	
	/**
	 * 
	 * @param memberId
	 * @return
	 */
	public MemberEntity getMemberEntity(int memberId);
	
	/**
	 * Returns the list of super accounts based on the filter
	 * @param filter
	 * @return
	 */
	public List<Member> getSupers(MemberFilter filter);
	
	/**
	 * Returns the count of super accounts based on the filter
	 * @param filter
	 * @return
	 */
	public Long getCountSupers(MemberFilter filter);
	
	/**
	 * 
	 * @param memberID
	 * @return
	 */
    public Member getSuper(int memberID);
    
    /**
     * 
     * @param member
     * @return
     */
	public int insertSuper(Member member);
	
	/**
	 * 
	 * @param memberID
	 * @return
	 */
	public int deleteSuper(int memberID) throws AccessRestrictedException;
	
	public MemberManagementResponse addMember(int tenantID, Member member, String requestScheme, String requestHost);
	
	public Long getCountMembers(int tenantID, MemberFilter filter);
	
	public int getLicensedExecutive(int tenantID);
	
	public int getLicensedPanorama(int tenantID);
	
	public List<Member> getMembers(int tenantID, MemberFilter filter);
	
	public int updateMember(int tenantID, int memberID, Member member) throws AccessRestrictedException;
	
	public Member getMemberByName(String userName, int tenantID);
	
    /**
     * Duplicate method of IMemberService. All APIs from IMemberservice will be moved to this class
     * @return
     */
    public Long getCountAllSeats();
    

}
