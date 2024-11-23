package cinema.rest.seats;

import cinema.domain.seats.Seat;
import cinema.domain.seats.SeatsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SeatsController.class)
class SeatsControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    SeatsService seatsService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getSeats_shouldReturnSeats() throws Exception {
        Seat seat = new Seat();
        seat.setRow(1);
        seat.setColumn(1);
        Seat seat2 = new Seat();
        seat2.setRow(2);
        seat2.setColumn(2);
        List<Seat> seats = List.of(seat, seat2);

        Page<Seat> seatsPage = new PageImpl<>(seats);

        Mockito.when(seatsService.getAllSeats(Mockito.any(Pageable.class))).thenReturn(seatsPage);

        mvc.perform(get("/seats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.seats[0].row").value(seat.getRow()))
                .andExpect(jsonPath("$.seats[0].column").value(seat.getColumn()))
                .andExpect(jsonPath("$.seats[1].row").value(seat2.getRow()))
                .andExpect(jsonPath("$.seats[1].column").value(seat2.getColumn()))
                .andDo(print());
    }
}