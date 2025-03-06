package com.dvp.infra.api.router.controller;

import com.dvp.TicketsApplication;
import com.dvp.app.TicketsService;
import com.dvp.domain.enums.StatusEnum;
import com.dvp.domain.port.cache.CachePortRepository;
import com.dvp.infra.adapter.db.TicketsRepository;
import com.dvp.infra.api.router.controller.dto.response.ticket.TicketDataDto;
import com.dvp.infra.api.router.controller.dto.response.ticket.TicketDto;
import com.dvp.infra.api.router.controller.dto.response.ticket.TicketPaginationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import redis.clients.jedis.Jedis;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations="classpath:application.properties")
@ContextConfiguration(classes = { TicketsApplication.class })
@WebAppConfiguration
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class TicketsControllerIntegrationsTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private TicketsService ticketsService;

    @MockBean
    private TicketsRepository ticketsRepository;

    @MockBean
    private CachePortRepository cachePortRepository;

    private MockMvc mockMvc;

    private String body;

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        this.body = "{ \n" +
                "    \"user_id\": 1,\n" +
                "    \"description\" : \"ticket para instalacion de windows 2\" ,\n" +
                "    \"status\" : \"ABIERTO\"\n" +
                "}";
    }

    @Test
    public void createTicketThenVerifyResponse() throws Exception {

        Mockito.when(ticketsService.createTicket(any())).thenReturn(getTicketDto());


        this.mockMvc.perform(post("/ticket" ).content(body).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is(201)).andExpect(content()
                        .contentType("application/json"))
                .andExpect(jsonPath("$.code").value("200"))
                .andReturn();

    }

    @Test
    public void getTikectByIdThenVerifyResponse() throws Exception {

        Mockito.when(ticketsService.getTicketById(any())).thenReturn(getTicketDto());


         this.mockMvc.perform(get("/ticket/{id}", "1"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andReturn();

    }

    @Test
    public void getTikectsThenVerifyResponse() throws Exception {

        Mockito.when(ticketsService.getTickets(any(), any())).thenReturn(getTicketPaginationDto());

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("page", Collections.singletonList("1"));
        params.put("size", Collections.singletonList("1"));

        this.mockMvc.perform(get("/ticket" ).queryParams(params))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andReturn();

    }

    @Test
    public void deleteTicketThenVerifyResponse() throws Exception {

        this.mockMvc.perform(delete("/ticket/{id}", "1" ).contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andReturn();

    }

    @Test
    public void updateTicketThenVerifyResponse() throws Exception {
        Mockito.when(ticketsService.updateTicket(any())).thenReturn(getTicketDto());

        this.mockMvc.perform(put("/ticket/{id}", "1" ).content(body).contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andReturn();

    }

    @Test
    public void getByFilterThenVerifyResponse() throws Exception {

        Mockito.when(ticketsService.getByFilter(1L, StatusEnum.ABIERTO)).thenReturn(getTicketDto());

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.put("user_id", Collections.singletonList("1"));
        params.put("status", Collections.singletonList("ABIERTO"));

        this.mockMvc.perform(get("/ticket/filter" ).queryParams(params))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andReturn();

    }


    private TicketDto getTicketDto() {
        TicketDto ticketDto = TicketDto.builder()
                .data(Collections.singletonList(TicketDataDto.builder()
                        .status(StatusEnum.ABIERTO)
                        .description("description")
                        .ticketId(1L)
                        .updateAt(java.sql.Timestamp.valueOf(LocalDateTime.now()))
                        .createAt(java.sql.Timestamp.valueOf(LocalDateTime.now()))
                        .ticketId(1L).build())).build();

        ticketDto.setCode("200");
        ticketDto.setMessage("ok");
        return  ticketDto;
    }

    TicketPaginationDto getTicketPaginationDto(){
        TicketPaginationDto ticketPaginationDto = TicketPaginationDto.builder()
                .page(1)
                .totalCount(1L)
                .data(Collections.singletonList(TicketDataDto.builder()
                        .status(StatusEnum.ABIERTO)
                        .description("description")
                        .ticketId(1L)
                        .updateAt(java.sql.Timestamp.valueOf(LocalDateTime.now()))
                        .createAt(java.sql.Timestamp.valueOf(LocalDateTime.now()))
                        .ticketId(1L).build())).build();

        ticketPaginationDto.setCode("200");
        ticketPaginationDto.setMessage("ok");

        return ticketPaginationDto;
    }
}
