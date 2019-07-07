package com.vidyo.parser.xml;

import com.vidyo.service.IServiceService;
import com.vidyo.service.INetworkService;
import com.vidyo.service.ISystemService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.*;

import java.util.Properties;


public class NetworkInterface {

    private ApplicationContext applicationContext = null;
    private IServiceService service = null;
    //private ISystemService  system  = null;
    //private INetworkService network = null;
    //private int allInOne = -1;
    //private boolean httpsEnabled=false;

    public NetworkInterface() {
        applicationContext = new ClassPathXmlApplicationContext(new String[]{"vidyo-portal-super-service-standalone.xml"});
        this.service = (IServiceService)applicationContext.getBean("serviceService");
        //this.system = (ISystemService)applicationContext.getBean("systemService");
        //this.network = (INetworkService)applicationContext.getBean("networkService");
        //this.allInOne = this.service.isAllInOne();
        /*Properties prop = this.system.readHttpHttpsPorts();
        if(prop!=null)
            this.httpsEnabled = ("Enabled".equalsIgnoreCase(prop.getProperty("HTTPS")) );*/
    }

    private void output(Object msg) {
        System.out.println(msg);
    }

	public boolean replace(String intf, String ipAddress1, String fqdn1) {
		
		if (ipAddress1.equalsIgnoreCase("null")) {
			output("Error: 1st pair of IP/FQDN cannot be null.");
			return false;
		}

		try {
			this.service.reconfigVmVrNetworkConfig(ipAddress1, fqdn1);
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

		return true;

	}


    public static void showHelp() {
        System.out.println("Usage:");
        System.out.println("  java [-classpath..] com.vidyo.parser.xml.NetworkInterface InterfaceName ip1 fqdn1");
        System.out.println("  java com.vidyo.parser.xml.NetworkInterface eth0 172.16.2.69 vm9_dev.vidyo.com");
    }

    /**
     * Console class which allowing user to set ip/fqdn on eth0, it also specifies which network interface is just modified.
     * example:
     *     java com.vidyo.parser.xml.NetworkInterface eth0 172.16.2.69 vm9.vidyo.com
     *
     * @param args  the arguments should be in following order
     *              eth ip1 fqdn1
     *              eth: is used to specified which interface has been updated
     *              ip1: the latest ipAddress for eth0
     *              fqdn1: the latest FQDN for eth0
     */
    public static void main(String[] args) {
        if(args.length != 5) {
            showHelp();
            System.exit(1);
        }
        if(args[0].toLowerCase().startsWith("eth0")) {
            NetworkInterface nic = new NetworkInterface();
            if(!nic.replace(args[0].toLowerCase(), args[1], args[2])){
                System.out.println("Failed to replace network interface ipaddress/fqdn");
                System.exit(1);
            }
        }
        else {
            showHelp();
            System.exit(1);
        }

        System.exit(0);
    }
}
