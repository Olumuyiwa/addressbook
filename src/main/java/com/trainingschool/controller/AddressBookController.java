package com.trainingschool.controller;

import com.trainingschool.dto.AddressBookRequest;
import com.trainingschool.dto.GeneralResponse;
import com.trainingschool.service.AddressBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AddressBookController {
    @Autowired
    AddressBookService addressBookService;

    Logger logger = LoggerFactory.getLogger(AddressBookController.class);

    @GetMapping(path = "/welcome")
    public String welcome() {
        logger.info("welcome logger ");
        System.out.println("welcome2 println ");
        return "Welcome to the address book webservice.";
    }

    @GetMapping(path = "/get-addresses")
    public GeneralResponse getAllRecords() {
        GeneralResponse generalResponse = addressBookService.getAllRecordsFromDB();
        logger.info("addressBookList count logger generalResponse = " + generalResponse);
        System.out.println("addressBookList count  = " + generalResponse.getAddressBookRequestList().size());
        return generalResponse;
    }

    @DeleteMapping(path = "/delete-address/{addressId}")
    public GeneralResponse deleteEmployee(@PathVariable("addressId") Long addressId) {
        logger.info("addressId = " + addressId);
        GeneralResponse generalResponse = addressBookService.deleteRecordById(addressId);
        return generalResponse;
    }

    @GetMapping(path = "/fetch-address/{addressId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public GeneralResponse getAllRecords(@PathVariable("addressId") Long addressId) {
        logger.info("addressId = " + addressId);
        System.out.println("addressId = " + addressId);
        GeneralResponse generalResponse = null;
        try {
            generalResponse = addressBookService.getARecordUsingAnId(addressId);
            logger.info("Executing account validation log addressBook = " + generalResponse);
            System.out.println("Inside the controller accountEnquiryRequest = " + generalResponse);
        } catch (Exception e) {
            System.out.println("Error in  getting AddressBookRequest = " + e.toString());
        }
        return generalResponse;
    }

    @PutMapping(path = "/modify-address")
    public ResponseEntity<GeneralResponse> updateAddressBook
            (@RequestBody AddressBookRequest addressBookRequest) {
        logger.info("Executing modify addressBookRequest = " + addressBookRequest);
        GeneralResponse updateStatus = addressBookService.update(addressBookRequest);
        return new ResponseEntity<GeneralResponse>(updateStatus, HttpStatus.OK);
    }

    @PostMapping(path = "/update-address")
    public ResponseEntity<GeneralResponse> updateAddressBook2Db
            (@RequestBody AddressBookRequest addressBookRequest) {
        logger.info("Executing update " + addressBookRequest);
        GeneralResponse response = addressBookService.update(addressBookRequest);
        return new ResponseEntity<GeneralResponse>(response, HttpStatus.OK);
    }

    @PostMapping(path = "/insert-address")
    public ResponseEntity<GeneralResponse> insertAddressBook2Db
            (@RequestBody AddressBookRequest addressBookRequest) {
        logger.info("Executing insert " + addressBookRequest);
        GeneralResponse response = addressBookService.saveRecordInService(addressBookRequest);

        return new ResponseEntity<GeneralResponse>(response, HttpStatus.OK);
    }

/*
insert_record
read_all_records
read_one_record
update_a_record
delete_a_record
*
* */
}
