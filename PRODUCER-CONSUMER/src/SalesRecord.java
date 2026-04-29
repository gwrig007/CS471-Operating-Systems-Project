import java.util.Locale;

public final class SalesRecord {
    public static final int FIXED_YEAR = 16;
    public static final SalesRecord POISON_PILL = new SalesRecord(0, 0, 0, 0, 0.0f);

    private final int day;
    private final int month;
    private final int storeId;
    private final int registerNumber;
    private final float saleAmount;

    public SalesRecord(int day, int month, int storeId, int registerNumber, float saleAmount) {
        this.day = day;
        this.month = month;
        this.storeId = storeId;
        this.registerNumber = registerNumber;
        this.saleAmount = saleAmount;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getStoreId() {
        return storeId;
    }

    public int getRegisterNumber() {
        return registerNumber;
    }

    public float getSaleAmount() {
        return saleAmount;
    }

    public boolean isPoisonPill() {
        return this == POISON_PILL;
    }

    public String dateString() {
        return String.format(Locale.US, "%02d/%02d/%02d", day, month, FIXED_YEAR);
    }
}
