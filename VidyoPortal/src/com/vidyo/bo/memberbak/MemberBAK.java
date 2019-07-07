package com.vidyo.bo.memberbak;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "member_bak")
public class MemberBAK implements Serializable {

	private static final long serialVersionUID = 1519301997431859597L;

	@Id
	@Column(name = "member_bak_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int memberBakId;

	@Column(name = "member_id")
	private int memberId;

	@Column(name = "bak")
	private String bak;

	@Column(name = "bak_type")
	private String bakType;

	@Column(name = "creation_time")
	private Date creationTime;

	public int getMemberBakId() {
		return memberBakId;
	}

	public void setMemberBakId(int memberBakId) {
		this.memberBakId = memberBakId;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getBak() {
		return bak;
	}

	public void setBak(String bak) {
		this.bak = bak;
	}

	public String getBakType() {
		return bakType;
	}

	public void setBakType(String bakType) {
		this.bakType = bakType;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

}