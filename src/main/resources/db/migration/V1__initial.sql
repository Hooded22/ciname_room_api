CREATE TABLE Seat (
                      id INT PRIMARY KEY AUTO_INCREMENT,
                      `row` INT NOT NULL,
                      `column` INT NOT NULL,
                      isOccupied BOOLEAN DEFAULT FALSE,
                      price INT NOT NULL
);

CREATE TABLE Ticket (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        token VARCHAR(36),
                        seat_id INT,
                        FOREIGN KEY (seat_id) REFERENCES Seat(id)
);

CREATE TABLE ArchivedTicket (
                                id INT PRIMARY KEY AUTO_INCREMENT,
                                token VARCHAR(36),
                                seat_id INT,
                                FOREIGN KEY (seat_id) REFERENCES Seat(id)
);