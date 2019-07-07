/**
 * 
 */
package com.vidyo.utils.room;

import com.vidyo.bo.TenantConfiguration;
import com.vidyo.db.IRoomDao;
import com.vidyo.db.TenantConfigurationDao;
import com.vidyo.service.room.ScheduledRoomResponse;
import com.vidyo.utils.Generator;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;


/**
 * Generates unique room extension for Scheduled Rooms. It also takes care of
 * persistence across server restarts as well.
 * 
 * @author Ganesh
 * 
 */
public class RoomExtnGenerator implements Generator {
	
	/**
	 * 
	 */
	protected static final Logger logger = LoggerFactory.getLogger(RoomExtnGenerator.class);

	/**
	 * Unique Room Extension Id generation based on time in milliseconds
	 */
	private AtomicLong atomicLong = new AtomicLong(System.currentTimeMillis());

	/**
	 * 
	 */
	private Random random = new Random(System.currentTimeMillis());

	/**
	 * 
	 */
	private int min = 1;

	/**
	 * 
	 */
	private int max = 65535;


	private UniformRandomProvider rng = RandomSource.create(RandomSource.SPLIT_MIX_64);


	public TenantConfigurationDao getTenantConfigurationDao() {
		return tenantConfigurationDao;
	}

	public void setTenantConfigurationDao(TenantConfigurationDao tenantConfigurationDao) {
		this.tenantConfigurationDao = tenantConfigurationDao;
	}

	private TenantConfigurationDao tenantConfigurationDao;

	private IRoomDao roomDao;


	public IRoomDao getRoomDao() {
		return roomDao;
	}

	public void setRoomDao(IRoomDao roomDao) {
		this.roomDao = roomDao;
	}


	/**
	 * 
	 */
	@Override
	public String generate() {
		return String.valueOf(atomicLong.incrementAndGet());
	}


	/**
	 * 
	 * @param memberId
	 * @param randNumb
	 * @return
	 */
	public String generateSchRoomExtnWithPin(int memberId, int randNumb) {
		logger.debug("MemberId -{}, Random - {}", memberId, randNumb);
		// Convert the integer to binary equivalent and pad zeros
		String binary = Integer.toBinaryString(randNumb);
		String randomBinPadded = String.format("%16s", binary).replace(" ", "0");
		//logger.debug("randomBinPadded->"+randomBinPadded);
		String memberBin = Integer.toBinaryString(memberId);
		String memberBinPadded = String.format("%16s", memberBin).replace(" ", "0");
		//logger.debug("memberBinPadded->"+memberBinPadded);
		// Convert the binary string to character array
		char[] randomNumbBinArr = randomBinPadded.toCharArray();
		char[] memberIdBinArr = memberBinPadded.toCharArray();
		char[] pinArr = new char[16];
		// Calculate pin value - mixing the bits of memberId and random
		for (int i = 0; i < pinArr.length; i = i + 2) {
			pinArr[i] = randomNumbBinArr[i];
		}
		for (int i = 1; i < pinArr.length; i = i + 2) {
			pinArr[i] = memberIdBinArr[i];
		}
		// calculate Extension value - mixing the bits of memberId and random
		char[] extnArr = new char[memberIdBinArr.length];
		for (int i = 0; i < 16; i = i + 2) {
			extnArr[i] = memberIdBinArr[i];
		}
		for (int i = 1; i < 16; i = i + 2) {
			extnArr[i] = randomNumbBinArr[i];
		}
		
		if(memberIdBinArr.length > 11) {
			for(int i = 16; i < memberIdBinArr.length; i++) {
				extnArr[i] = memberIdBinArr[i];
			}
		}
		
		//logger.debug("Extn value before modification ->"+ String.valueOf(extnArr));
		String extGen = String.valueOf(extnArr) + 1;
		//logger.debug("Extn value after modification  ->"+ extGen);
		int extnVal = Integer.parseInt(String.valueOf(extGen), 2);
		int pinVal = Integer.parseInt(String.valueOf(pinArr), 2);
		
		//Add a factor to PIN number - to make PIN number 4 digits
		pinVal += 3459;
		
		// Modify the Extension Value

		String value = String.valueOf(extnVal) + "-" + pinVal;
		return value;

	}

	/**
	 * Generates a random number range - 1 to 65535
	 */
	public int generateRandom() {
		// Initialize Random every time
		random = new Random();
		int randNumb = random.nextInt(max) + min;
		return randNumb;
	}

	/**
	 * Decrypts the scheduled room extension & pin and returns back the original extension and pin. This can handle only
	 * extension of length of 4 digits.
	 * 
	 * @param extn
	 * @param pin
	 * @return
	 */
	public ScheduledRoomResponse decryptShceduledRoom(String ext, String pin) {
		logger.debug("Decrypt the Scheduled Room Extn {}, Pin {}", ext, pin);

		if (ext == null) {
			ScheduledRoomResponse scheduledRoomResponse = new ScheduledRoomResponse();
			scheduledRoomResponse.setStatus(ScheduledRoomResponse.INVALID_PIN_EXTN);
			return scheduledRoomResponse;
		}

		String extBin = null;

		try {
			extBin = Long.toBinaryString(Long.valueOf(ext));
		} catch (NumberFormatException e) {
			logger.error("Extension Value is invalid {}", ext);
		}

		if (extBin == null) {
			ScheduledRoomResponse scheduledRoomResponse = new ScheduledRoomResponse();
			scheduledRoomResponse.setStatus(ScheduledRoomResponse.INVALID_PIN_EXTN);
			return scheduledRoomResponse;
		}

		// Check the last bit of extension to identify if PIN is required or not
		// logger.debug("extBin->"+extBin);
		boolean isPinReqd = (extBin.substring(extBin.length() - 1).equalsIgnoreCase("1"));

		if (isPinReqd && (pin == null || pin.isEmpty())) {
			ScheduledRoomResponse scheduledRoomResponse = new ScheduledRoomResponse();
			scheduledRoomResponse.setStatus(ScheduledRoomResponse.WRONG_SCHEDULED_ROOM_PIN);
			return scheduledRoomResponse;
		}

		String pinBin = null;
		if (isPinReqd && pin != null && !pin.isEmpty()) {
			try {
				// Subtract the 3459 factor from the PIN
				pinBin = Long.toBinaryString((Long.valueOf(pin) - 3459));
			} catch (NumberFormatException e) {
				logger.error("Pin Value is invalid {}", ext);
			}
			if (pinBin == null) {
				ScheduledRoomResponse scheduledRoomResponse = new ScheduledRoomResponse();
				scheduledRoomResponse.setStatus(ScheduledRoomResponse.WRONG_SCHEDULED_ROOM_PIN);
				return scheduledRoomResponse;
			}
		}

		ScheduledRoomResponse scheduledRoomResponse = new ScheduledRoomResponse();
		if (isPinReqd) {
			logger.debug("extBin {}", extBin);
			extBin = extBin.substring(0, extBin.length() - 1);
			// Scheduled Room supports only 16 bits

			logger.debug("extBin {}", extBin);

			if (pinBin != null && pinBin.length() > 16) {
				pinBin = pinBin.substring(0, 16);
			}
			String extBinPadded = String.format("%16s", extBin).replace(" ", "0");
			String pinBinPadded = String.format("%16s", pinBin).replace(" ", "0");
			char[] extBinArr = extBinPadded.toCharArray();
			char[] pinBinArr = pinBinPadded.toCharArray();
			char[] extnArr = new char[extBinArr.length];
			char[] pinArr = new char[16];

			for (int i = 0; i < 16; i = i + 2) {
				pinArr[i] = pinBinArr[i];
			}

			for (int i = 1; i < 16; i = i + 2) {
				pinArr[i] = extBinArr[i];
			}

			for (int i = 0; i < pinBinArr.length; i = i + 2) {
				extnArr[i] = extBinArr[i];
			}
			for (int i = 1; i < 16; i = i + 2) {
				extnArr[i] = pinBinArr[i];
			}

			if (extBinArr.length > 16) {
				for (int i = 16; i < extBinArr.length; i++) {
					extnArr[i] = extBinArr[i];
				}
			}

			logger.debug("extnArr -{}", Arrays.toString(extnArr));
			long extnOrig = Long.parseLong(String.valueOf(extnArr), 2);
			long pinOrig = Long.parseLong(String.valueOf(pinArr), 2);
			scheduledRoomResponse.setPin(pinOrig);
			scheduledRoomResponse.setRoomExtn(String.valueOf(extnOrig));
		} else {
			// If no PIN, remove the PIN bit and convert to the original number
			logger.debug("final extbin -{}", extBin);
			long roomExtn = Long.valueOf(extBin, 2);
			scheduledRoomResponse.setRoomExtn(String.valueOf(roomExtn));
		}

		logger.debug("Decrypted the Scheduled Room Extn -{}, Pin -{}", scheduledRoomResponse.getRoomExtn(),
				scheduledRoomResponse.getPin());
		return scheduledRoomResponse;
	}
	
	/**
	 * 
	 * @param memberId
	 * @param randNumb
	 * @return
	 */
	public String generateSchRoomExtnWithoutPin(int memberId, int randNumb) {
		logger.debug("MemberId -{}, Random - {}", memberId, randNumb);
		// Convert the integer to binary equivalent and pad zeros
		String binary = Long.toBinaryString(randNumb);
		String randomBinPadded = String.format("%16s", binary).replace(" ", "0");
		String memberBin = Long.toBinaryString(memberId);
		// Last bit indicating no PIN
		//logger.debug("value before adding pin flag ->"+Integer.valueOf("11001000101101001111111111111111", 2));
		String combinedVal = memberBin + randomBinPadded + "0";
		logger.debug("combinedVal {}", combinedVal);
		long convertedVal = Long.valueOf(combinedVal, 2);
		logger.debug("Extension value {}", convertedVal);
		return String.valueOf(convertedVal);
	}

	/**
	 * Generate the Room Extn using the MIN length required (based on SYSTEM level setting)
	 * @param memberId
	 * @param roomExtnLen
	 * @return
	 */
		//@Deprecated
	/*public String generateRandomRoomExtn (int roomExtnLen) {
		logger.debug("roomExtnLen - {}", roomExtnLen);

		String roomExtn = SecureRandomUtils.generateRoomExtn(roomExtnLen);
		if (roomExtn != null && roomExtn.length() < roomExtnLen){
			int remaining = roomExtnLen - roomExtn.length();
			StringBuilder strBuild = new StringBuilder();
			while (remaining-- > 0){
				strBuild.append(random.nextInt(8) + 1);
			}
			roomExtn = roomExtn.concat(strBuild.toString());
		}
	    logger.debug("Generated roomExtn - ", roomExtn);
	    return roomExtn;
	}*/

		/**
		 * @param length
		 * @return RoomExtension
		 */
		public String generateRandomRoomExtn(int tenantId, String tenantPrefix) {
			logger.debug("Came inside the generateRandomRoomExtn method with params :: tenantId :: "+tenantId+" tenantPrefix :: "+tenantPrefix);
			tenantPrefix = StringUtils.defaultIfBlank(tenantPrefix, "");
			 TenantConfiguration tenantConfiguration = tenantConfigurationDao.getTenantConfiguration(tenantId);
			 int extnLength = tenantConfiguration.getExtnLength();

			if (reachedThreshold(tenantId, extnLength, tenantPrefix, tenantConfiguration.getRoomCountThreshold())) {
				logger.info("Threshold reached for tenantId :: "+tenantId+" length :: " + extnLength);
				roomDao.clearRoomCounterCache(tenantId);
				validateExtnLength(extnLength);
				extnLength = extnLength + 1;
				tenantConfiguration.setExtnLength(extnLength);
				tenantConfigurationDao.updateTenantConfiguration(tenantId, tenantConfiguration);
			}
			long randomNumber;

			int retryTimes = 0;
			do {
				randomNumber = generateRandomNumber(extnLength);
				++retryTimes;
				if (reachedRetryCountThreshold(extnLength, retryTimes ) ){
					logger.info("Retry count threshold exceeded. Extension length will be incremented");
					validateExtnLength(extnLength);
					extnLength = extnLength + 1;
					tenantConfiguration.setExtnLength(extnLength);
					tenantConfigurationDao.updateTenantConfiguration(tenantId, tenantConfiguration);
				}
			} while (roomDao.isRoomExistForRoomExtNumber(tenantPrefix+randomNumber,0));

			return Long.toString(randomNumber);
		}

	private long generateRandomNumber(int extnLength) {
		double random = rng.nextDouble();
		long randomNumber = Math.round(random * (long)Math.pow(10, extnLength));
		// If the random number generated is less than the expected, then multiply by appropriate number to increase the length.
		if (randomNumber < (long) Math.pow(10,extnLength - 1)){
			long retLength = (long) (Math.log10(randomNumber))+1;
			randomNumber = (long)(randomNumber * Math.pow(10, (extnLength - retLength)));
		}
		return randomNumber;
	}

	private boolean reachedThreshold(int tenantId, int extnLength, String tenantPrefix, double roomCountThreholdInPercentage) {
		logger.debug("Inside the reachedThreshold method with params :: tenantId "+tenantId+ " extnLength ::"+extnLength+" tenantPrefix :: "+tenantPrefix+ " roomCountThresholdInPercentage ::"+roomCountThreholdInPercentage);
		long threshold;
		long maxNumberForLength = ((long) Math.pow(10,extnLength)) - 1;
		long minimumNumberForLength = (long)Math.pow(10,extnLength-1);
		threshold = (long)( (maxNumberForLength - minimumNumberForLength) * ( roomCountThreholdInPercentage / 100 ) );
		long counter = roomDao.getRoomCountByExtnLength(tenantId, tenantPrefix.length() + extnLength);
		logger.debug("Room Count from Cache :: "+counter);
		return counter >= threshold;
	}

	private void validateExtnLength(int extnLength){
			if(extnLength >= 15 ){
				logger.error("Extension length is greater than 15 :: current value ::"+extnLength);
			throw new IllegalStateException("Extension length cannot be greter than 13 digits");
		}
	}

	private boolean reachedRetryCountThreshold(int extnLength, int retryCount){
		boolean flag = false;
		int retryCountThreshold = 5 * extnLength;
		if(retryCount  > retryCountThreshold){
			logger.info("Retry count :: "+retryCount+" exceeded threshold value:: "+ retryCountThreshold);
			flag = true;
		}
		return flag;
	}
}
