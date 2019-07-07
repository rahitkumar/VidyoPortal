/**
 * 
 */
package com.vidyo.utils.generators;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.owasp.esapi.ESAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByType;

import com.vidyo.service.room.ScheduledRoomResponse;
import com.vidyo.utils.Generator;

/**
 * @author Ganesh
 * 
 */
@SpringApplicationContext({ "classpath:test-config.xml" })
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class ESAPITest {
	
	/**
	 * 
	 */
	protected static final Logger logger = LoggerFactory.getLogger(RoomExtnGeneratorTest.class);

	@SpringBeanByType
	private Generator generator;

	/**
	 * 
	 */
	@Test
	public void testEncodeForHtml() {
		String name = "admin<ganesh>hdjd%kjkjfkf&fkjf'";
		String encodedName = ESAPI.encoder().encodeForHTML(name);
		System.out.println(encodedName);
		
	}

	/**
	 * 
	 */
	@Test
	public void testDecryptShceduledRoom() {
		ScheduledRoomResponse roomResponse = generator.decryptShceduledRoom("120", "1012");
		System.out.println(roomResponse.getPin());
		System.out.println(roomResponse.getRoomExtn());
	}

	/**
	 * 
	 */
	@Test
	public void testGenerateSchRoomExtnWithPin() {
		int memberId = 380;
		int random = 1752;
		String extPin = generator.generateSchRoomExtnWithPin(memberId, random);
		Assert.assertNotNull(extPin);
		String[] extPinVal = extPin.split("-");
		ScheduledRoomResponse scheduledRoomResponse = generator.decryptShceduledRoom(extPinVal[0], extPinVal[1]);
		Assert.assertEquals(memberId, scheduledRoomResponse.getRoomExtn());
		Assert.assertEquals(random, scheduledRoomResponse.getPin());		
	}
	
	/**
	 * 
	 */
	@Test
	public void testGenerateSchRoomExtnNoPin() {
		int memberId = 51380;
		int random = 65535;
		String extPin = generator.generateSchRoomExtnWithoutPin(memberId, random);
		Assert.assertNotNull(extPin);
		ScheduledRoomResponse scheduledRoomResponse = generator.decryptShceduledRoom(extPin, null);
		Assert.assertEquals(extPin, scheduledRoomResponse.getRoomExtn());
		Assert.assertEquals(0, scheduledRoomResponse.getPin());		
	}	

	/**
	 * 
	 */
	@Test
	public void testGenerateMultipleSchRoomExtnWithPin() {
		for (int i = 0; i < 10000; i++) {
			int randNumb = generator.generateRandom();
			Random memberRandom = new Random();
			int memberRand = memberRandom.nextInt(10000) + 1;
			String extPin = generator.generateSchRoomExtnWithPin(memberRand, randNumb);
			logger.debug("extpin - {}", extPin);
			String[] extPinVal = extPin.split("-");
			Assert.assertEquals(1, Integer.valueOf(extPinVal[0]) % 2);
			Assert.assertTrue((Integer.valueOf(extPinVal[1]) > 100));
			ScheduledRoomResponse scheduledRoomResponse = generator.decryptShceduledRoom(extPinVal[0], extPinVal[1]);
			Assert.assertEquals(memberRand, Integer.parseInt(scheduledRoomResponse.getRoomExtn()));
			Assert.assertEquals(randNumb, scheduledRoomResponse.getPin());
		}
	}
	
	/**
	 * 
	 */
	@Test
	public void testGenerateMultipleSchRoomExtnBothCases() {
		for (int i = 0; i < 1000; i++) {
			System.out.println("-----------------------------------------------------");
			int randNumb = generator.generateRandom();
			Random memberRandom = new Random();
			int memberRand = memberRandom.nextInt(100000) + 1;			
			String extPin = generator.generateSchRoomExtnWithPin(memberRand, randNumb);
			String[] extPinVal = extPin.split("-");
			ScheduledRoomResponse scheduledRoomResponse = generator.decryptShceduledRoom(extPinVal[0], extPinVal[1]);
			Assert.assertEquals(String.valueOf(memberRand), scheduledRoomResponse.getRoomExtn());
			Assert.assertEquals(randNumb, scheduledRoomResponse.getPin());
			String extNoPin = generator.generateSchRoomExtnWithoutPin(memberRand, randNumb);
			Assert.assertNotNull(extNoPin);
			Assert.assertEquals(0, Long.parseLong(extNoPin) % 2);
			ScheduledRoomResponse scheduledRoomResponse1 = generator.decryptShceduledRoom(extNoPin, "");
			Assert.assertEquals(extNoPin, scheduledRoomResponse1.getRoomExtn());
			Assert.assertEquals(0, scheduledRoomResponse1.getPin());				
		}
	}
	Random random = new Random(System.currentTimeMillis());			
	
	@Test
	public void testGenerate() {
		String extn = null;
		Set<String> extns = new HashSet<String>();
		for(int i = 0; i < 15500; i++) {			
			System.out.println("generated value"+ random.nextDouble());
			long fraction = (long) (1000000 * random.nextDouble());
			int pin = (int) (fraction + 1000000);
			String finalExt = String.valueOf(pin);
			System.out.println("pin---"+finalExt);
			Assert.assertTrue(finalExt.length() >= 7);			
		}

		//Assert.assertEquals(1500, extns.size());
	}
}
