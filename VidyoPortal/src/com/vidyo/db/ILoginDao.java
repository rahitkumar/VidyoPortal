package com.vidyo.db;

import java.util.List;

import com.vidyo.bo.ForgotPassword;

public interface ILoginDao {
	public List<ForgotPassword> getMembersForEmail(String email, int tenantID,
			List<String> userTypes);

	public int updateMemberPassKey(int memberID, String passKey);

	public List<ForgotPassword> getMemberForPassKey(String passKey);

	public int updateMemberPassword(int memberID, String newPassword);
}