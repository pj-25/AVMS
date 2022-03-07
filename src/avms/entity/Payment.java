package avms.entity;

public class Payment {
    private int paymentID;
    private int checkoutLogID;
    private float amount;

    public Payment(int paymentID, int checkoutLogID, float amount) {
        this.paymentID = paymentID;
        this.checkoutLogID = checkoutLogID;
        this.amount = amount;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public int getCheckoutLogID() {
        return checkoutLogID;
    }

    public void setCheckoutLogID(int checkoutLogID) {
        this.checkoutLogID = checkoutLogID;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
