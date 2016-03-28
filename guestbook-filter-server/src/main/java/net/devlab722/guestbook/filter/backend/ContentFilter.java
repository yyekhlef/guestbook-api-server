package net.devlab722.guestbook.filter.backend;

import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableMap;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Component
public class ContentFilter {

    public static final ImmutableMap<String, String> BUZZ_WORDS =
            new ImmutableMap.Builder<String, String>()
                    .put("microservices", "beeeeep")
                    .put("microservice", "beeeeep")
                    .put("bières", "bières (au chtijug? merci Zenika!)")
                    .put("Rancher", "Rancher (ça rulez!)")
                    .build();

    public static final String EMPTY_MESSAGE_COUNTER_MEASURE = "moi j'aime pas les messages vides";

    public FilteredMessage filterIfNeeded(String content) {
        boolean filtered = false;
        String newContent = content;
        if (content.isEmpty()) {
            newContent = EMPTY_MESSAGE_COUNTER_MEASURE;
            filtered = true;
        } else {
            for (String buzzword : BUZZ_WORDS.keySet()) {
                if (content.contains(buzzword)) {
                    filtered = true;
                    newContent = newContent.replace(buzzword, BUZZ_WORDS.get(buzzword));
                }
            }
        }
        return new FilteredMessage(newContent, filtered);
    }


    @Getter
    @ToString
    @RequiredArgsConstructor
    public static class FilteredMessage {
        private final String content;
        private final boolean filtered;
    }
}
