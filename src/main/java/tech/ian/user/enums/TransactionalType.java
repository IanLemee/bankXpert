package tech.ian.user.enums;

public enum TransactionalType {
    TRANSFER("transfer"),
    SELL("sell"),
    PURCHASE("purchase");

    private final String type;

    TransactionalType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
