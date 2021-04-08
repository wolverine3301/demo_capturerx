package logan.demo_capturerx;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class KafkaSender{

    private static final String TOPIC= "my_topic";
    private KafkaProducer kafkaProducer;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    public KafkaSender() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        this.kafkaProducer = new KafkaProducer(properties);

    }
    public void writeMessage(int id){
        this.kafkaProducer.send(new ProducerRecord(TOPIC, Integer.toString(id)));
    }

}