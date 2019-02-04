import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Pattern;

public class Parser {
    private static Document getPage() throws IOException {
        String url = "http://pogoda.spb.ru/";
        Document page = Jsoup.parse(new URL(url), 3000);
        return page;
    }

    //from this 04.02 Понедельник погода сегодня -> get 04.02
    private Pattern pattern = Pattern.compile("\\d{2}.\\d{2}") //Паттерн - механизм шаблон чтобы
    // искать нужную инфо в тексте. Матчер - ищет, матчит нужную инфо в тексте
    // \d{2}.\d{2}  - d значит 2 символа
    private String getDateFromString(String stringDate) {

    }

    public static void main(String[] args) throws IOException {
        Document page = getPage();
        //CSS query language
        Element tableWth = page.select("table[class=wt]").first();
        Elements names = tableWth.select("tr[class=wth]"); //Говорим элементу, что из нашей таблицы мы хотим
        Elements values = tableWth.select("tr[valign=top]");
        //выбрать только те tr у когорых класс wth

        for (Element name: names) {
            String date = name.select("th[id=dt").text();
            System.out.println(date + "    Явления    Температура    Давление    Влажность    Ветер");
        }
    }
}
