package com.vidyo.service.statusnotify;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.vidyo.bo.CDRinfo2;
import com.vidyo.bo.statusnotify.Alert;
import com.vidyo.bo.statusnotify.NotificationInfo;

public class EpicStatusNotificationsSender {

	public static void main(String[] args) throws Exception {
		 // Create a ConnectionFactory
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        // Create a Connection
        Connection connection = connectionFactory.createConnection();
        connection.start();

        // Create a Session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create the destination (Topic or Queue)
        Destination destination = session.createQueue("statusNotify.queue");
        
        ///Create a MessageProducer from the Session to the Topic or Queue
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        
        // Create a messages
        NotificationInfo notificationInfo = new NotificationInfo();
        CDRinfo2 info = new CDRinfo2();
		info.setExtData("ConferenceID=26699&ExternalID=FAMMD&ExternalIDType=1&VendorName=Vidyo&ConnectionStatus=1");
		info.setExtDataType(1);
		notificationInfo.setUserNotification(info);
		notificationInfo.setTenantId(0);
		notificationInfo.setAlert(new Alert("55", "Online", 3));
		
		notificationInfo.setExternalStatusNotificationUrl("https://apporchard.epic.com/interconnect-ao85prd-username/api/epic/2018/Clinical/Patient/SETEXTERNALCONNECTIONSTATUS/Telemedicine/SetExternalConnectionStatus");
		notificationInfo.setExternalUsername("emp$VIDYO");
		notificationInfo.setExternalPassword("?wG2VwUug!H^yPZ");
		notificationInfo.setPlainTextExternalPassword("?wG2VwUug!H^yPZ");
		
		notificationInfo.setVidyoStatusNotificationUrl("http://172.24.249.75:8080/status-notify/services/StatusNotificationServicePort");
		notificationInfo.setVidyoUsername("vidyo-user");
		notificationInfo.setVidyoPassword("vidyo-pass");
		notificationInfo.setPlainTextVidyoPassword("vidyo-pass");
        
        ObjectMessage message = session.createObjectMessage(notificationInfo);
        producer.send(message);

        // Clean up
        session.close();
        connection.close();
	}
}
