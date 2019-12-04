//import java.time.Duration;
//import java.util.Collections;
//import java.util.Properties;
//import java.util.Random;
//
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.clients.consumer.ConsumerRecords;
//import org.apache.kafka.clients.consumer.KafkaConsumer;
//import org.apache.kafka.clients.producer.KafkaProducer;
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.apache.kafka.clients.producer.ProducerRecord;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.apache.kafka.common.serialization.StringSerializer;
//
///**
// * @author : ddv
// * @since : 2019/11/27 10:17 PM
// */
//
//public class KafkaTest {
//
//    @org.junit.Test
//    public void run() throws InterruptedException {
//        String topic = "ddv";
//        Properties producerConfig = new Properties();
//        producerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");// kafka地址，多个地址用逗号分割
//        producerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        producerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(producerConfig);
//
//        Properties consumerConfig = new Properties();
//        consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");// kafka地址，多个地址用逗号分割
//        consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, topic);
//        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(consumerConfig);
//        kafkaConsumer.subscribe(Collections.singleton(topic));
//
//        try {
//            for (int i = 0; i < 100; i++) {
//                String msg = "Hello," + new Random().nextInt(100);
//                ProducerRecord<String, String> record = new ProducerRecord<>(topic, msg);
//                kafkaProducer.send(record);
//                System.out.println("消息发送成功:" + msg);
//            }
//
//            ConsumerRecords<String, String> poll = kafkaConsumer.poll(Duration.ofMillis(100));
//            for (ConsumerRecord<String, String> record : poll) {
//                System.out.println(record.key() + " " + record.value());
//            }
//        } finally {
//            kafkaProducer.close();
//        }
//    }
//}
