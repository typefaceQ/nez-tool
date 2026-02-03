/*
 *  |CrAsHeR CrAsHeR CrAsHeR CrAsHeR CrAsHeR CrAsHeR|
 * 
 *  Данная программа написана на языке Java. 
 *  Сама программа представляет собой мульти-тул, в котором есть всё 
 *  необходимое — от базовых команд для спама в Discord до краша серверов Minecraft и так далее. 
 *  Программа была написана одним человеком — nezi4k.
 *  Автор: nezi4k
 *  Версия: 1.0
 *  Дата создания: ꜱᴛᴀʀᴛᴇᴅ ꜰʀᴏᴍ 23.05.2025
 * 

 * 
 *  |CrAsHeR CrAsHeR CrAsHeR CrAsHeR CrAsHeR CrAsHeR|
 */

package CrAsHeR;

import java.util.Scanner;


public class crasher {
  public static final String COLOR_RED = "\u001b[31m";
  public static final String COLOR_GREEN = "\u001b[32m";
  public static final String COLOR_YELLOW = "\u001b[33m";
  public static final String COLOR_RESET = "\u001b[0m";
  public static final String PASSWORD = "0100101100011100";
  public static final String NAME = "CrAsHeR";


   public crasher() {
   }

   public static void Delay200ms() {
      try {
         Thread.sleep(200);
      } catch (InterruptedException var1) {
      }

   }

   public static void Delay5sek() {
      try {
         Thread.sleep(5000);
      } catch (InterruptedException var1) {
      }

   }

      public static void Delay1sek() {
      try {
         Thread.sleep(1000);
      } catch (InterruptedException var1) {
      }

   }

   static void Delay5sekWithSpinner() {
    String[] spinner = {"⠋","⠙","⠹","⠸","⠼","⠴","⠦","⠧","⠇","⠏"};
    long endTime = System.currentTimeMillis() + 5000;
    int i = 0;

    try {
        while (System.currentTimeMillis() < endTime) {
            System.out.print("\r" + COLOR_RED + "                                     │                                " + spinner[i++ % spinner.length] + " Загрузка..." + "                              │");
            Thread.sleep(80);
        }
    } catch (InterruptedException ignored) {}
   }


   public static void main(String[] args) {
      Scanner scanner = new Scanner(System.in);

      while(true) {
         System.out.print("\u001b[H\u001b[J");

         try {
            (new ProcessBuilder(new String[]{"clear"})).inheritIO().start().waitFor();
         } catch (Exception var11) {
            var11.printStackTrace();
         }
         Delay200ms();
         System.out.println(COLOR_RED + "                                     ┌───────────────────────────────────────────────────────────────────────────┐");
         Delay200ms();
         System.out.println(COLOR_RED + "                                     │                                                                           │");
         Delay200ms();
         System.out.println(COLOR_RED + "                                     │                                                                           │");
         Delay200ms();
         System.out.println(COLOR_RED + "                                     │                                                                           │");
         Delay200ms();
         System.out.println(COLOR_RED + "                                     │        ▄████▄   ██▀███   ▄▄▄        ██████  ██░ ██ ▓█████  ██▀███         │");
         Delay200ms();
         System.out.println(COLOR_RED + "                                     │       ▒██▀ ▀█  ▓██ ▒ ██▒▒████▄    ▒██    ▒ ▓██░ ██▒▓█   ▀ ▓██ ▒ ██▒       │");
         Delay200ms();
         System.out.println(COLOR_RED + "                                     │       ▒▓█    ▄ ▓██ ░▄█ ▒▒██  ▀█▄  ░ ▓██▄   ▒██▀▀██░▒███   ▓██ ░▄█ ▒       │");
         Delay200ms();
         System.out.println(COLOR_RED + "                                     │       ▒▓▓▄ ▄██▒▒██▀▀█▄  ░██▄▄▄▄██   ▒   ██▒░▓█ ░██ ▒▓█  ▄ ▒██▀▀█▄         │");
         Delay200ms();
         System.out.println(COLOR_RED + "                                     │       ▒ ▓███▀ ░░██▓ ▒██▒ ▓█   ▓██▒▒██████▒▒░▓█▒░██▓░▒████▒░██▓ ▒██▒       │");
         Delay200ms();
         System.out.println(COLOR_RED + "                                     │       ░ ░▒ ▒  ░░ ▒▓ ░▒▓░ ▒▒   ▓▒█░▒ ▒▓▒ ▒ ░ ▒ ░░▒░▒░░ ▒░ ░░ ▒▓ ░▒▓░       │");
         Delay200ms();
         System.out.println(COLOR_RED + "                                     │         ░  ▒     ░▒ ░ ▒░  ▒   ▒▒ ░░ ░▒  ░ ░ ▒ ░▒░ ░ ░ ░  ░  ░▒ ░ ▒░       │");
         Delay200ms();
         System.out.println(COLOR_RED + "                                     │       ░          ░░   ░   ░   ▒   ░  ░  ░   ░  ░░ ░   ░     ░░   ░        │");
         Delay200ms();
         System.out.println(COLOR_RED + "                                     │       ░ ░         ░           ░  ░      ░   ░  ░  ░   ░  ░   ░            │");
         Delay200ms();
         System.out.println(COLOR_RED + "                                     │       ░                      ░         ░                                  │");
         Delay200ms();
         System.out.println(COLOR_RED + "                                     │                                                                           │");
         Delay200ms();
         System.out.println(COLOR_RED + "                                     │                             Created by Nezi4k                             │");
         Delay200ms();
         Delay5sekWithSpinner();
         Delay200ms();
         System.out.println(COLOR_RED + "                                     │                                  Готово!                                  │");
         Delay200ms();
         System.out.println(COLOR_RED + "                                     └───────────────────────────────────────────────────────────────────────────┘");
         Delay1sek();
         next(args);
         scanner.close();
      }


   }


   public static void next(String[] args) {
      System.out.print("\u001b[H\u001b[J");

      try {
         (new ProcessBuilder(new String[]{"clear"})).inheritIO().start().waitFor();
      } catch (Exception var5) {
         var5.printStackTrace();
      }

      System.out.println("");
      System.out.println(COLOR_RED + "                 ┌───────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
      System.out.println(COLOR_RED + "                 │                                                                                                               │");
      System.out.println(COLOR_RED + "                 │                                                                                                               │");
      System.out.println(COLOR_RED + "                 │                                                                                                               │");
      System.out.println(COLOR_RED + "                 │                        ▄████▄   ██▀███   ▄▄▄        ██████  ██░ ██ ▓█████  ██▀███                             │");
      Delay200ms();
      System.out.println(COLOR_RED + "                 │                        ▒██▀ ▀█  ▓██ ▒ ██▒▒████▄    ▒██    ▒ ▓██░ ██▒▓█   ▀ ▓██ ▒ ██▒                          │");
      Delay200ms();
      System.out.println(COLOR_RED + "                 │                        ▒▓█    ▄ ▓██ ░▄█ ▒▒██  ▀█▄  ░ ▓██▄   ▒██▀▀██░▒███   ▓██ ░▄█ ▒                          │");
      Delay200ms();
      System.out.println(COLOR_RED + "                 │                        ▒▓▓▄ ▄██▒▒██▀▀█▄  ░██▄▄▄▄██   ▒   ██▒░▓█ ░██ ▒▓█  ▄ ▒██▀▀█▄                            │");
      Delay200ms();
      System.out.println(COLOR_RED + "                 │                        ▒ ▓███▀ ░░██▓ ▒██▒ ▓█   ▓██▒▒██████▒▒░▓█▒░██▓░▒████▒░██▓ ▒██▒                          │");
      Delay200ms();
      System.out.println(COLOR_RED + "                 │                        ░ ░▒ ▒  ░░ ▒▓ ░▒▓░ ▒▒   ▓▒█░▒ ▒▓▒ ▒ ░ ▒ ░░▒░▒░░ ▒░ ░░ ▒▓ ░▒▓░                          │");
      Delay200ms();
      System.out.println(COLOR_RED + "                 │                        ░  ▒     ░▒ ░ ▒░  ▒   ▒▒ ░░ ░▒  ░ ░ ▒ ░▒░ ░ ░ ░  ░  ░▒ ░ ▒░                            │");
      Delay200ms();
      System.out.println(COLOR_RED + "                 │                        ░          ░░   ░   ░   ▒   ░  ░  ░   ░  ░░ ░   ░     ░░   ░                           │");
      Delay200ms();
      System.out.println(COLOR_RED + "                 │                        ░ ░         ░           ░  ░      ░   ░  ░  ░   ░  ░   ░                               │");
      Delay200ms();
      System.out.println(COLOR_RED + "                 │                        ░                      ░         ░                                                     │");
      System.out.println(COLOR_RED + "                 │                                                                                                               │");
      System.out.println(COLOR_RED + "                 │                                              Created by Nezi4k                                                │");
      System.out.println(COLOR_RED + "                 └───────────────────────────────────────────────────────────────────────────────────────────────────────────────┘");
      System.out.println("");
      System.out.println(COLOR_RED + "                 ┌──────────────────────────────────────────────────────────────────────────────────────────────────────────➤ 0 ᴇɴᴅ");
      System.out.println(COLOR_RED + "                 │➤ 1. Discord Webhook spam           │➤ 5. Discord Bot attack             │➤ 9. в разработке...      │  ");
      System.out.println(COLOR_RED + "                 │                                    │                                    │                          │  ");
      System.out.println(COLOR_RED + "                 │➤ 2. Port scaner                    │➤ 6. в разработке...                │➤ 10. в разработке...     │  ");
      System.out.println(COLOR_RED + "                 │                                    │                                    │                          │  ");
      System.out.println(COLOR_RED + "                 │➤ 3. http DDoS                      │➤ 7. в разработке...                │➤ 11. в разработке...     └─────➤ 100 ᴍᴇɴᴜ");
      System.out.println(COLOR_RED + "                 │                                    │                                    │                            ");
      System.out.println(COLOR_RED + "                 │➤ 4. IP scaner                      │➤ 8. в разработке...                │➤ 12. в разработке...       ");
      System.out.println(COLOR_RED + "                 │                                    │                                    │                             ");
      System.out.println(COLOR_RED + "                 │");
      Scanner scanner = new Scanner(System.in);

      while(true) {
         System.out.print(COLOR_RED + "                 └➤ Выберите функцию: ");
         String input = scanner.nextLine().trim();

         int choice;
         try {
            choice = Integer.parseInt(input);
         } catch (NumberFormatException var6) {
            System.out.println(COLOR_RED + "                 └─?─➤ Пожалуйста, введите число от 0 до 12.");
            continue;
         }

         if (choice == 0) {
            System.out.println(COLOR_RED + "                 └➤ Выход из программы... пару секунд.. ");
            scanner.close();
            return;
         }

         if (choice == 100) {
            System.out.println(COLOR_RED + "                 └➤ Возвращаемся в главное меню...");
            Delay200ms();
            next(args);
         }

         switch (choice) {
            case 1:
               Function1.execute();
               break;
            case 2:
               Function2.execute();
               break;
            case 3:
               Function3.execute();
               break;
            case 4:
               Function4.execute();
               break;
            case 5:
               Function5.execute();
               break;
            case 6:
               Function6.execute();
               break;
            case 7:
               Function7.execute();
               break;
            case 8:
               Function8.execute();
               break;
            case 9:
               Function9.execute();
               break;
            case 10:
               Function10.execute();
               break;
            case 11:
               Function11.execute();
               break;
            case 12:
               Function12.execute();
               break;
            default:
               System.out.println(COLOR_RED + "                 └─?─➤ Неверный выбор. Введите число от 1 до 12.");
         }
      }
   }
}
