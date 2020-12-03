package cn.zql.vgbill.Tools;

public class Values {
    private Integer id;
    private String CustomerName;        //顾客姓名
    private String CustomerPhone;         //顾客手机号
    private String CustomerAddress;       //顾客住址
    private String size;                 //购买产品型号
    private String BuyDate;            //购买日期
    private String Money;               //定金交付
    private String Maker;               //安装人员
    private String MakerDate;               //安装日期
    private String payment;              //付款方式
    private String service;              //售后服务

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        this.CustomerName = customerName;
    }

    public String getCustomerPhone() {
        return CustomerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.CustomerPhone = customerPhone;
    }

    public String getCustomerAddress() {
        return CustomerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.CustomerAddress = customerAddress;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getBuyDate() {
        return BuyDate;
    }

    public void setBuyDate(String buyDate) {
        this.BuyDate = buyDate;
    }

    public String getMoney() {
        return Money;
    }

    public void setMoney(String money) {
        this.Money = money;
    }

    public String getMaker() {
        return Maker;
    }

    public void setMaker(String maker) {
        this.Maker = maker;
    }

    public String getMakerDate() {
        return MakerDate;
    }

    public void setMakerDate(String makerDate) {
        this.MakerDate = makerDate;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    @Override
    public String toString() {
        return "Values{" +
                "id=" + id +
                ", CustomerName='" + CustomerName + '\'' +
                ", CustomerPhone='" + CustomerPhone + '\'' +
                ", CustomerAddress='" + CustomerAddress + '\'' +
                ", size='" + size + '\'' +
                ", BuyDate='" + BuyDate + '\'' +
                ", Money='" + Money + '\'' +
                ", Maker='" + Maker + '\'' +
                ", MakerDate='" + MakerDate + '\'' +
                ", payment='" + payment + '\'' +
                ", service='" + service + '\'' +
                '}';
    }
}
