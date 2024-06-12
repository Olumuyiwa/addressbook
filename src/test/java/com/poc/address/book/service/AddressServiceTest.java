package com.poc.address.book.service;

import com.poc.address.book.entity.Address;
import com.poc.address.book.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressService addressService;

    private Address address;

    @BeforeEach
    public void setUp() {
        address = new Address();
        address.setId(1L);
        address.setName("Test Name");
        address.setPhonenumber("1234567890");
    }

    @Test
    public void testGetAllAddresses() {
        // Arrange
        when(addressRepository.findAll()).thenReturn(Arrays.asList(address));

        // Act
        List<Address> addresses = addressService.getAllAddresses();

        // Assert
        assertThat(addresses).isNotEmpty();
        assertThat(addresses.get(0).getName()).isEqualTo("Test Name");
        verify(addressRepository, times(1)).findAll();
    }

    @Test
    public void testGetAddressById() {
        // Arrange
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));

        // Act
        Optional<Address> foundAddress = addressService.getAddressById(1L);

        // Assert
        assertThat(foundAddress).isPresent();
        assertThat(foundAddress.get().getName()).isEqualTo("Test Name");
        verify(addressRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetAddressById_NotFound() {
        // Arrange
        when(addressRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Optional<Address> foundAddress = addressService.getAddressById(1L);

        // Assert
        assertThat(foundAddress).isNotPresent();
        verify(addressRepository, times(1)).findById(1L);
    }

    @Test
    public void testSaveAddress() {
        // Arrange
        when(addressRepository.save(any(Address.class))).thenReturn(address);

        // Act
        Address savedAddress = addressService.saveAddress(address);

        // Assert
        assertThat(savedAddress).isNotNull();
        assertThat(savedAddress.getName()).isEqualTo("Test Name");
        verify(addressRepository, times(1)).save(address);
    }

    @Test
    public void testDeleteAddress() {
        // Arrange
        doNothing().when(addressRepository).deleteById(1L);

        // Act
        addressService.deleteAddress(1L);

        // Assert
        verify(addressRepository, times(1)).deleteById(1L);
    }
}
