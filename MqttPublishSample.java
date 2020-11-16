package javaapplication6;

import java.util.Random;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
public class MqttPublishSample {

  public static void main(String[] args) throws InterruptedException {
    String topic        = "sensor/temperature/1";
    String content      = "Message from MqttPublishSample";
    int qos             = 2;
    String broker       = "tcp://0.0.0.0:1883";
    String clientId     = "JavaSample";
    MemoryPersistence persistence = new MemoryPersistence();

    try {
      // Publisher
      MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
      MqttConnectOptions connOpts = new MqttConnectOptions();
      connOpts.setCleanSession(true);
      System.out.println("Connecting to broker: "+broker);
      sampleClient.connect(connOpts);
      System.out.println("Connected");
      Random gerador = new Random();
      
      // Subscriber
      
      while(true){
          
        try{
            Thread.sleep(1000);
        }
        catch(InterruptedException ex){
            Thread.currentThread().interrupt();
        }
        
        content = Integer.toString(gerador.nextInt(45-15)+15);
        
        System.out.println("Publishing message: "+content);
        MqttMessage message = new MqttMessage(content.getBytes());
        message.setQos(qos);
        sampleClient.publish(topic, message);
        System.out.println("Message published");
        
      }
    } catch(MqttException me) {
      System.out.println("reason "+me.getReasonCode());
      System.out.println("msg "+me.getMessage());
      System.out.println("loc "+me.getLocalizedMessage());
      System.out.println("cause "+me.getCause());
      System.out.println("excep "+me);
      me.printStackTrace();
    }
  }
}