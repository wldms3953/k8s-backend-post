package com.welab.k8s_backend_post.event.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaMessageProducer {
    //Kafka 메시지를 전송하기 위한 Spring Kafka 핵심 도구!!!
    private final KafkaTemplate<String, Object> kafkaTemplate;
    public void send(String topic, Object message) {
        kafkaTemplate.send(topic, message);
    }
}
//
//이렇게 호출하면, post-topic이라는 Kafka 토픽에 postDto라는 데이터를 publish(발행) 합니다.
// → 나중에 Kafka consumer가 해당 토픽을 구독 중이면 이 메시지를 받아서 처리하게 됩니다.