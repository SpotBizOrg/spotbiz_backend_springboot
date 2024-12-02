package com.spotbiz.spotbiz_backend_springboot.api;

import com.spotbiz.spotbiz_backend_springboot.dto.CustomerChosenPicResponseDto;
import com.spotbiz.spotbiz_backend_springboot.dto.CustomerDto;
import com.spotbiz.spotbiz_backend_springboot.service.impl.CustomerChosenPicServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class CustomerChosenPicControllerTest {

    @InjectMocks
    private CustomerChosenPicController customerChosenPicController;

    @Mock
    private CustomerChosenPicServiceImpl customerChosenPicService;

    @Test
    void saveChosenPic() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setUserId(1);
        customerDto.setPicId(1);

        when(customerChosenPicService.saveChosenPic(customerDto)).thenReturn(customerDto);

        ResponseEntity<?> response = customerChosenPicController.saveChosenPic(customerDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customerDto, response.getBody());
    }

    @Test
    void saveChosenPic_Exception() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setUserId(1);
        customerDto.setPicId(10);

        when(customerChosenPicService.saveChosenPic(customerDto)).thenThrow(new RuntimeException("Error saving data."));

        ResponseEntity<?> response = customerChosenPicController.saveChosenPic(customerDto);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to save data: Error saving data.", response.getBody());
    }

    @Test
    void getChosenPic() {
        int userId = 1;
        CustomerChosenPicResponseDto responseDto = new CustomerChosenPicResponseDto();
        responseDto.setUserId(1);
        responseDto.setImageUrl("http://example.com/pic.jpg");

        when(customerChosenPicService.getChosenPic(userId)).thenReturn(responseDto);

        ResponseEntity<?> response = customerChosenPicController.getChosenPic(userId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void getChosenPic_Exception() {
        int userId = 1;

        when(customerChosenPicService.getChosenPic(userId)).thenThrow(new RuntimeException("Error fetching data."));

        ResponseEntity<?> response = customerChosenPicController.getChosenPic(userId);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Failed to get data: Error fetching data.", response.getBody());
    }

    @Test
    void getChosenOneWithAll_NotImplemented() {
        int userId = 1;

        ResponseEntity<?> response = customerChosenPicController.getChosenOneWithAll(userId);
        assertEquals(null, response);
    }
}
