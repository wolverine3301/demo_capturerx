package logan.demo_capturerx;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.annotation.KafkaListener;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.StringDeserializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.*;


//@RequestMapping("/jobs")
@RestController
public class JobApplicationController {
	
	@Autowired
	JobApplicationRepository jobApplicationRepository;

    /* KAFKA
    private static final String topic = "my-topic";
    Properties props = new Properties();
    props.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

    //creating consumer
    KafkaConsumer<String,JobApplicationEntity> consumer= new KafkaConsumer<String,JobApplicationEntity>(props);

    //Subscribing
    consumer.subscribe(Collections.singleton(topic));
    */
    @GetMapping("/getall")
    public List<JobApplicationEntity> getAllEmployee(){
        List<JobApplicationEntity> allJoblist = jobApplicationRepository.findAll();
        return allJoblist;

    }
    @GetMapping("/job")
	public JobApplicationEntity getjobApplicationbyId(@RequestParam(value = "id", defaultValue = "1") Integer jobApplicationId){
		JobApplicationEntity jobApplicationEntity = jobApplicationRepository.findById(jobApplicationId).get();
    /*
        ConsumerRecords<String, JobApplicationEntity> records = consumer.poll(Duration.ofMillis(100));
        for (ConsumerRecord<String, JobApplicationEntity> record : records) {
            JobApplicationEntity job = record.value();
            job.setStatus("in progress");
            jobApplicationRepository.save(job);
        }
        consumer.commitAsync();

     */
        return jobApplicationEntity;
	}
    @PostMapping("/job")
    public JobApplicationEntity createjobApplication() {

        JobApplicationEntity jobApplication = new JobApplicationEntity();
        jobApplicationRepository.save(jobApplication);
        //try (KafkaProducer<String, JobApplicationEntity> producer = new KafkaProducer(props)) {
         //   ProducerRecord<String, JobApplicationEntity> message1 = new ProducerRecord<>(topic, jobApplication);
        //   producer.send(message1);

        //}
        return jobApplication;
    }

    @PutMapping("/job")
    public ResponseEntity<JobApplicationEntity> updatejobApplication(@PathVariable(value = "id") Integer jobApplicationId,
          @RequestBody JobApplicationEntity jobApplicationDetails) {
        JobApplicationEntity jobApplication = jobApplicationRepository.findById(jobApplicationId).get();

        jobApplication.setStatus(jobApplicationDetails.getStatus());

        final JobApplicationEntity updatedjobApplication = jobApplicationRepository.save(jobApplication);
        return ResponseEntity.ok(updatedjobApplication);
    }
    
    @DeleteMapping("/job")
    public Map<String, Boolean> deletejobApplication(@PathVariable(value = "id") Integer jobApplicationId)
    {
     JobApplicationEntity jobApplication = jobApplicationRepository.findById(jobApplicationId).get();

        jobApplicationRepository.delete(jobApplication);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }



}
