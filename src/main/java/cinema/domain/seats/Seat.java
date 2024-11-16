package cinema.domain.seats;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Seat")
@Getter
@Setter
@NoArgsConstructor
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int row;
    private int column;
    private boolean isOccupied = false;
    private int price;

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        this.price = calculatePrice(row);
    }

    public Seat(int row, int column, boolean isOccupied) {
        this.row = row;
        this.column = column;
        this.price = calculatePrice(row);
        this.isOccupied = isOccupied;
    }

    private int calculatePrice(int row) {
        return row <= 4 ? 10 : 8;
    }
}
