package cinema.domain.seats;


import java.util.concurrent.ThreadLocalRandom;

public class Seat {
    private Long id;
    private int row;
    private int column;
    private boolean isOccupied = false;
    private int price;


    public Seat() {
    }

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

    public Long getId() {
        return id;
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

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    private int calculatePrice(int row) {
        if (row <= 4) {
            return 10;
        } else {
            return 8;
        }
    }
}
