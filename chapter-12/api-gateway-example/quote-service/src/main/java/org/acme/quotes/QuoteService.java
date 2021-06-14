package org.acme.quotes;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Path("/")
public class QuoteService {

    private final Random random = new Random();

    @GET
    public String getQuote() {
        int index = random.nextInt(QUOTES.size());
        return QUOTES.get(index);
    }

    /**
     * List of quotes, from https://www.goodhousekeeping.com/life/g26948562/funny-coffee-quotes/.
     */
    static final List<String> QUOTES = Arrays.asList(
            "Coffee is a beverage that puts one to sleep when not drank.",
            "Everyone should believe in something. I believe I will have another coffee.",
            "I never drink coffee at lunch. I find it keeps me awake for the afternoon.",
            "Coffee first. Schemes later.",
            "Humanity runs on coffee.",
            "But even a bad cup of coffee is better than no coffee at all.",
            "What goes best with a cup of coffee? Another cup.",
            "The most dangerous drinking game is seeing how long I can go without coffee.",
            "My birthstone is a coffee bean.",
            "Caffeine and sugar, the two basic food groups.",
            "Black as the devil, hot as hell, pure as an angel, sweet as love.",
            "It's amazing how the world begins to change through the eyes of a cup of coffee.",
            "I want someone to look at me the way I look at coffee.",
            "May your coffee kick in before reality does.",
            "As long as there was coffee in the world, how bad could things be?",
            "The powers of a man's mind are directly proportional to the quantity of coffee he drinks.",
            "I never laugh until I've had my coffee.",
            "Without my morning coffee, I'm just like a dried-up piece of goat.",
            "I believe humans get a lot done, not because we're smart, but because we have thumbs so we can make coffee.",
            "We want to do a lot of stuff; we're not in great shape. We didn't get a good night's sleep. We're a little depressed. Coffee solves all these problems in one delightful little cup."
    );

}
