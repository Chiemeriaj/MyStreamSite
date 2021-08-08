package com.chukwu.MyStreamSite.controller;


import com.chukwu.MyStreamSite.model.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {
  @MessageMapping("/chatbox")
  @SendTo("/topic/chat")
  public Message message(@Payload Message message) throws Exception {
    Thread.sleep(1000); // simulated delay
    return new Message(message.getContent(), message.getName());
  }


}
