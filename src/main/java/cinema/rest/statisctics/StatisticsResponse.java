package cinema.rest.statisctics;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class StatisticsResponse {
    private BigDecimal income;
    private int available;
    private int purchased;

    public StatisticsResponse(BigDecimal income, int available, int purchased) {
        this.income = income;
        this.available = available;
        this.purchased = purchased;
    }

    public StatisticsResponse() {
    }

}
