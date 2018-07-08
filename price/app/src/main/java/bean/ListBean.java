package bean;

/**
 * Created by asus on 2018/4/16.
 */

public class ListBean {
    private String linsice;
    private String name;
    private String money;

    public ListBean(String linsice, String name, String money) {
        this.linsice=linsice;
        this.name=name;
        this.money=money;
    }

    public String getLinsice() {
        return linsice;
    }

    public void setLinsice(String linsice) {
        this.linsice = linsice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "ListBean{" +
                "linsice='" + linsice + '\'' +
                ", name='" + name + '\'' +
                ", money='" + money + '\'' +
                '}';
    }
}
