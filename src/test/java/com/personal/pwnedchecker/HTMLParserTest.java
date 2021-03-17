package com.personal.pwnedchecker;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;

public class HTMLParserTest {

    String rawResponse = "Description\":\"In June 2011 as part of a final breached data dump, the hacker collective &quot;LulzSec&quot; <a href=\\\"http://www.rockpapershotgun.com/2011/06/26/lulzsec-over-release-battlefield-heroes-data\\\" target=\\\"_blank\\\" rel=\\\"noopener\\\">obtained and released over half a million usernames and passwords from the game Battlefield Heroes</a>. The passwords were stored as MD5 hashes with no salt and many were easily converted back to their plain text versions.";

    @Test
    public void testHTMLParseToRemoveTags() {

        String parsedString = Jsoup.parse(rawResponse).text();
        System.out.println(parsedString);
    }
}
