package smartlogistic.impl;

import java.util.UUID;
import java.io.File;

import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotMessage;
import com.amazonaws.services.iot.client.AWSIotMqttClient;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.amazonaws.services.iot.client.sample.sampleUtil.SampleUtil;
import com.amazonaws.services.iot.client.sample.sampleUtil.SampleUtil.KeyStorePasswordPair;

import utils.MySimpleLogger;

public class SmartLogisticNotifier {

	// valores por defecto de los parámetros de inicio
	protected static String clientEndpoint = "<prefix>.iot.<region>.amazonaws.com";       				// replace <prefix> and <region> with your own
	protected static String clientId = "<client>-" + UUID.randomUUID().toString();                  	// replace with your own client ID. Use unique client IDs for concurrent connections.
	protected static String certsDir = setPath();														//"<certs-folder>/";
	protected static String certificateFile = certsDir + "<cert-id>-certificate.pem.crt";               // X.509 based certificate file
	protected static String privateKeyFile = certsDir + "<cert-id>-private.pem.key";                    // PKCS#1 or PKCS#8 PEM encoded private key file

	protected static AWSIotMqttClient client = null;

	protected static AWSIotQos qos = null;

	protected static String loggerId = "my-aws-iot-Smart-City";

	public void awsConnect() {

		client = initClient();

		// CONNECT CLIENT TO AWS IOT MQTT
		// optional parameters can be set before connect()
		qos = AWSIotQos.QOS0;
        
		try {
			client.connect();
			
			MySimpleLogger.info(loggerId, "Client Connected to AWS IoT MQTT");
			

		} catch (AWSIotException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	public static AWSIotMqttClient initClient() {
        
		KeyStorePasswordPair pair = SampleUtil.getKeyStorePasswordPair(certificateFile, privateKeyFile);
		AWSIotMqttClient client = new AWSIotMqttClient(clientEndpoint, clientId, pair.keyStore, pair.keyPassword);

		return client;
	}

	public static void subscribe(String topic) {
		
		AWSIoT_TopicHandler theTopic = new AWSIoT_TopicHandler(topic, qos, client );
		try {
			client.subscribe(theTopic);
			MySimpleLogger.info(loggerId, "... SUBSCRIBED to TOPIC: " + topic);
		} catch (AWSIotException e) {
			e.printStackTrace();
		}
		
	}
	public static String setPath(){
		String rutaProyecto = System.getProperty("user.dir");
		rutaProyecto = rutaProyecto + "\\certs\\";
        System.out.println("Ruta absoluta del proyecto: " + rutaProyecto);
		return rutaProyecto;
	}

}
