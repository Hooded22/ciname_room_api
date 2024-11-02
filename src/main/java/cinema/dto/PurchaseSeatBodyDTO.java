package cinema.dto;

public class PurchaseSeatBodyDTO {
    int row;
    int column;

    public PurchaseSeatBodyDTO(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public PurchaseSeatBodyDTO() {
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
}
