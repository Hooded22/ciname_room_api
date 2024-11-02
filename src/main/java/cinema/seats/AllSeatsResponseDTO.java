package cinema.seats;

import cinema.seats.dto.SeatDTO;

import java.util.List;

public class AllSeatsResponseDTO {
    int rows;
    int columns;
    List<SeatDTO> seats;

    public AllSeatsResponseDTO(int rows, int columns, List<SeatDTO> seats) {
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

    public List<SeatDTO> getSeats() {
        return seats;
    }

    public void setSeats(List<SeatDTO> seats) {
        this.seats = seats;
    }
}
