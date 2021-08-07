package com.chukwu.MyStreamSite.resource;



import com.chukwu.MyStreamSite.model.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;


//@Controller
public class MessageController {
  @MessageMapping("/chatbox")
  @SendTo("/topic/chat")
  public Message message(@Payload Message message) throws Exception {
    Thread.sleep(1000); // simulated delay
    return new Message(message.getContent(), message.getName());
    }




}
