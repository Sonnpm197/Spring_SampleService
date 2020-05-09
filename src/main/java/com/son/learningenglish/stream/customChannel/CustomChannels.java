package com.son.learningenglish.stream.customChannel;


import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface CustomChannels {
    @Input("customQuizletChannel") // channel name
    SubscribableChannel customQuizletChannel();
}
