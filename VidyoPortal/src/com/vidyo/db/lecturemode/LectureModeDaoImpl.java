package com.vidyo.db.lecturemode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.Collections;
import java.util.List;

public class LectureModeDaoImpl extends NamedParameterJdbcDaoSupport implements  LectureModeDao {
	protected final Logger logger = LoggerFactory.getLogger(LectureModeDaoImpl.class.getName());

	public static final String RAISE_HAND_MEMBER = "UPDATE Conferences c " +
			"INNER JOIN Endpoints e ON e.endpointGUID = c.GUID " +
			"SET c.handRaised = 1, c.handRaisedTime = NOW() " +
			"WHERE e.status != 0 AND e.memberID = :memberID AND e.memberType = 'R'";

	public static final String UNRAISE_HAND_MEMBER = "UPDATE Conferences c " +
			"INNER JOIN Endpoints e ON e.endpointGUID = c.GUID " +
			"SET c.handRaised = 0 " +
			"WHERE e.status != 0 AND e.memberID = :memberID AND e.memberType = 'R'";

	public static final String RAISE_HAND_GUEST = "UPDATE Conferences c " +
			"INNER JOIN Endpoints e ON e.endpointGUID = c.GUID " +
			"INNER JOIN Guests g ON g.guestID = e.memberID " +
			"SET c.handRaised = 1, c.handRaisedTime = NOW() " +
			"WHERE e.status != 0 AND e.memberID = :guestID AND e.memberType = 'G' AND g.username = :username";

	public static final String UNRAISE_HAND_GUEST = "UPDATE Conferences c " +
			"INNER JOIN Endpoints e ON e.endpointGUID = c.GUID " +
			"INNER JOIN Guests g ON g.guestID = e.memberID " +
			"SET c.handRaised = 0 " +
			"WHERE e.status != 0 AND e.memberID = :guestID AND e.memberType = 'G' AND g.username = :username";

	@Override
	public int raiseHandForMember(int memberID) {
		int rowsUpdated = 0;
		SqlParameterSource updateParameters = new MapSqlParameterSource()
				.addValue("memberID", memberID);
		try {
			rowsUpdated = getNamedParameterJdbcTemplate().update(RAISE_HAND_MEMBER, updateParameters);
		} catch (DataAccessException dae) {
			logger.info("SQL Exception raising hand for memberID: " + memberID + ", message: " + dae.getMessage());
		}

		return rowsUpdated;
	}

	@Override
	public int unraiseHandForMember(int memberID) {
		int rowsUpdated = 0;
		SqlParameterSource updateParameters = new MapSqlParameterSource()
				.addValue("memberID", memberID);

		try {
			rowsUpdated = getNamedParameterJdbcTemplate().update(UNRAISE_HAND_MEMBER, updateParameters);
		} catch (DataAccessException dae) {
			logger.info("SQL Exception raising hand for memberID: " + memberID + ", message: " + dae.getMessage());
		}

		return rowsUpdated;

	}

	@Override
	public int raiseHandForGuest(int guestID, String username) {
		int rowsUpdated = 0;
		SqlParameterSource updateParameters = new MapSqlParameterSource()
				.addValue("guestID", guestID)
				.addValue("username", username);

		try {
			rowsUpdated = getNamedParameterJdbcTemplate().update(RAISE_HAND_GUEST, updateParameters);
		} catch (DataAccessException dae) {
			logger.info("SQL Exception raising hand for guest: " + username + ", message: " + dae.getMessage());
		}

		return rowsUpdated;
	}

	@Override
	public int unraiseHandForGuest(int guestID, String username) {
		int rowsUpdated = 0;
		SqlParameterSource updateParameters = new MapSqlParameterSource()
				.addValue("guestID", guestID)
				.addValue("username", username);

		try {
			rowsUpdated = getNamedParameterJdbcTemplate().update(UNRAISE_HAND_GUEST, updateParameters);
		} catch (DataAccessException dae) {
			logger.info("SQL Exception raising hand for guest: " + username + ", message: " + dae.getMessage());
		}

		return rowsUpdated;
	}

	public static final String UPDATE_LECTURE_MODE_FLAG = "UPDATE Room SET lectureMode = :flag WHERE roomID = :roomID";

	@Override
	public int setLectureModeFlag(int roomID, boolean flag) {
		int rowsUpdated = 0;
		int flagValue = (flag ? 1 : 0);
		SqlParameterSource updateParameters = new MapSqlParameterSource()
				.addValue("roomID", roomID)
				.addValue("flag", flagValue);

		try {
			rowsUpdated = getNamedParameterJdbcTemplate().update(UPDATE_LECTURE_MODE_FLAG, updateParameters);
		} catch (DataAccessException dae) {
			logger.info("SQL Exception setting lecture mode for roomID: " + roomID + ", message: " + dae.getMessage());
		}

		return rowsUpdated;
	}

	public static final String CLEAR_LECTURE_MODE_STATE = "UPDATE Conferences SET handRaised = 0, handRaisedTime = NULL, presenter = 0 WHERE roomID = :roomID";

	@Override
	public int clearLectureModeState(int roomID) {
		int rowsUpdated = 0;
		SqlParameterSource updateParameters = new MapSqlParameterSource()
				.addValue("roomID", roomID);

		try {
			rowsUpdated = getNamedParameterJdbcTemplate().update(CLEAR_LECTURE_MODE_STATE, updateParameters);
		} catch (DataAccessException dae) {
			logger.info("SQL Exception clearing lecture mode state for roomID: " + roomID + ", message: " + dae.getMessage());
		}

		return rowsUpdated;
	}


	public static final String GET_ENDPOINTGUIDS_IN_CONFERENCE = "SELECT GUID FROM Conferences WHERE roomID = :roomID";

	@Override
	public List<String> getEndpointGUIDsInConference(int roomID) {
		List<String> guids = Collections.<String>emptyList();
		SqlParameterSource selectParams = new MapSqlParameterSource()
				.addValue("roomID", roomID);

		try {
			guids = getNamedParameterJdbcTemplate().queryForList(GET_ENDPOINTGUIDS_IN_CONFERENCE, selectParams, String.class);
		} catch (DataAccessException dae) {
			logger.info("SQL Exception lecture mode guids in roomID: " + roomID + ", message: " + dae.getMessage());
		}
		return guids;
	}

	public static final String GET_ENDPOINT_GUIDS_WITH_HANDS_RAISED = "SELECT GUID FROM Conferences WHERE roomID = :roomID and handRaised = 1";

	@Override
	public List<String> getEndpointGUIDsWithHandsRaised(int roomID) {
		List<String> guids = Collections.<String>emptyList();
		SqlParameterSource selectParams = new MapSqlParameterSource()
				.addValue("roomID", roomID);

		try {
			guids = getNamedParameterJdbcTemplate().queryForList(GET_ENDPOINT_GUIDS_WITH_HANDS_RAISED, selectParams, String.class);
		} catch (DataAccessException dae) {
			logger.info("SQL Exception lecture mode hands raised in roomID: " + roomID + ", message: " + dae.getMessage());
		}
		return guids;
	}

	public static final String DISMISS_ALL_HANDS = "UPDATE Conferences SET handRaised = 0, handRaisedTime = NULL WHERE roomID = :roomID";

	@Override
	public int dismissAllHands(int roomID) {
		int rowsUpdated = 0;
		SqlParameterSource updateParameters = new MapSqlParameterSource()
				.addValue("roomID", roomID);

		try {
			rowsUpdated = getNamedParameterJdbcTemplate().update(DISMISS_ALL_HANDS, updateParameters);
		} catch (DataAccessException dae) {
			logger.info("SQL Exception dismissing all hands for roomID: " + roomID + ", message: " + dae.getMessage());
		}

		return rowsUpdated;
	}

	public static final String DISMISS_HAND = "UPDATE Conferences SET handRaised = 0, handRaisedTime = NULL WHERE GUID = :guid";

	@Override
	public int dismissHand(String handRaisedGUID) {
		int rowsUpdated = 0;
		SqlParameterSource updateParameters = new MapSqlParameterSource()
				.addValue("guid", handRaisedGUID);

		try {
			rowsUpdated = getNamedParameterJdbcTemplate().update(DISMISS_HAND, updateParameters);
		} catch (DataAccessException dae) {
			logger.info("SQL Exception dismissing hand for GUID: " + handRaisedGUID + ", message: " + dae.getMessage());
		}

		return rowsUpdated;
	}

	public static final String GET_CURRENT_PRESENTER = "SELECT GUID from Conferences WHERE presenter = 1 AND roomID = :roomID";

	@Override
	public String getCurrentPresenterGUID(int roomID) {
		String presenterGuid = null;
		SqlParameterSource selectParams = new MapSqlParameterSource()
				.addValue("roomID", roomID);

		try {
			presenterGuid = (String) getNamedParameterJdbcTemplate().queryForObject(GET_CURRENT_PRESENTER, selectParams, String.class);
		} catch (DataAccessException dae) {
			logger.info("SQL Exception getting presenter for roomID: " + roomID + ", message: " + dae.getMessage());
		}
		return presenterGuid;
	}


	public static final String SET_PRESENTER = "UPDATE Conferences SET presenter = :flag WHERE GUID = :guid";

	@Override
	public int setPresenterFlag(String currentPresenterGUID, boolean flag) {
		int rowsUpdated = 0;
		int flagValue = (flag ? 1 : 0);
		SqlParameterSource updateParameters = new MapSqlParameterSource()
				.addValue("flag", flagValue)
				.addValue("guid", currentPresenterGUID);

		try {
			rowsUpdated = getNamedParameterJdbcTemplate().update(SET_PRESENTER, updateParameters);
		} catch (DataAccessException dae) {
			logger.info("SQL Exception setting lecture mode presenter flag: " + flagValue + ", for GUID: " + currentPresenterGUID + ", message: " + dae.getMessage());
		}

		return rowsUpdated;
	}


	public static final String GET_PARTICIPANTID_FOR_GUID = "SELECT participantID from Conferences WHERE GUID = :guid";

	@Override
	public String getRouterParticipantIDForGUID(String newPresenterGUID) {
		String participantID = null;
		SqlParameterSource selectParams = new MapSqlParameterSource()
				.addValue("guid", newPresenterGUID);

		try {
			participantID = (String) getNamedParameterJdbcTemplate().queryForObject(GET_PARTICIPANTID_FOR_GUID, selectParams, String.class);
		} catch (DataAccessException dae) {
			logger.info("SQL Exception getting router participantID for GUID: " + newPresenterGUID + ", message: " + dae.getMessage());
		}
		return participantID;
	}

    public static final String GET_ENDPOINTS_WITHOUT_LECTURE_MODE_SUPPORT = "SELECT GUID FROM Conferences c, " +
    "Endpoints e WHERE c.roomID = :roomID and c.GUID = e.endpointGUID and e.lectureModeSupport = 0";
    // only join Endpoints table as VirtualEndpoints support lectureMode and recorderEndpoints can be ignored


    @Override
    public List<String> getEndpointsWithoutLectureModeSupportInRoom(int roomID) {
        List<String> guids = Collections.<String>emptyList();
        SqlParameterSource selectParams = new MapSqlParameterSource()
                .addValue("roomID", roomID);

        try {
            guids = getNamedParameterJdbcTemplate().queryForList(GET_ENDPOINTS_WITHOUT_LECTURE_MODE_SUPPORT, selectParams, String.class);
        } catch (DataAccessException dae) {
            logger.info("SQL Exception lecture mode unsupported endpoint guids in roomID: " + roomID + ", message: " + dae.getMessage());
        }
        return guids;
    }

    public static final String GET_COUNT_ENDPOINTS_WITHOUT_LECTURE_MODE_SUPPORT = "SELECT count(GUID) FROM Conferences c, " +
            "Endpoints e WHERE c.roomID = :roomID and c.GUID = e.endpointGUID and e.lectureModeSupport = 0";
    // only join Endpoints table as VirtualEndpoints support lectureMode and recorderEndpoints can be ignored


    @Override
    public int getCountEndpointsWithoutLectureModeSupportInRoom(int roomID) {

        SqlParameterSource selectParams = new MapSqlParameterSource()
                .addValue("roomID", roomID);

        try {
            return getNamedParameterJdbcTemplate().queryForObject(GET_COUNT_ENDPOINTS_WITHOUT_LECTURE_MODE_SUPPORT, selectParams, Integer.class);
        } catch (DataAccessException dae) {
            logger.info("SQL Exception count lecture mode unsupported endpoint guids in roomID: " + roomID + ", message: " + dae.getMessage());
        }

        return 0;
    }
}
