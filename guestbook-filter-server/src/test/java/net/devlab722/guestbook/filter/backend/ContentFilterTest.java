package net.devlab722.guestbook.filter.backend;


import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ContentFilterTest {

    @DataProvider(name = "dataProviderFor_testFilterIfNeeded")
    public Object[][] dataProviderFor_testFilterIfNeeded() {
        return new Object[][]{
                {"vive les microservices",
                        "vive les beeeeep", true},
                {"trop fort mon microservice",
                        "trop fort mon beeeeep", true},
                {"microservices? vous avez dit microservices?",
                        "beeeeep? vous avez dit beeeeep?", true},
                {"Rancher l'orchestrateur docker",
                        "Rancher (ça rulez!) l'orchestrateur docker", true},
                {"des bières pour tout le monde!",
                        "des bières (au chtijug? merci Zenika!) pour tout le monde!", true},
                {"des bières pour tous ceux qui aiment les microservices!",
                        "des bières (au chtijug? merci Zenika!) pour tous ceux qui aiment les beeeeep!", true},
                {"vive les micro services",
                        "vive les micro services", false},
                {"",
                        ContentFilter.EMPTY_MESSAGE_COUNTER_MEASURE, true}
        };
    }

    @Test(dataProvider = "dataProviderFor_testFilterIfNeeded")
    public void testFilterIfNeeded(String input, String expectedContentValue, boolean expectedFilteredValue) {
        ContentFilter.FilteredMessage filteredMessage = new ContentFilter().filterIfNeeded(input);
        assertThat(filteredMessage.getContent()).isEqualTo(expectedContentValue);
        assertThat(filteredMessage.isFiltered()).isEqualTo(expectedFilteredValue);
    }
}