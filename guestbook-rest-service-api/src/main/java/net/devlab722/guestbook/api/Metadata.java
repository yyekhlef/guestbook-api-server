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
@JsonDeserialize(builder = Metadata.MetadataBuilder.class)
public class Metadata {

    private final String apiServerName;
    private final String datetimeString;

    @JsonPOJOBuilder(withPrefix = "")
    public static final class MetadataBuilder {
    }
}
