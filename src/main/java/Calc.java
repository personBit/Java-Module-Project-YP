import java.util.ArrayList;
import java.util.Scanner;
import java.util.Locale;


public class Calc {
    Scanner scanner = new Scanner(System.in);


    ArrayList<Item> items = new ArrayList<>(); // список товаров
    int countPeople; // количество человек
    int rubleFormat; // формат слова "рубль"
    double totalPrice = 0; // стоимость всех товаров (изначально 0)
    double fromEach; // каждый должен заплатить
    public void countPeople() { // узнаем количество человек
        System.out.println("Привет! На сколько человек делим счет (введите число)?:");

        do {
            while (!scanner.hasNextInt()) { // пользователь ввел не число
                System.out.println("Ошибка! Введите число:");
                scanner.next();
            } countPeople = scanner.nextInt();
            if (countPeople <= 1) { // пользователь ввел число меньше нуля, сообщаем об ошибке и начинаем снова
                System.out.println("Должно быть минимум 2 человека для того, чтобы разделить счет. Повторите попытку:");
            }
        } while (countPeople <= 1);

    }

    public void itemAdd(String name, double price) { // добавляем товар в список
        Item newItem = new Item();
        newItem.name = name;
        newItem.price = price;

        items.add(newItem);
    }
    public void itemAddOut() { // внешний метод для добавления товара
        scanner.useLocale(Locale.ENGLISH);
        System.out.println("Введите название товара:");
        String name = scanner.next();
        double price;

        System.out.print("Введите цену товара (в точности до копеек. Используйте точку в качестве разделения):\n");
        do { // пользователь добавит хотя-бы один товар
            while (!scanner.hasNextDouble()) { // пользователь ввел не число, тогда
                System.out.println("Ошибка! Введите число:");
                scanner.next();
            }
            price = scanner.nextDouble(); // если ввел число, присвоем его переменной price
            if (price <= 0) { // если число меньше нуля, выводим ошибку и переходим на while ()
                System.out.println("Число должно быть больше нуля. Попробуйте снова:");
            }
        } while (price <= 0); // в случае ошибки начинаем цикл сначало

        itemAdd(name, price);
        System.out.println("Вы добавили товар с именем " + name + " и ценой " + price);
    }

    public boolean agreeUser() { // метод на согласие продолжить добавлять товары
        System.out.println("Продолжить добавление товаров (введите Продолжить/Завершить)?:");
        String agreeUser = scanner.next().toLowerCase();
        boolean agree = true;
        boolean invalidAgree;

        if (agreeUser.equals("завершить")) {
            agree = false;
        } else if (agreeUser.equals("продолжить")) {
            agree = true;
        } else {
            invalidAgree = true;
            while (invalidAgree) {
                System.out.println("Пожалуйста, выберите ОДНУ из ДОСТУПНЫХ команд: введите Продолжить/Завершить:");
                agreeUser = scanner.next().toLowerCase();

                if (agreeUser.equals("завершить")) {
                    agree = false;
                    invalidAgree = false;
                } else if (agreeUser.equals("продолжить")) {
                    agree = true;
                    invalidAgree = false;
                }
            }
        } return agree;

    }


    public void total() { // считаем чек с каждого
        System.out.println("Добавленные товары: ");
        for (Item item : items) {
            System.out.println("Товар '" + item.name + "' за " + item.price + " " + rubleFormat(item.price));
        }
        for (Item item : items) {
            totalPrice = totalPrice + item.price;
        }
        fromEach = (totalPrice / countPeople);
        System.out.println(String.format("Итого, каждый должен заплатить: %.2f ", fromEach) + rubleFormat(fromEach));
    }

     public String rubleFormat(double price) { // определяем формат слова "рубль"
        int priceInt = (int) price;

        rubleFormat = priceInt % 10;
        int rubleFormatH = priceInt % 100;
        if (price > 100 && (rubleFormatH == 11 || rubleFormatH == 12 || rubleFormatH == 13 || rubleFormatH == 14)) {
            return "рублей";
        } else if (priceInt == 11 || priceInt == 12 || priceInt == 13 || priceInt == 14) {
            return "рублей";
        } else if (rubleFormat == 1) {
            return "рубль";
        } else if (rubleFormat == 2 || rubleFormat == 3 || rubleFormat == 4) {
            return "рубля";
        } else {
            return "рублей";
        }
    }


    public void itemAddMain() { // основная логика калькулятора
        boolean processAdd = true;
        countPeople(); // узнаем количество человек
        boolean agree = true; // пользователь хочет добавить товар
        while (agree) {
        itemAddOut(); // процесс добавления товара
        agree = agreeUser();// узнаем, хочет ли продолжить пользователь
        } if (!agree) {
            total(); // если не хочет, выводим результат
        }

    }
}
