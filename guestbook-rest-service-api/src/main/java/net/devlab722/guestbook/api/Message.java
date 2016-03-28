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
    private final String userName;
    private final String originalContent;
    private final boolean filtered;
    private final Metadata metadata;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class MessageBuilder {
    }
}
