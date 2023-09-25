package com.fordevs.springbatchpostgresqltokafka.reader;

import com.fordevs.springbatchpostgresqltokafka.entity.postgresql.InputStudent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.batch.item.ItemReader;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;

@Slf4j
@Service
public class CustomKafkaItemReader implements ItemReader<String> {
    private Queue<String> messageQueue = new LinkedList<>();

    @Override
    public String read() throws Exception {
        return messageQueue.poll();
    }

    @KafkaListener(topics = "student_topic", groupId = "students_group_id")
    public void readMsg(ConsumerRecord<String, String> record) {

        String message =  record.value();
        messageQueue.add(message);
        log.info("Received Message: {}", message);
    }

}

