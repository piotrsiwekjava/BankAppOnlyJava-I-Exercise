package J38_3;

public class Recipient extends NamesAndAccount{

    public Recipient() {
    }

    public Recipient(String nameF, String nameL, String accountNumb) {
        super(nameF, nameL, accountNumb);
    }



    @Override
    public String toString() {
        return getNameF() + "/" + getNameL() + "/" + getAccountNumb();
    }
}
