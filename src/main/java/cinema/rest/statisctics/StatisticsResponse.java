package cinema.rest.statisctics;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
public class StatisticsResponse implements Serializable {
    private static final long serialVersionUID = 1L;
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
