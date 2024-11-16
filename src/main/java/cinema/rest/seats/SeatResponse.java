package cinema.rest.seats;

import lombok.Getter;

@Getter
public class SeatResponse {
    private int row;
    private int column;
    private int price;

    public SeatResponse() {
    }

    public SeatResponse(int row, int column, int price) {
        this.row = row;
        this.column = column;
        this.price = price;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
