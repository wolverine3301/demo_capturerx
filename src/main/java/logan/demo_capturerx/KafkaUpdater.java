package logan.demo_capturerx;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

@Service
public class KafkaUpdater {

    private KafkaConsumer kafkaConsumer;
    @Autowired
    JobApplicationRepository jobApplicationRepository;

    public KafkaUpdater(){
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("group.id", "test-group");

        this.kafkaConsumer = new KafkaConsumer(properties);
        List topics = new ArrayList();
        topics.add("my-topic");
        kafkaConsumer.subscribe(topics);
    }
    public void getMessage(int id){
        while (true) {
            ConsumerRecords records = kafkaConsumer.poll(10);
            for (Iterator it = records.iterator(); it.hasNext(); ) {
                ConsumerRecord record = (ConsumerRecord) it.next();
                JobApplicationEntity jobApplication = jobApplicationRepository.findById(id).get();
                jobApplication.setStatus("in progress");
                final JobApplicationEntity updatedjobApplication = jobApplicationRepository.save(jobApplication);
            }
        }

    }
}
