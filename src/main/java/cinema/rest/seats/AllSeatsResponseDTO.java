package cinema.rest.seats;

import java.util.List;

public class AllSeatsResponseDTO {
    int rows;
    int columns;
    List<SeatResponse> seats;

    public AllSeatsResponseDTO(int rows, int columns, List<SeatResponse> seats) {
        this.rows = rows;
        this.columns = columns;
        this.seats = seats;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public List<SeatResponse> getSeats() {
        return seats;
    }

    public void setSeats(List<SeatResponse> seats) {
        this.seats = seats;
    }
}
