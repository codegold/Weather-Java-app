import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private static Document getPage() throws IOException {
        String url = "http://pogoda.spb.ru/";
        Document page = Jsoup.parse(new URL(url), 3000);
        return page;
    }

    //from this 04.02 Понедельник погода сегодня -> get 04.02
    private static Pattern pattern = Pattern.compile("\\d{2}.\\d{2}"); //Паттерн - механизм шаблон чтобы

    // искать нужную инфо в тексте. Матчер - ищет, матчит нужную инфо в тексте
    // \d{2}.\d{2}  - d значит 2 символа
    private static String getDateFromString(String stringDate) throws Exception {
        Matcher matcher = pattern.matcher(stringDate);
        if (matcher.find()) { //если матчер что-то нашел
            return matcher.group(); //верни и сгруппируй найденное
        }
        throw new Exception("Can't extract date from string!");
    }

    private static int printPartValues(Elements values, int index) {
        int iterationCount = 4;
        if (index == 0) {
            Element valueLn = values.get(3); ////Хотим из Вэльюз забрать по индексу
            boolean isMorning = valueLn.text().contains(" Утро");
            if (isMorning) {
                iterationCount = 3;
            }

            for (int i = 0; i < iterationCount; i++) {
                Element valueLine = values.get(index + i);
                for (Element td : valueLn.select("td")) {
                    System.out.print(td.text() + "    ");
                }
                System.out.println();
            }
            return iterationCount;
        } else {
            /**Дублирурующийся код - переработать чтобы не было */
            for (int i = 0; i < iterationCount; i++) {
                Element valueLine = values.get(index + i); //Хотим из Вэльюз забрать по индексу
                for (Element td : valueLine.select("td")) {
                    System.out.print(td.text() + "    ");
                }
                System.out.println();
            }
        }
        return iterationCount;
    }

    public static void main(String[] args) throws Exception {
        Document page = getPage();
        //CSS query language
        Element tableWth = page.select("table[class=wt]").first();
        Elements names = tableWth.select("tr[class=wth]"); //Говорим элементу, что из нашей таблицы мы хотим
        Elements values = tableWth.select("tr[valign=top]");
        int index = 0; // Будет говорить на каком значении мы сейчас находимся.
        //выбрать только те tr у когорых класс wth
        for (Element name : names) {
            String dateString = name.select("th[id=dt").text();
            String date = getDateFromString(dateString);
            System.out.println(date + "    Явления    Температура    Давление    Влажность    Ветер");
            int iterationCount = printPartValues(values, index);
            index = index + iterationCount;
        }
    }
}

