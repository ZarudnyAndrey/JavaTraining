package main;

import com.mongodb.client.MongoCollection;
import java.util.Scanner;
import org.bson.Document;

public class Main {
  public static MongoCollection<Document> shops;
  public static MongoCollection<Document> goods;

  public static void main(String[] args) {
    Connector connector = new Connector();

    shops = connector.init().getCollection("shops");
    goods = connector.init().getCollection("goods");

    shops.drop();
    goods.drop();

    QueryHandler queryHandler = new QueryHandler();

    for (;;) {
      System.out.println("Добро пожаловать в программу для менеджмента товаров в магазине!\n" +
          "Введите одну из команд для взаимодействия с системой:\n" +
          "\tДОБАВИТЬ_МАГАЗИН <название магазина>\n" +
          "\tДОБАВИТЬ_ТОВАР <наименование товара> <цена товара>\n" +
          "\tВЫСТАВИТЬ_ТОВАР <наименование товара> <название магазина>\n" +
          "\tСТАТИСТИКА_ТОВАРОВ");

      String input = new Scanner(System.in).nextLine();
      queryHandler.makeQuery(input);
    }
  }
}