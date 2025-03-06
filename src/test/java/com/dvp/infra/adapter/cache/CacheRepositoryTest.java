package com.dvp.infra.adapter.cache;

import com.dvp.TicketsApplication;
import com.dvp.domain.entities.Ticket;
import com.dvp.domain.enums.StatusEnum;
import com.dvp.infra.adapter.db.TicketsRepository;
import com.dvp.infra.adapter.db.entites.TicketsDto;
import com.dvp.infra.adapter.db.jpa.TicketsJpaRepository;
import com.dvp.infra.adapter.db.jpa.TicketsPaginationJpaRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TicketsApplication.class)
public class CacheRepositoryTest {

    @Autowired
    private CacheRepository cacheRepository;

    @MockBean
    private Jedis jedis;

    @Test
    public void getTestWhenSuccess()   {
        Mockito.when(jedis.get(anyString())).thenReturn("cache response");

        String response = cacheRepository.get("key");

        Assertions.assertNotNull(response);
        Assertions.assertEquals(response, "cache response");
    }

    @Test
    public void setTestWhenSuccess()  {
        cacheRepository.set("key", "value");
    }
}
