package cinema.domain.seats;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
@NoArgsConstructor
public class Seat {
    private Long id;
    private int row;
    private int column;
    private boolean isOccupied = false;
    private int price;

    public Seat(int row, int column) {
        this.id = generateRandomId();
        this.row = row;
        this.column = column;
        this.price = calculatePrice(row);
    }

    public Seat(int row, int column, boolean isOccupied) {
        this.id = generateRandomId();
        this.row = row;
        this.column = column;
        this.price = calculatePrice(row);
        this.isOccupied = isOccupied;
    }

    private Long generateRandomId() {
        return ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
    }

    private int calculatePrice(int row) {
        if (row <= 4) {
            return 10;
        } else {
            return 8;
        }
    }
}
