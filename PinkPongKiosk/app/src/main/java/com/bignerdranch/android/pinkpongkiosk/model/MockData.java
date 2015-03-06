package com.bignerdranch.android.pinkpongkiosk.model;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockData {

    public static List<Match> generateMatchList() {

        Player bob = new Player(0, "Bob Evans", null, 3, 5);
        Player sue = new Player(1, "Sue MacDonald", null, 7, 2);
        Player joe = new Player(2, "Joe Smith", null, 8, 4);

        Match match1 = new Match(0, bob, sue, "1:00pm");
        Match match2 = new Match(1, joe, bob, "2:00pm");
        Match match3 = new Match(3, sue, joe, "3:00pm");

        List<Match> matchList = new ArrayList<>();
        matchList.add(match1);
        matchList.add(match2);
        matchList.add(match3);

        return matchList;
    }
}
