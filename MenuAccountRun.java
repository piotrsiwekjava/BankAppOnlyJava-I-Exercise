package J38_3;





import com.google.gson.Gson;

import java.io.*;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class MenuAccountRun {


    Scanner sc = new Scanner(System.in);
    String power = "ON", dec, nameF, nameL, city, login, pass, password, password2, accountnums;
    String typeNorm="normal";
    String typeNoNumbers="noNumbers";
    String typeOnlyNumbers="onlyNumbers";
    public String pathAL = "C:\\Users\\Hores di En'Claws\\Desktop\\Projekty ALX\\src\\J38_3\\AccountsList.txt";
    public String pathLL = "C:\\Users\\Hores di En'Claws\\Desktop\\Projekty ALX\\src\\J38_3\\LoginsList";
    public String pathNA = "C:\\Users\\Hores di En'Claws\\Desktop\\Projekty ALX\\src\\J38_3\\NumbersAccounts.txt";
    public String pathJsonlist= "C:\\Users\\Hores di En'Claws\\Desktop\\Projekty ALX\\src\\J38_3\\Jsonlist.txt";
    public char [] listChar = {'!','@','#','`','$','%','^','&','*','(',')','_','-','=','+','{','}','[',']',';',':','"','<','<',',','>','.','/','?','|','`'};
    public char [] listChar2 = {'!','@','#','`','$','%','^','&','*','(',')','_','-','=','+','{','}','[',']',';',':','"','<','<','>','/','?','|','`'};
    public char [] listABC = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','w','v','x','y','z'};
    public char [] listPOL = {'ą','ć','ę','ł','ń','ó','ż','ź'};
    public char [] list123 = {'1','2','3','4','5','6','7','8','9','0'};
    private ArrayList<Account> listAcc = new ArrayList<>(); // Accounts list
    public Gson json = new Gson();


    public void menu() throws IOException {
        System.out.println("Witamy w systemie logowania do konta banku xxx");
        while (!power.equals("OFF")) {

            power="ON";
            System.out.println("Menu: 1-dodaj konto, 2-zaloguj na konto,8-wyświetl konta, 9-Koniec pracy programu");
            dec = getScannerIn(typeNorm);
            if (dec=="!@#"){menu();}
            if(dec.length()>3){systemOFF();}

            if (dec.equals("1")) {
                addAccountInMenu();
            } else if (dec.equals("2")) {
                logowanie();

            } else if (dec.equals("8")) {
                wyswietlListeKont(pathAL);


            } else if (dec.equals("9")) {
                System.out.println("Do widzenia");
                zapiszWJson();
                sc.close();
                power = "OFF";
            } else {
                System.out.println("Nie rozpoznana komenda!");
            }

        }
    }

    public void logowanie() throws IOException {
        int noRetry=0; //powrót do menu głównego jeśli będą trzy błedy
        while (!power.equals("BackMain")) {

            boolean found = false;
            power = "ON";
            System.out.println("Logowanie do konta");
            System.out.print("Podaj login:");
            login = getScannerIn(typeNorm);
            if (login=="!@#"){logowanie();}
            File plik = new File(pathAL);
            Scanner oc = new Scanner(plik);
            while(oc.hasNextLine()) {
                String wiersz = oc.nextLine();
                String[] tabwiersz = wiersz.split(";");
                Account account = new Account(tabwiersz[0],tabwiersz[1],tabwiersz[2],tabwiersz[3],tabwiersz[4]);
                account.setBalance(Double.parseDouble(tabwiersz[5]));
                account.setLoginPass(tabwiersz[6]);
                String wtabwiersz = tabwiersz[7];
                account.addOldRecipients(account,wtabwiersz);
                listAcc.add(account);
            }
            oc.close();

            for (Account account : listAcc) {
                if (account.getLogin().equals(login)) {
                    found = true;
                    System.out.print("Podaj hasło: ");
                    noRetry=0;
                    while (!power.equals("BackMain")) {
                        pass = getScannerInforPassword();
                        if (pass=="!@#"){logowanie();}
                        if (account.getLoginPass().equals(pass)) {
                            System.out.println("");

                            System.out.println("Witaj "+account.getNameF()+" "+account.getNameL()+".");         //menu logowania
                            while (!power.equals("OFF")){
                                power="ON";
                                System.out.println("Konto: "+account.getLogin());
                                System.out.println("Numer rachunku: "+account.getAccountNumb());
                                while (true){
                                System.out.println("1-dodaj odbiorce, 2-wykonaj przelew, 3-dokonaj wpłaty, 4-historia transakcji, 5-wyloguj");
                                dec=getScannerIn(typeNorm);
                                if (!dec.equals("!@#")){break;}
                                }

                                if (dec.equals("1")){
                                    addRecipientsToList(account);
                                }
                                else if (dec.equals("2")){
                                    makeTransMoneyOut(account);
                                }
                                else if (dec.equals("3")){
                                    makeTransMoneyIn(login,account,pathAL);
                                }
                                else if (dec.equals("4")){
                                    account.showTransMoney(login,pathAL);
                                }
                                else if (dec.equals("5")){
                                    System.out.println("Wylogowałeś się z konta.");
                                    power="BackMain";
                                    menu();
                                }
                                else if (dec.length()>3){systemOFF();}
                                else {
                                    System.out.println("Błędna komenda");
                                }

                            }
                            System.out.println("");

                        } else if (pass.equals("K")) {
                            System.out.println("Powrót do menu głównego");
                            menu();
                        } else {
                            System.out.println("Podałeś złe hasło. Popraw lub wpisz K-koniec");
                            noRetry++;
                        }
                        if (noRetry==3){
                            systemOFF();
                        }
                    }

                }
            }
            noRetry++;
            if (noRetry==3){
                systemOFF();
            }



        }
    }

    public void zapiszWJson() throws IOException {
        File plikjson = new File(pathJsonlist);
        PrintWriter clearA = new PrintWriter(plikjson);
        clearA.print("");
        clearA.close();
        File plik = new File(pathAL);
        Scanner oc=new Scanner(plik);
        while(oc.hasNextLine()) {
            String jsonList = this.json.toJson(oc.nextLine());
            FileWriter zapis = new FileWriter(this.pathJsonlist, true);
            zapis.append(jsonList+"\n");
            zapis.close();
        }
        oc.close();
    }

    public void zapiszKonto(String login, Account account, String pathAL) throws IOException {
        int find = 0;
        File plik = new File(pathAL);
        Scanner oc = new Scanner(plik);
        listAcc.clear();
        while (oc.hasNextLine()){
            String wiersz = oc.nextLine();
            String [] tabwiersz = wiersz.split(";");

            if(!tabwiersz[0].equals(login)) {
                Account tab = new Account(tabwiersz[0], tabwiersz[1], tabwiersz[2], tabwiersz[3], tabwiersz[4]);
                tab.setBalance(Double.parseDouble(tabwiersz[5]));
                tab.setLoginPass(tabwiersz[6]);
                this.listAcc.add(tab);
                String listRec = tabwiersz[7];
                tab.addOldRecipients(tab,listRec);
                String listTrans = tabwiersz[8];
                tab.addOldTransMoney(listTrans);

            }
            else if (tabwiersz[0].equals(login)){
                find=1;
                this.listAcc.add(account);
                String listRecips = tabwiersz[8];
                account.addOldTransMoney(listRecips);
            }
        }
        if (find ==0){
            this.listAcc.add(account);
        }
        oc.close();
        PrintWriter pw = new PrintWriter(pathAL);
        pw.print("");
        pw.close();
        FileWriter fw = new FileWriter(pathAL, true);
        for (Account acc:listAcc) {
            fw.append(acc + "\n");
        }
        fw.close();

    }
    public void addAccountInMenu() throws IOException {
        System.out.println("Dodajesz konto. Potrzebne są dane właściciela.");
        while(true) {
            System.out.println("Podaj imie:");
            nameF = getScannerIn(typeNoNumbers);
            if (!nameF.equals("!@#")) {
                break;
            }
        }
        while (true) {
            System.out.println("Podaj nazwisko:");
            nameL = getScannerIn(typeNoNumbers);
            if (!nameL.equals("!@#")) {
                break;
            }
        }
        while (true) {
            System.out.println("Podaj miasto:");

            city = getScannerIn(typeNoNumbers);
            if (!city.equals("!@#")) {
                break;
            }
        }
        if (nameF.equals("")||nameL.equals("")||city.equals("")){
            System.out.println("Użyłeś Entera. Konto nie zostanie założone");
            menu();}

        Account account = new Account(nameF, nameL, city);
        System.out.print("Wygenerowany login do konta: ");

        boolean have =false;
        while (have==false) {
            account.drawLogin(nameF, nameL);           //sprawdzanie czy login istniej, a jeśli nie to akceptacja
            File plik = new File(pathLL);
            Scanner oc = new Scanner(plik);
            ArrayList<String>list=new ArrayList<>();
            while (oc.hasNextLine()) {
                list.add(oc.nextLine());
            }
            if (!list.contains(account.getLogin())||list.size()==0){
                zapiszLogin(account.getLogin());
                have=true;
                break;
            }
        }
        while (!power.equals("BackMain")) {
            while (true) {
                System.out.println("Wprowadź hasło do logowania na koncie: ");
                password = getScannerInforPassword();
                if (!password.equals("!@#")) {
                    break;
                }
            }
            while(true) {
                System.out.println("Powtórz hasło:");
                password2 = getScannerInforPassword();
                if (!password2.equals("!@#")) {
                    break;
                }
            }
            if (password.equals(password2)&&!password.equals("")) {
                account.setLoginPass(password);
                System.out.println("Hasło się zgadza. " +
                        "Następuje generowanie numeru rachunku.");

                System.out.print("Wygenerowany numer rachunku: ");
                account.draw26(pathNA);
                System.out.println("");

                try {
                    zapiszKonto(account.getLogin(), account, pathAL);                                  //stworzenie konta
                    System.out.println("Konto " + account.getLogin() + " zostało utworzone");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Powrót do menu");
                break;
            } else {
                System.out.println("Hasło się nie zgadza. Popraw je.");

            }
        }

    }
    public void wyswietlListeKont(String pathAl) throws FileNotFoundException {
        File plik = new File(pathAl);
        Scanner oc = new Scanner(plik);
        while (oc.hasNextLine()) {
            System.out.println(oc.nextLine());
        }
    }
    public void zapiszLogin (String login) throws IOException {
        FileWriter fw = new FileWriter(pathLL,true);
        fw.append(login+"\n");
        fw.close();

    }
    public void addRecipientsToList (Account account) throws IOException {
        System.out.println("Dodajesz odbiorcę.");
        while (true) {
            System.out.print("Podaj imię: ");
            nameF = getScannerIn(typeNoNumbers);
            if (nameF.equals("!@#")) {
                break;
            }
            System.out.print("Podaj nazwisko: ");
            nameL = getScannerIn(typeNoNumbers);
            if (nameL.equals("!@#")) {
                break;
            }
            System.out.print("Podaj numer rachunku: ");
            accountnums = getScannerIn(typeOnlyNumbers);
            if (accountnums.equals("!@#")) {
                break;
            }

            account.addRecipient(nameF, nameL, accountnums);
            System.out.println("Odbiorca " + nameF + " " + nameL + " dodany pomyślnie.");
            zapiszKonto(login, account, pathAL);
            break;
        }
    }
    public void makeTransMoneyOut(Account account) throws IOException {
        while (true) {
            System.out.println("Wykonujesz przelew. Odbiorca zdefioniowany? 1-Tak, 2-Nie, 3-powrót.");
            String chooseRp = null;
            dec = getScannerIn(typeNorm);
            if (dec.equals("!@#")){break;}
            if (dec.length() > 3) {
                systemOFF();
            }
            if (dec.equals("1")) {
                System.out.println("Wybierz numer odbiorcy:");
                account.showRecipList();
                dec = getScannerIn(typeOnlyNumbers);
                if (dec.equals("!@#")){break;}
                if (!dec.equals("")) {
                    chooseRp = account.getchooseRecip(login, dec, pathAL);
                    if (chooseRp==null){
                        System.out.println("Błąd");
                        break;
                    }
                }
                else {
                    System.out.println("Bład");
                    break;
                }
            } else if (dec.equals("2")) {
                System.out.print("Podaj imię i nazwisko lub nazwę odbiorcy: ");
                nameF = getScannerIn(typeNoNumbers).replace(" ","_");
                if (nameF.equals("!@#")){break;}
                System.out.print("Podaj numer rachunku: ");
                accountnums = getScannerIn(typeOnlyNumbers);
                if (accountnums.equals("!@#")){break;}
                chooseRp = nameF + "_" + accountnums;
            } else if (dec.equals("3")) {
                System.out.println("Anulowanie przelewu. Powrót do menu konta.");
                break;
            } else {
                System.out.println("Błedna komenda");
                break;
            }
            System.out.println("Podaj sumę na jaką chcesz dokonać przelewu:");
            String amountstr = getScannerInMoneyTrans().replace("-","").replace(",",".");
            if (amountstr.equals("!@#")){break;}
            double amountdb = Double.parseDouble(amountstr);
            if (((account.getBalance() - amountdb) >= 0) && amountdb > 0) {
                System.out.println("Aby potwierdzić przelew wpisz hasło które się wyświetli.");
                int dronums = new Random().nextInt(9998) + 1;
                String str = String.valueOf(dronums);
                if (str.length() == 3) {
                    str = "0" + str;
                }                            //dodawanie zer do hasła
                else if (str.length() == 2) {
                    str = "00" + str;
                } else if (str.length() == 1) {
                    str = "000" + str;
                }
                System.out.println(str);
                String decyz = getScannerIn(typeNorm);
                if (decyz.equals("!@#")){break;}
                if (decyz.equals(str)) {
                    System.out.println("Hasło się zgadza. Wykonuję przelew.");
                    amountdb*=(-1);
                    account.transMoney(chooseRp, amountdb);
                    zapiszKonto(login, account, pathAL);
                    System.out.println("Przelew wykonany pomyślnie na kwote " + amountstr + "pln dla obiorcy "+chooseRp+".");
                } else {
                    System.out.println("Anulacja przelewu. Powrót.");
                }
            } else {
                System.out.println("Brak środków na koncie. Powtórzyć przelew? 1-Tak, 2-Nie,powrót.");
                dec = getScannerIn(typeNorm);
                if (dec.equals("!@#")){break;}
                if (dec.equals("1")) {

                } else if (dec.equals("2")) {
                    break;
                } else {
                    System.out.println("Błędna komenda");
                    break;
                }

            }
        }


    }

    public void makeTransMoneyIn(String login,Account account,String pathAL) throws IOException {
        while (true) {
            System.out.println("Dokonujesz wpłaty. Podaj kwotę:");
            String amount = getScannerInMoneyTrans().replace(",", ".");
            if (amount.equals("!@#")) {
                break;
            }
            double amountdb = Double.parseDouble(amount);
            account.transMoney("Zasilenie_Konta", amountdb);
            System.out.println("Konto zostało zasilone na kwotę " + amountdb);
            zapiszKonto(login, account, pathAL);
            break;
        }
    }
    public void systemOFF (){
        sc.close();
        throw new ArithmeticException("Wykryto atak! Przerwa w działaniu programu!");
    }
    public void getTheNumbersAccounts() throws FileNotFoundException {
        File plik = new File(pathAL);
        Scanner oc=new Scanner(plik);
        ArrayList<String>listNA=new ArrayList<>();
        while (oc.hasNextLine()){
            String wiersz = oc.nextLine();
            String []tabwiersz=wiersz.split(";");
            listNA.add(tabwiersz[1]);
        }
        PrintWriter pw = new PrintWriter(pathNA);
        pw.print(listNA);
        oc.close();
        pw.close();
    }
    public String getScannerIn (String type){
        String wordAdded = sc.nextLine().trim();
        int check=0;
        for (int i = 0; i < wordAdded.length(); i++) {
            for (int j = 0; j < listChar.length; j++) {
                if (wordAdded.charAt(i) == listChar[j]) {
                    check = 1;

                }
            }
        }
        if (type.equals(typeNoNumbers)){
            for (int i=0;i<wordAdded.length();i++) {
                for (int k=0;k<list123.length;k++){
                    if (wordAdded.charAt(i)==list123[k]){
                        check=3;
                    }
                }
            }
            if (check==3){System.out.println("Nie można stosować cyfr");}
        }
        else if (type.equals(typeOnlyNumbers)){
            for (int i=0;i<wordAdded.length();i++) {
                for (int k=0;k<listABC.length;k++){
                    if (wordAdded.charAt(i)==listABC[k]){
                        check=4;
                    }
                }
            }
            if (check==4){System.out.println("Wprowadzaj tylko cyfry!");}
        }
        if (check==1) {
            System.out.println("Nie można stosować znaków specjalnych!");
            System.out.println("Usunąć znaki specjalne? 1-Tak, 2-Nie");
            String decChar = sc.nextLine().trim();
            if (decChar == "1") {
                for (int i=0;i<wordAdded.length();i++) {
                    for (int j = 0; j < listChar.length; j++) {
                        if (wordAdded.charAt(i) == listChar[j]) {
                            wordAdded.replace("i","");
                            System.out.println("Znaki specjalne usunięte");
                            check=0;

                        }
                    }
                }
            }
        }
        if (check==0){
            return wordAdded;
        }
        if (check!=0){
            wordAdded="!@#";

        }
        return wordAdded;

    }public String getScannerInMoneyTrans () throws IOException {
        String wordAdded = sc.nextLine();
        int check=0;
        try {
            Double checkDb = Double.parseDouble(wordAdded);         //sprawdzanie czy ma błąd
        }
        catch(NumberFormatException e){
            System.out.println("Błąd przy wprowadzaniu danych! Trzeba je poprawić.");
            check=2;
        }
        if (check==0) {
            for (int i = 0; i < wordAdded.length(); i++) {
                for (int j = 0; j < listChar2.length; j++) {
                    if (wordAdded.charAt(i) == listChar2[j]) {
                        check = 1;

                    }
                }
            }
        }
        if (check==1) {
            System.out.println("Nie można stosować znaków specjalnych!");
            System.out.println("Usunąć znaki specjalne? 1-Tak, 2-Nie");
            String decChar = sc.nextLine().trim();
            if (decChar == "1") {
                for (int i=0;i<wordAdded.length();i++) {
                    for (int j = 0; j < listChar2.length; j++) {
                        if (wordAdded.charAt(i) == listChar2[j]) {
                            wordAdded.replace("i","");
                            System.out.println("Znaki specjalne usunięte");
                            check=0;

                        }
                    }
                }
            }
        }
        if (check==0){
            return wordAdded;
        }
        else {
            wordAdded="!@#";

        }
        return wordAdded;

    }public String getScannerInforPassword (){
        String wordAdded = sc.nextLine();
        int check=0;
        for (int i=0;i<wordAdded.length();i++) {
                if (wordAdded.charAt(i) == ';') {
                    check=1;
                }
        }
        if (check==1) {
            System.out.println("Nie można stosować znaku ';'");
            System.out.println("Usunąć znak specjalny? 1-Tak, 2-Nie");
            String decChar = sc.nextLine().trim();
            if (decChar == "1") {
                check=0;
                wordAdded.replace(";","");
                System.out.println("Znaki specjalne usunięte");
            }
        }
        if (check==0){
            return wordAdded;
        }
        else {
            wordAdded="!@#";

        }
        return wordAdded;

    }

}
