package cinema.seats.dto;

import cinema.seats.Seat;

import java.util.List;
import java.util.stream.Collectors;

public class SeatDTO {
    int row;
    int column;
    int price;

    public SeatDTO() {
    }

    public SeatDTO(int row, int column, int price) {
        this.row = row;
        this.column = column;
        this.price = price;
    }

    public static List<SeatDTO> convertSeatsList(List<Seat> seats) {
        return seats.stream()
                .map(seat -> new SeatDTO(seat.getRow(), seat.getColumn(), seat.getPrice()))
                .collect(Collectors.toList());
    }

    public static SeatDTO convertSeat(Seat seat) {
        return new SeatDTO(seat.getRow(), seat.getColumn(), seat.getPrice());
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
