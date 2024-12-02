package com.spotbiz.spotbiz_backend_springboot.service.impl;

import com.spotbiz.spotbiz_backend_springboot.dto.CustomerDto;
import com.spotbiz.spotbiz_backend_springboot.entity.ChosenPics;
import com.spotbiz.spotbiz_backend_springboot.entity.CustomerPics;
import com.spotbiz.spotbiz_backend_springboot.entity.User;
import com.spotbiz.spotbiz_backend_springboot.repo.ChosenPicsRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.CustomerPicsRepo;
import com.spotbiz.spotbiz_backend_springboot.repo.UserRepo;
import com.spotbiz.spotbiz_backend_springboot.service.CustomerChosenPicService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerChosenPicServiceImplTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private CustomerPicsRepo customerPicsRepo;

    @Mock
    private ChosenPicsRepo chosenPicsRepo;

    @InjectMocks
    private CustomerChosenPicServiceImpl customerChosenPicService;

    private User user;
    private CustomerPics customerPics;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
        // Set up mock user and customerPics
        user = new User();
        user.setUserId(1);
        user.setName("testuser");
        user.setEmail("testemail@example.com");
        customerPics = new CustomerPics(1, "imageUrl");
    }

    @Test
    void saveChosenPic_whenUserAndPicExist_shouldReturnCustomerDto() {
        // Mock the behavior of the repositories
        when(userRepo.findById(anyInt())).thenReturn(Optional.of(user));
        when(customerPicsRepo.findById(anyInt())).thenReturn(Optional.of(customerPics));

        // Mock the save method to return the saved entity
        when(chosenPicsRepo.save(any(ChosenPics.class))).thenAnswer(invocation -> {
            ChosenPics chosenPics = invocation.getArgument(0);
            // Simulate the entity being saved and return it
            chosenPics.setCustomerPics(customerPics); // Simulate setting an ID after saving
            return chosenPics;
        });

        // Call the method to test
        CustomerDto result = customerChosenPicService.saveChosenPic(new CustomerDto(user.getUserId(), customerPics.getPicId()));

        // Verify the results
        assertNotNull(result);
        assertEquals(user.getUserId(), result.getUserId());
        assertEquals(customerPics.getPicId(), result.getPicId());

        // Verify interactions
        verify(userRepo).findById(anyInt());
        verify(customerPicsRepo).findById(anyInt());
        verify(chosenPicsRepo).save(any(ChosenPics.class));
    }

    @Test
    void saveChosenPic_whenUserNotFound_shouldThrowException() {
        // Mock the repository behavior to simulate user not found
        when(userRepo.findById(anyInt())).thenReturn(Optional.empty());

        // Call the method and assert that it throws an exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            customerChosenPicService.saveChosenPic(new CustomerDto(999, 1));
        });

        assertEquals("User not found with ID: 999", exception.getMessage());
    }

    @Test
    void saveChosenPic_whenPicNotFound_shouldThrowException() {
        // Mock the repository behavior to simulate picture not found
        when(userRepo.findById(anyInt())).thenReturn(Optional.of(user));
        when(customerPicsRepo.findById(anyInt())).thenReturn(Optional.empty());

        // Call the method and assert that it throws an exception
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            customerChosenPicService.saveChosenPic(new CustomerDto(user.getUserId(), 999));
        });

        assertEquals("Picture not found with ID: 999", exception.getMessage());
    }

    @Test
    void getChosenPic_whenUserExists_shouldReturnCustomerChosenPicResponseDto() {
        // Mock the repository behavior
        when(userRepo.findById(anyInt())).thenReturn(Optional.of(user));

        ChosenPics chosenPics = new ChosenPics(1, user, customerPics);
        when(chosenPicsRepo.findChosenPicsByUser(any(User.class))).thenReturn(Optional.of(chosenPics));

        // Call the method to test
        var result = customerChosenPicService.getChosenPic(user.getUserId());

        // Verify the results
        assertNotNull(result);
        assertEquals(user.getUserId(), result.getUserId());
        assertEquals(customerPics.getPicId(), result.getPicId());
        assertEquals(customerPics.getImageUrl(), result.getImageUrl());

        // Verify interactions
        verify(userRepo).findById(anyInt());
        verify(chosenPicsRepo).findChosenPicsByUser(any(User.class));
    }

    @Test
    void getChosenPic_whenUserNotFound_shouldReturnNull() {
        // Mock the repository behavior to simulate user not found
        when(userRepo.findById(anyInt())).thenReturn(Optional.empty());

        // Call the method and assert that it returns null
        var result = customerChosenPicService.getChosenPic(999);

        assertNull(result);

        // Verify interactions
        verify(userRepo).findById(anyInt());
    }
}
