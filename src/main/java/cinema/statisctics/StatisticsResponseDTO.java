package cinema.statisctics;

import java.math.BigDecimal;

public class StatisticsResponseDTO {
    private BigDecimal income;
    private int available;
    private int purchased;

    public StatisticsResponseDTO(BigDecimal income, int available, int purchased) {
        this.income = income;
        this.available = available;
        this.purchased = purchased;
    }

    public StatisticsResponseDTO() {
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int getPurchased() {
        return purchased;
    }

    public void setPurchased(int purchased) {
        this.purchased = purchased;
    }
}
