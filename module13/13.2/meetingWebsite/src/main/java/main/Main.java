package main;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ListPosition;

public class Main {
  private static final String HOST = "127.0.0.1";
  private static final Integer PORT = 6379;
  private static final int USERS_COUNT = 20;
  private static final int TIMEOUT = 1000;
  private static final int TIMEOUT_BETWEEN_CYCLES = 2500;
  private static final String KEY = "users";
  private static Jedis jedis;

  public static void main(String[] args) throws InterruptedException {
    jedis = new Jedis(HOST, PORT);
    jedis.del(KEY);

    for (int i = 1; i <= USERS_COUNT; i++) {
      jedis.rpush(KEY, Integer.toString(i));
    }

    Long userListLen = jedis.llen(KEY);

    for (;;) {
      for (int i = 0; i < userListLen; i++) {
        String currentUser = jedis.lindex(KEY, i);
        System.out.printf("- На главной странице показываем пользователя %2s%n", currentUser);
        if (Math.random() <= 0.1) {
          long random = randomUser(userListLen, currentUser);
          if (random < i) {
            i--;
          }
        }
        Thread.sleep(TIMEOUT);
      }
      Thread.sleep(TIMEOUT_BETWEEN_CYCLES);
    }
  }

  private static long randomUser(Long length, String currentUser) {
    boolean isCurrentUser = true;
    String donated = "";
    long random = 0;

    while (isCurrentUser) {
      random = Math.round(Math.random() * (length - 1));
      donated = jedis.lindex(KEY, random);
      isCurrentUser = donated.equals(currentUser);
    }

    jedis.lrem(KEY, 0 , donated);
    jedis.linsert(KEY, ListPosition.AFTER, currentUser, donated);
    System.out.printf("> Пользователь %2s оплатил платную услугу%n", donated);
    return random;
  }
}