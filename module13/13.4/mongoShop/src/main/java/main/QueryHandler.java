package main;

import static com.mongodb.client.model.Accumulators.avg;
import static com.mongodb.client.model.Accumulators.max;
import static com.mongodb.client.model.Accumulators.min;
import static com.mongodb.client.model.Accumulators.sum;
import static com.mongodb.client.model.Aggregates.group;
import static com.mongodb.client.model.Aggregates.unwind;
import static com.mongodb.client.model.Aggregates.lookup;

import static main.Main.goods;
import static main.Main.shops;

import com.mongodb.BasicDBList;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bson.Document;
import org.bson.conversions.Bson;

public class QueryHandler {
  private static final String REGEX_ADD_SHOP = "(ДОБАВИТЬ_МАГАЗИН)\\s(.+)";
  private static final String REGEX_ADD_PRODUCT = "(ДОБАВИТЬ_ТОВАР)\\s(.+)\\s(\\d+)";
  private static final String REGEX_DISPLAY_PRODUCT = "(ВЫСТАВИТЬ_ТОВАР)\\s(.+)\\s(.+)";
  private static final String REGEX_GOODS_STATISTIC = "^СТАТИСТИКА_ТОВАРОВ$";

  public void makeQuery(String input) {
    if (input.toUpperCase().matches(REGEX_ADD_SHOP)) {
      addShop(Pattern.compile(REGEX_ADD_SHOP, Pattern.CASE_INSENSITIVE)
          .matcher(input));
    } else if (input.toUpperCase().matches(REGEX_ADD_PRODUCT)) {
      addGoods(Pattern.compile(REGEX_ADD_PRODUCT, Pattern.CASE_INSENSITIVE)
          .matcher(input));
    } else if (input.toUpperCase().matches(REGEX_DISPLAY_PRODUCT)) {
      displayProduct(Pattern.compile(REGEX_DISPLAY_PRODUCT, Pattern.CASE_INSENSITIVE)
          .matcher(input));
    } else if (input.toUpperCase().matches(REGEX_GOODS_STATISTIC)) {
      viewStatistic();
    } else {
      System.out.println("Неверная команда");
    }
  }

  private static void addShop(Matcher matcher) {
    if (matcher.find()) {
      String shopName = matcher.group(2);
      Document shop = new Document("name", shopName)
          .append("goods", new BasicDBList());
      if (shops.find(shop).first() == null) {
        shops.insertOne(shop);
        System.out.printf("Магазин %s успешно добавлен%n", shopName);
      } else {
        System.out.printf("Магазин %s уже присутствует в базе%n", shopName);
      }
    }
  }

  private void addGoods(Matcher matcher) {
    if (matcher.find()) {
      String productName = matcher.group(2);
      int price = Integer.parseInt(matcher.group(3));
      Document product = new Document("name", productName)
          .append("price", price);
      if (goods.find(product).first() == null) {
        goods.insertOne(product);
        System.out.printf("Продукт %s успешно добавлен%n", productName);
      } else {
        System.out.printf("Продукт %s уже присутствует в базе%n", productName);
      }
    }
  }

  private void displayProduct(Matcher matcher) {
    if (matcher.find()) {
      String productName = matcher.group(2);
      String shopName = matcher.group(3);
      Document shop = shops.find(new Document("name", shopName)).first();
      Document product = goods.find(new Document("name", productName)).first();
      boolean productPlaced = shops.countDocuments(new Document("name", shopName)
          .append("goods", productName)) > 0;
      if (shop == null) {
        System.out.printf("Магазин %s отсутствует в базе%n", shopName);
      } else if (product == null) {
        System.out.printf("Товара %s отсутствует в базе%n", productName);
      } else if (productPlaced) {
        System.out.println("В данном магазине товар уже размещён");
      } else {
        shops.updateOne(Filters.eq("name", shopName),
            Updates.addToSet("goods", productName));
        System.out.printf("Товар %s успешно размещён в магазине %s%n", productName, shopName);
      }
    }
  }

  private void viewStatistic() {
    List<Bson> query = Arrays.asList(
       unwind("$goods"),
       lookup("goods", "goods", "name", "goods_list"),
       unwind("$goods_list"),
       group("$name",
           avg("avgPrice", "$goods_list.price"),
           min("minPrice", "$goods_list.price"),
           max("maxPrice", "$goods_list.price"),
           sum("totalCount", 1),
           sum("cheaperThan100", new Document("$cond", Arrays.asList(
               new Document("$lt", Arrays.asList("$goods_list.price", 100)), 1, 0)))
       )
    );

    AggregateIterable<Document> response = shops.aggregate(query);
    outputFormat(response);
  }

  private void outputFormat(AggregateIterable<Document> response) {
    for (Document document : response) {
      System.out.printf(
          "%nМагазин %s %n" +
              "Товаров всего: " + "%s%n" +
              "Средняя цена товара: " + "%s%n" +
              "Минимальная цена товара: " + "%s%n" +
              "Максимальная цена товара: " + "%s%n" +
              "Товаров дешевле чем 100: " + "%s%n",
          document.get("_id"),
          document.get("totalCount"),
          document.get("avgPrice"),
          document.get("minPrice"),
          document.get("maxPrice"),
          document.get("cheaperThan100")
      );
    }
  }
}