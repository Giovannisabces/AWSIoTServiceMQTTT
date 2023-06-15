package smartlogistic.impl;

import com.amazonaws.services.iot.client.AWSIotMessage;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.amazonaws.services.iot.client.AWSIotTopic;


import utils.MySimpleLogger;

import com.amazonaws.services.iot.client.AWSIotMqttClient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class AWSIoT_TopicHandler extends AWSIotTopic {
	private AWSIotMqttClient clientl = null;
	public AWSIoT_TopicHandler(String topic, AWSIotQos qos, AWSIotMqttClient clientTest) {
		super(topic, qos);
		this.clientl = clientTest;
	}
	
	@Override
	public void onMessage(AWSIotMessage message) {
		super.onMessage(message);
		String text = message.getStringPayload();
		MySimpleLogger.info(SmartLogisticNotifier.loggerId + "-topicHandler", "RECEIVED: " + text);

		String payload = new String(message.getPayload());
		
		System.out.println("-------------------------------------------------");
		System.out.println("| Topic:" + topic);
		System.out.println("| Message: " + payload);
		System.out.println("-------------------------------------------------");
		/*
		 * Este URL utilizado solo esta disponible al estar conectado por VPN
		 */
		String url = "http://ttmi021.iot.upv.es:8182/car/engine"; 				
		String putPayload = "{action:brake}";

		
		try{
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("PUT");

			//add request header
			con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
			con.setRequestProperty("Content-Type", "application/json;");
			con.setRequestProperty("Accept", "application/json;");
			con.setRequestProperty("Accept-Language", "es");
			
			// Send Data
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(putPayload);
			
			wr.flush();
			wr.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			System.out.println(response);

		}catch(Exception e){
			e.printStackTrace();
		}

	}
}