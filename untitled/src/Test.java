import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Данные для расчета не найдены");
            return;
        }

        double refinRate = 7.25;
        int dayPeni = 10;
        int dayCount = 31;
        int reduce = 300;
        int month = 12;
        int year = 2016;

        double sum = 0;
        double peni;
        double peniSum = 0;
        double moneyMonth = 0;

        try (Scanner scanner = new Scanner(new FileInputStream(args[0]))) {
            scanner.nextLine();
            while (scanner.hasNext()) {
                String stringInFile = scanner.nextLine();
                String data = stringInFile.substring(0, stringInFile.indexOf(';'));
                int lastIndex = stringInFile.lastIndexOf(';');
                String stringLastIndex = stringInFile.substring(0, stringInFile.lastIndexOf(';'));
                String money = stringInFile.substring(stringLastIndex.lastIndexOf(';') + 1, lastIndex);
                if (((Integer.parseInt(data.substring(0, data.indexOf('.')))) < month - 2) && (Integer.parseInt(data.substring(data.indexOf('.') + 1)) == year)) {
                    sum += Double.parseDouble(money);
                }
                if (((Integer.parseInt(data.substring(0, data.indexOf('.')))) == month - 2) && (Integer.parseInt(data.substring(data.indexOf('.') + 1)) == year)) {
                    moneyMonth = Double.parseDouble(money);
                }
            }
            int monthDays = getMonthDays(month - 1, year);
            int dayPenalty = dayPeni + dayCount - monthDays;
            System.out.println("День           Сумма         Пени");
            for (int i = 1; i <= getMonthDays(month, year); i++) {
                if (i < dayPenalty) {
                    peni = refinRate * sum / 100 / reduce;
                    System.out.println(i + "." + month + "." + year + "      " + sum + "     " + peni);
                    peniSum += peni;
                } else {
                    peni = refinRate * (sum + moneyMonth) / 100 / reduce;
                    System.out.println(i + "." + month + "." + year + "      " + (sum + moneyMonth) + "     " + peni);
                    peniSum += peni;
                }
            }
            System.out.println("Итого:" + peniSum);
        } catch (FileNotFoundException e) {
            System.out.println("Файл не открыт");
        }
    }


    private static int getMonthDays(int month, int year) {
        int daysInMonth;
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            daysInMonth = 30;
        } else {
            if (month == 2) {
                daysInMonth = (year % 4 == 0) ? 29 : 28;
            } else {
                daysInMonth = 31;
            }
        }
        return daysInMonth;
    }

}
