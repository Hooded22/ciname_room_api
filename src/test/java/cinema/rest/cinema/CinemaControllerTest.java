package cinema.rest.cinema;

import cinema.domain.auth.PasswordValidationService;
import cinema.domain.seats.Seat;
import cinema.domain.seats.SeatsService;
import cinema.domain.statistics.StatisticsService;
import cinema.domain.tickets.Ticket;
import cinema.domain.tickets.TicketsService;
import cinema.rest.seats.PurchaseSeatBodyDTO;
import cinema.rest.seats.SeatResponse;
import cinema.rest.statisctics.StatisticsResponse;
import cinema.rest.tickets.ReturnTicketRequest;
import cinema.rest.tickets.ReturnTicketResponse;
import cinema.rest.tickets.TicketResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@WebMvcTest(controllers = CinemaController.class)
class CinemaControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    SeatsService seatsService;

    @MockBean
    TicketsService ticketsService;

    @MockBean
    StatisticsService statisticsService;

    @MockBean
    PasswordValidationService passwordValidationService;

    @Test
    void purchaseSeat_shouldReturnTicket() throws Exception {
        SeatResponse seat = new SeatResponse();
        seat.setColumn(1);
        seat.setRow(1);
        seat.setPrice(123);

        TicketResponse ticketResponse = new TicketResponse();
        ticketResponse.setTicketSeat(seat);
        PurchaseSeatBodyDTO purchaseSeatBodyDTO = new PurchaseSeatBodyDTO(1,1);

        Mockito.when(ticketsService.purchaseTicket(purchaseSeatBodyDTO.getRow(), purchaseSeatBodyDTO.getColumn())).thenReturn(ticketResponse);

        mvc.perform(
                post("/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(purchaseSeatBodyDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticketSeat.row").value(1))
                .andExpect(jsonPath("$.ticketSeat.column").value(1))
                .andDo(print());

    }

    @Test
    void returnTicket_shouldReturnSuccess() throws Exception {
        ReturnTicketRequest returnTicketBodyDTO = new ReturnTicketRequest();
        ReturnTicketResponse returnTicketResponseDTO = new ReturnTicketResponse(new SeatResponse(1, 1, 123));

        Mockito.when(ticketsService.returnTicket(returnTicketBodyDTO.getToken())).thenReturn(returnTicketResponseDTO);

        mvc.perform(post("/return")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(returnTicketBodyDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticketSeat.row").value(1))
                .andExpect(jsonPath("$.ticketSeat.column").value(1))
                .andDo(print());
    }

    @Test
    void getAllStats_shouldReturnStatistics() throws Exception {
        String password = "valid-password";
        StatisticsResponse statisticsResponseDTO = new StatisticsResponse(BigDecimal.valueOf(100), 10, 90);

        Mockito.doNothing().when(passwordValidationService).validatePassword(password);
        Mockito.when(statisticsService.getStatistics()).thenReturn(statisticsResponseDTO);

        mvc.perform(get("/stats")
                        .param("password", password))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.income").value(100))
                .andExpect(jsonPath("$.available").value(10))
                .andExpect(jsonPath("$.purchased").value(90))
                .andDo(print());
    }
}