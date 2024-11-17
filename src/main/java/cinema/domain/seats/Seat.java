package cinema.domain.seats;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Entity
@Table(name = "Seat")
@Getter
@Setter
@NoArgsConstructor
public class Seat implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "`row`")
    private int row;

    @Column(name = "`column`")
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
