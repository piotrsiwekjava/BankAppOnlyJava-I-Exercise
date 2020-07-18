package J38_3;

import com.google.gson.Gson;

import java.io.*;
import java.util.*;

public class Account extends NamesAndAccount{

    private String city;
    private String login;
    private String loginPass; //Login Password
    private double balance;
    private ArrayList<Recipient>listRecip=new ArrayList<>();
    private HashSet<String>listTrans=new HashSet<>(); //transaction
    Gson json = new Gson();


    public Account(){}

    public Account(String nameF, String nameL,String city){
        super(nameF,nameL);
        this.city=city;
    }

    public Account(String nameF, String nameL, String city, String loginPass, double balance) {
        super(nameF, nameL);
        this.city = city;
        this.loginPass = loginPass;
        this.balance = balance;
    }

    public Account(String login, String accountNumb, String nameF, String nameL, String city) {
        super(nameF,nameL,accountNumb);
        this.city = city;
        this.login=login;
    }

    public Account(String login,String accountNumb, String nameF, String nameL, String city, double balance, String loginPass) {
        super(nameF, nameL, accountNumb);
        this.login=login;
        this.city = city;
        this.loginPass = loginPass;
        this.balance = balance;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLoginPass() {
        return loginPass;
    }

    public void setLoginPass(String loginPass) {
        this.loginPass = loginPass;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void drawLogin(String nameF,String nameL){
        int drawNum = new Random().nextInt(998)+1;
        String login=nameF.charAt(0)+nameL+drawNum;
        for (int i=0;i<login.length();i++){
            login=login.replace("ą", "a");
            login=login.replace("ć", "c");
            login=login.replace("ę", "e");
            login=login.replace("ł", "l");
            login=login.replace("ń", "n");
            login=login.replace("ó", "o");
            login=login.replace("ś", "s");
            login=login.replace("ź", "z");
            login=login.replace("ż", "z");
            login=login.replace("Ą", "A");
            login=login.replace("Ć", "C");
            login=login.replace("Ę", "E");
            login=login.replace("Ł", "L");
            login=login.replace("Ń", "N");
            login=login.replace("Ó", "O");
            login=login.replace("Ś", "S");
            login=login.replace("Ź", "Z");
            login=login.replace("Ż", "Z");
            this.login=login.trim();
        }
        System.out.println(login);
    }
    public void addRecipient (String nameF, String nameL, String accountNumb) {
        Recipient recipient = new Recipient(nameF,nameL,accountNumb);
        this.listRecip.add(recipient);
    }
    public void addOldRecipients(Account account, String wtabwiersz){
        int k=wtabwiersz.length();
        k-=1;
        String newwiersz = wtabwiersz.substring(1,k);
        newwiersz=newwiersz.replace(" ","");
        if (newwiersz.contains(",")) {
            String[] t2tabwiersz = newwiersz.split(",");
            for (int i = 0; i < t2tabwiersz.length; i++) {
                String wt2twiersz = t2tabwiersz[i];
                String[] t3twiersz = wt2twiersz.split("/");
                account.addRecipient(t3twiersz[0], t3twiersz[1], t3twiersz[2]);
            }
        }
        else if(newwiersz.contains("/")){
            String []t2tabwiersz=newwiersz.split("/");
            account.addRecipient(t2tabwiersz[0],t2tabwiersz[1],t2tabwiersz[2]);
        }
    }
    public void addOldTransMoney(String lTM){

        int ltmL = lTM.length();
        ltmL--;
        lTM = lTM.substring(1, ltmL);
        String[] listTransMoney = lTM.split(",");
        String hack=listTransMoney[0];
        if(hack.equals("")){
        }
        else if (!hack.equals("")) {
            for (int i = 0; i < listTransMoney.length; i++) {
                String wiersz2 = listTransMoney[i];
                String[] tr = wiersz2.split("/");
                String trZero = tr[0];
                trZero = trZero.replace(" ", "");
                String almost = (trZero + "/" + tr[1] + "/" + tr[2] + "/" + tr[3]);
                this.listTrans.add(almost);
            }
        }
    }

    public void transMoney (String str, Double amount){
            this.balance+=amount;
            Date today = Calendar.getInstance().getTime();
            String amountStr = String.valueOf(amount);
            String balanceStr = String.valueOf(this.balance);
            this.listTrans.add(str+"/"+amountStr+"/"+balanceStr+"/"+today);


    }
    public String getchooseRecip (String login, String str, String pathAL) throws FileNotFoundException {
        int dec=Integer.parseInt(str);
        dec--;
        String name = null;
        File plik = new File (pathAL);
        Scanner oc = new Scanner(plik);
        while (oc.hasNextLine()){
            String wiersz = oc.nextLine();
            String [] tabwiersz = wiersz.split(";");
            if (tabwiersz[0].equals(login)){
                String wtabwiersz = tabwiersz[7];
                int k=wtabwiersz.length();
                k-=1;
                String newwiersz = wtabwiersz.substring(1,k);
                newwiersz=newwiersz.replace(" ","");
                if (newwiersz.contains(",")) {
                    String[] t2tabwiersz = newwiersz.split(",");
                    if (dec<t2tabwiersz.length && dec>=0) {
                        String wt2twiersz = t2tabwiersz[dec];
                        String[] t3twiersz = wt2twiersz.split("/");
                        name = t3twiersz[0] + "_" + t3twiersz[1] + "_" + t3twiersz[2];
                    }
                    else {
                        System.out.println("Brak takiego numeru na liście!");
                        break;
                    }

                }
                else if(dec==0 && newwiersz.contains("/")){
                    String []t2tabwiersz=newwiersz.split("/");
                    name = t2tabwiersz[0]+"_"+t2tabwiersz[1]+"_"+t2tabwiersz[2];
                }
                else {
                    System.out.println("Błędna komenda");
                }
            }
        }
        oc.close();
        return name;

    }
    public void showChooseRecip (String login, String str, String pathAL) throws FileNotFoundException {
        int dec=Integer.parseInt(str);
        dec--;
        String name = null;
        File plik = new File (pathAL);
        Scanner oc = new Scanner(plik);
        while (oc.hasNextLine()){
            String wiersz = oc.nextLine();
            String [] tabwiersz = wiersz.split(";");
            if (tabwiersz[0].equals(login)){
                String wtabwiersz = tabwiersz[7];
                int k=wtabwiersz.length();
                k-=1;
                String newwiersz = wtabwiersz.substring(1,k);
                newwiersz=newwiersz.replace(" ","");
                if (newwiersz.contains(",")) {
                    String[] t2tabwiersz = newwiersz.split(",");
                    String wt2twiersz = t2tabwiersz[dec];
                    String[] t3twiersz = wt2twiersz.split("/");
                    System.out.println(t3twiersz[0]+" "+t3twiersz[1]+" "+t3twiersz[2]);
                }
                else if(newwiersz.contains("/")){
                    String []t2tabwiersz=newwiersz.split("/");
                    System.out.println(t2tabwiersz[0]+" "+t2tabwiersz[1]+" "+t2tabwiersz[2]);
                }
            }
        }
        oc.close();
    }
    public void showTransMoney (String login, String pathAl) throws FileNotFoundException {
        File plik = new File(pathAl);
        Scanner oc = new Scanner(plik);
        System.out.println("Nr.Transakcji . Odbiorca / Nr.Rachunku / KwotaPrzelewu / SaldoPoPrzelewie / DataTransacji");
        while (oc.hasNextLine()) {
            String wiersz = oc.nextLine();
            String[] tabwiersz = wiersz.split(";");
            if (tabwiersz[0].equals(login)) {
                String lTM = tabwiersz[8];
                int ltmL = lTM.length();
                ltmL--;
                lTM = lTM.substring(1, ltmL);
                String[] listTransMoney = lTM.split(",");
                String hack=listTransMoney[0];
                if(hack.equals("")){
                    System.out.println("Brak historii transakcji na koncie.");
                    break;
                }
                else if (!hack.equals("")) {
                    int lp = 0;
                    for (int i = 0; i < listTransMoney.length; i++) {
                        String wiersz2 = listTransMoney[i];
                        String[] tr = wiersz2.split("/");
                        ++lp;
                        String trZero = tr[0];
                        trZero = trZero.replace(" ", "");
                        trZero = trZero.replace("_", " ");
                        String almost = (lp + "." + trZero + "/" + tr[1] + "/" + tr[2] + "/" + tr[3]);
                        System.out.println(almost);
                    }
                }
            }
        }
        oc.close();
    }
    public ArrayList<Recipient> getListRecip() {
        return listRecip;
    }

    public HashSet<String> getListTrans() {
        return listTrans;
    }

    public void showRecipList(){
        int i=1;                                                    //pokaż listę odbiorców
        for (Recipient recipient: listRecip){
            System.out.println(i++ +"-"+recipient);
        }
    }

    @Override
    public String toString() {
        return  getLogin()+";"+getAccountNumb()+ ";"+ getNameF()+";"+getNameL()+";" + getCity() +
                ";"+ getBalance()+";"+getLoginPass()+";"+getListRecip()+";"+getListTrans()+";";
    }
}
