package com.vidyo.parser.xml;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vidyo.service.INetworkService;
import com.vidyo.service.IServiceService;
import com.vidyo.service.ISystemService;

public class AddVmIdToVrConfig {

	private ApplicationContext applicationContext = null;
	private IServiceService service = null;

	public AddVmIdToVrConfig() {
		applicationContext = new ClassPathXmlApplicationContext(new String[] {
				"vidyo-portal-super-service-standalone.xml" });
		this.service = (IServiceService) applicationContext
				.getBean("serviceService");
	}

	public AddVmIdToVrConfig(IServiceService service, ISystemService system,
			INetworkService network) {
		this.service = service;
	}

	public boolean addVMIdtoVR() {

		try {
			this.service.addVmIdToConnectVMUri();
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

		return true;

	}

	/**
	 * Console class which allowing user to set ip/fqdn on eth0, it also
	 * specifies which network interface is just modified. example: java
	 * com.vidyo.parser.xml.NetworkInterface eth0 172.16.2.69 vm9.vidyo.com
	 * 
	 * @param args
	 *            the arguments should be in following order eth ip1 fqdn1 eth:
	 *            is used to specified which interface has been updated ip1: the
	 *            latest ipAddress for eth0 fqdn1: the latest FQDN for eth0
	 */
	public static void main(String[] args) {
		AddVmIdToVrConfig addVMId = new AddVmIdToVrConfig();
		if (!addVMId.addVMIdtoVR()) {
			System.out.println("Failed to Add VMID to VR Connect URI");
			System.exit(1);
		}
		System.exit(0);
	}
}
