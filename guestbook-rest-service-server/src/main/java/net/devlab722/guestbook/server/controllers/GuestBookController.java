package net.devlab722.guestbook.server.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.devlab722.guestbook.api.Message;
import net.devlab722.guestbook.server.backend.RedisBackend;

@Controller
@RequestMapping(path = "/api/v1/guestbook/messages")
public class GuestBookController {

    @Autowired
    RedisBackend redisBackend;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> storeMessage(
            @RequestBody(required = false) Message message) {
        redisBackend.storeMessage(message.getContent());
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Message> getMessages() {
        return redisBackend.getAllMessages().stream()
                .map(s -> Message.builder().content(s).build())
                .collect(Collectors.toList());
    }

}
