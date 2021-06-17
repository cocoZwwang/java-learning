package pers.cocoadel.java.learning.local.retire;

/**
 * language=语言
 * computeButton=计算
 * saving=预存
 * contrib=每年存金
 * income=退休收入
 * currentAge=现龄
 * retireAge=退休年龄
 * deathAge=预期寿命
 * inflationPercent=通货膨胀
 * investPercent=投资报酬
 * retire=年龄:{0,number} 余额:{1,number,currency}
 */
public class RetireInfo {
    private double saving;
    private double contrib;
    private double income;
    private int currentAge;
    private int retireAge;
    private int deathAge;
    private double inflationPercent;
    private double investPercent;
    private int age;
    private double balance;


    /**
     * 从今天开始存
     */
    public double getBalance(int year){
        if(year < currentAge){
            return 0;
        }
        if(year == currentAge){
            age = year;
            balance = saving;
            return balance;
        } else if (year == age) {
            return balance;
        }

        if(year != age + 1){
            return getBalance(year - 1);
        }

        age = year;
        if(age < retireAge){
            balance += contrib;
        }else{
            balance -= income;
        }
        balance = balance * (1 + (investPercent - inflationPercent));
        return balance;
    }

    public double getSaving() {
        return saving;
    }

    public void setSaving(double saving) {
        this.saving = saving;
    }

    public double getContrib() {
        return contrib;
    }

    public void setContrib(double contrib) {
        this.contrib = contrib;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public int getCurrentAge() {
        return currentAge;
    }

    public void setCurrentAge(int currentAge) {
        this.currentAge = currentAge;
    }

    public int getRetireAge() {
        return retireAge;
    }

    public void setRetireAge(int retireAge) {
        this.retireAge = retireAge;
    }

    public int getDeathAge() {
        return deathAge;
    }

    public void setDeathAge(int deathAge) {
        this.deathAge = deathAge;
    }

    public double getInflationPercent() {
        return inflationPercent;
    }

    public void setInflationPercent(double inflationPercent) {
        this.inflationPercent = inflationPercent;
    }

    public double getInvestPercent() {
        return investPercent;
    }

    public void setInvestPercent(double investPercent) {
        this.investPercent = investPercent;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "RetireInfo{" +
                "saving=" + saving +
                ", contrib=" + contrib +
                ", income=" + income +
                ", currentAge=" + currentAge +
                ", retireAge=" + retireAge +
                ", deathAge=" + deathAge +
                ", inflationPercent=" + inflationPercent +
                ", investPercent=" + investPercent +
                ", age=" + age +
                ", balance=" + balance +
                '}';
    }
}
