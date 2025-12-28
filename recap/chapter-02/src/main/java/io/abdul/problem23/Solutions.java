package io.abdul.problem23;

import static org.junit.jupiter.api.Assertions.assertEquals;

// https://leetcode.com/problems/the-number-of-full-rounds-you-have-played
// tag:math tag:string
public class Solutions {

  public static void main(String[] args) {
    System.out.println((-1125 + 1440) % 1440);
    System.out.println((15 + 1440) % 1440);
//    Solution solution = new Solution();
    Solution2 solution = new Solution2();

    assertEquals(1, solution.numberOfRounds("09:31", "10:14"));
    assertEquals(0, solution.numberOfRounds("00:47", "00:57"));
    assertEquals(0, solution.numberOfRounds("23:47", "00:07"));
  }
}

class Solution {

  /*
  Convert to mins for easy comparing
  Total mins in a day - 1440
  Total games in a day - 1440/15 = 96
  0
  15
  30
  45
  .
  .
  1425 (last game)
  Usual case: start < end (non midnight case)
  09:31 to 10:14

  571 to 614
  We need to bring 571 to next game start position -> Rounding off next 15th multiple -> (571 + 14) / 15 -> 585
  We need to bring 614 to previous 15th multiple -> Rounding off -> (614/15) * 15 -> 600
  How many games between 585 & 600 -> (600 - 585) / 15

  Midnight case: start > end
  21:33 to 03:09
  1293 to 189
  1293 -> rounded off -> 1305
  189 -> rounded off -> 180
  180 - 1305 = -1125 + 1440 = 315
  */
  public int numberOfRounds(String loginTime, String logoutTime) {
    int start = minutes(loginTime);
    int end = minutes(logoutTime);
    int firstGameStart = ((start + 14) / 15) * 15;
    int lastGameEnd = (end / 15) * 15;

    return start > end ? ((lastGameEnd - firstGameStart + 1440)) / 15
        : Math.max(0, (lastGameEnd - firstGameStart) / 15);
  }

  private int minutes(String time) {
    String[] timeSplit = time.split(":");
    return (Integer.parseInt(timeSplit[0]) * 60) + Integer.parseInt(timeSplit[1]);
  }
}

/*
Do it early to reduce checks later
 */
class Solution2 {

  public int numberOfRounds(String loginTime, String logoutTime) {
    int start = minutes(loginTime);
    int end = minutes(logoutTime);
    if (start > end) { // midnight case
      end += 1440;
    }

    int firstGameStart = ((start + 14) / 15) * 15;
    int lastGameEnd = (end / 15) * 15;

    return Math.max(0, (lastGameEnd - firstGameStart) / 15);
  }

  private int minutes(String time) {
    String[] timeSplit = time.split(":");
    return (Integer.parseInt(timeSplit[0]) * 60) + Integer.parseInt(timeSplit[1]);
  }
}