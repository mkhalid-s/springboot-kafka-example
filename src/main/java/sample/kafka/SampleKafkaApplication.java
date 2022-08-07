/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package sample.kafka;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@SpringBootApplication
@RestController
public class SampleKafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleKafkaApplication.class, args);
    }
    private Producer producer;
    @Bean
    public ApplicationRunner runner(Producer producer) {
        this.producer = producer;
        return (args) -> {
            for(int i = 1; i < 20; i++) {
                    producer.send(new SampleMessage(i, "A simple test message "+ Instant.now()));
            }
        };
    }

    @PostMapping("/send")
    public HttpStatus sendKafkaMessage(@RequestBody final String msg){
        System.out.println(msg);
        this.producer.send(new SampleMessage(0, msg ));
        return HttpStatus.CREATED;
    }

}
