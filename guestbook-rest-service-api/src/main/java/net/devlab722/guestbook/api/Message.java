package net.devlab722.guestbook.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


@Getter
@EqualsAndHashCode
@ToString
@Builder
@JsonDeserialize(builder = Message.MessageBuilder.class)
public class Message {
    private final String content;

    public Message(String content) {
        this.content = content;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class MessageBuilder {
    }
}
