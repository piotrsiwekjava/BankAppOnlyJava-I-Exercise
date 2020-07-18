package J38_3;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class NamesAndAccount {
    private String nameF;  //First Name
    private String nameL;  //Last Name
    private String accountNumb; // Account Number

    protected NamesAndAccount() {
    }

    protected NamesAndAccount(String nameF, String nameL) {
        this.nameF = nameF;
        this.nameL = nameL;
    }

    protected NamesAndAccount(String accountNumb) {
        this.accountNumb = accountNumb;
    }

    protected NamesAndAccount(String nameF, String nameL, String accountNumb) {
        this.nameF = nameF;
        this.nameL = nameL;
        this.accountNumb = accountNumb;
    }

    public String getNameF() {
        return nameF;
    }

    public void setNameF(String nameF) {
        this.nameF = nameF;
    }

    public String getNameL() {
        return nameL;
    }

    public void setNameL(String nameL) {
        this.nameL = nameL;
    }

    public String getAccountNumb() {

        return accountNumb;
    }

    public void setAccountNumb(String accountNumb) {
        this.accountNumb = accountNumb;
    }

    public void draw26 (String pathNA) throws IOException {
        int [] drawNums = new int[26];
        String listdraw = new String();
        while(true) {
            listdraw = new String();
            drawNums[0] = 1;
            drawNums[1] = 4;
            drawNums[2] = new Random().nextInt(10);
            drawNums[3] = new Random().nextInt(10);
            drawNums[4] = new Random().nextInt(10);
            drawNums[5] = new Random().nextInt(10);
            drawNums[6] = new Random().nextInt(10);
            drawNums[7] = new Random().nextInt(10);
            drawNums[8] = new Random().nextInt(10);
            drawNums[9] = new Random().nextInt(10);
            drawNums[10] = new Random().nextInt(10);
            drawNums[11] = new Random().nextInt(10);
            drawNums[12] = new Random().nextInt(10);
            drawNums[13] = new Random().nextInt(10);
            drawNums[14] = new Random().nextInt(10);
            drawNums[15] = new Random().nextInt(10);
            drawNums[16] = new Random().nextInt(10);
            drawNums[17] = new Random().nextInt(10);
            drawNums[18] = new Random().nextInt(10);
            drawNums[19] = new Random().nextInt(10);
            drawNums[20] = new Random().nextInt(10);
            drawNums[21] = new Random().nextInt(10);
            drawNums[22] = new Random().nextInt(10);
            drawNums[23] = new Random().nextInt(10);
            drawNums[24] = new Random().nextInt(10);
            drawNums[25] = new Random().nextInt(10);
            String num;
            for (int i = 0; i < drawNums.length; i++) {
             num = String.valueOf(drawNums[i]);
             listdraw+=num;

            }
            File plik = new File(pathNA);
            Scanner oc = new Scanner(plik);
            ArrayList <String> listNA=new ArrayList<>();
            while (oc.hasNextLine()) {
                String wiersz = oc.nextLine();
                listNA.add(wiersz);
            }
            oc.close();
            if (!listNA.contains(listdraw)){

                break;
            }
        }
        this.accountNumb=listdraw;
        FileWriter fw = new FileWriter(pathNA,true);
        fw.append(listdraw+"\n");
        fw.close();
        showaccnum();

    }
    public void showaccnum (){      //show account number
        System.out.print(accountNumb);

    }
}
