package com.spotbiz.spotbiz_backend_springboot.api;

import org.junit.jupiter.api.Test;

import com.spotbiz.spotbiz_backend_springboot.dto.SubscriptionBillingDto;
import com.spotbiz.spotbiz_backend_springboot.service.impl.SubscriptionBillingServiceImpl;
import com.spotbiz.spotbiz_backend_springboot.service.BusinessService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class SubscriptionBillingControllerTest {

    private SubscriptionBillingServiceImpl subscriptionBillingService;
    private BusinessService businessService;
    private SubscriptionBillingController controller;

    @BeforeEach
    void setup() {
        subscriptionBillingService = mock(SubscriptionBillingServiceImpl.class);
        businessService = mock(BusinessService.class);
        controller = new SubscriptionBillingController(subscriptionBillingService, businessService);
    }

    @Test
    void testInsertSubscriptionBilling() {
        // Arrange
        SubscriptionBillingDto dto = new SubscriptionBillingDto();
        dto.setSubscriptionId(1);
        when(subscriptionBillingService.insertSubscriptionBilling(dto)).thenReturn(dto);

        // Act
        ResponseEntity<?> response = controller.insertSubscriptionBilling(dto);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        verify(subscriptionBillingService, times(1)).insertSubscriptionBilling(dto);
    }

    @Test
    void testGetAllSubscriptionBillings() {
        // Arrange
        List<SubscriptionBillingDto> mockList = new ArrayList<>();
        mockList.add(new SubscriptionBillingDto());
        when(subscriptionBillingService.getAllSubscriptionBillings()).thenReturn(mockList);

        // Act
        ResponseEntity<?> response = controller.getAllSubscriptionBillings();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockList, response.getBody());
        verify(subscriptionBillingService, times(1)).getAllSubscriptionBillings();
    }

    @Test
    void testGetSubscriptionBillingById() {
        // Arrange
        SubscriptionBillingDto dto = new SubscriptionBillingDto();
        int id = 1;
        when(subscriptionBillingService.getSubscriptionBillingById(id)).thenReturn(dto);

        // Act
        ResponseEntity<?> response = controller.getSubscriptionBillingById(id);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dto, response.getBody());
        verify(subscriptionBillingService, times(1)).getSubscriptionBillingById(id);
    }

    @Test
    void testUpdateSubscriptionBilling() {
        // Arrange
        int id = 1;
        SubscriptionBillingDto dto = new SubscriptionBillingDto();
        when(subscriptionBillingService.updateSubscriptionBilling(id, dto)).thenReturn(dto);

        // Act
        ResponseEntity<SubscriptionBillingDto> response = controller.updateSubscriptionBilling(id, dto);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dto, response.getBody());
        verify(subscriptionBillingService, times(1)).updateSubscriptionBilling(id, dto);
    }

    @Test
    void testMarkDeleteSubscriptionBilling() {
        // Arrange
        int id = 1;
        SubscriptionBillingDto dto = new SubscriptionBillingDto();
        when(subscriptionBillingService.markDeleteSubscriptionBilling(id)).thenReturn(dto);

        // Act
        ResponseEntity<SubscriptionBillingDto> response = controller.markDeleteSubscriptionBilling(id);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dto, response.getBody());
        verify(subscriptionBillingService, times(1)).markDeleteSubscriptionBilling(id);
    }

    @Test
    void testDeleteSubscriptionBilling() {
        // Arrange
        int id = 1;
        when(subscriptionBillingService.deleteSubscriptionBilling(id)).thenReturn(id);

        // Act
        ResponseEntity<Integer> response = controller.deleteSubscriptionBilling(id);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(id, response.getBody());
        verify(subscriptionBillingService, times(1)).deleteSubscriptionBilling(id);
    }

}