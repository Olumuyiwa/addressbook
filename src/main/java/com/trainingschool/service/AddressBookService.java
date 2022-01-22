package com.trainingschool.service;

import com.trainingschool.dto.AddressBookRequest;
import com.trainingschool.dto.GeneralResponse;
import com.trainingschool.entity.AddressBookEntity;
import com.trainingschool.repository.AddressBookRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AddressBookService {

    Logger logger = LoggerFactory.getLogger(AddressBookService.class);
    @Autowired
    AddressBookRepository addressBookRepository;

    public GeneralResponse getAllRecordsFromDB() {
        Iterable<AddressBookEntity> iterable = addressBookRepository.findAll();
        List<AddressBookEntity> result =
                StreamSupport.stream(iterable.spliterator(), false)
                        .collect(Collectors.toList());
        GeneralResponse generalResponse = new GeneralResponse();
        List<AddressBookRequest> addressBookRequestList = (List<AddressBookRequest>) (Object) result;
        generalResponse.setAddressBookRequestList(addressBookRequestList);
        if (result.size() > 0) {
            generalResponse.setResponseCode("00");
            generalResponse.setResponseMessage("SUCCESS");
            generalResponse.setResponseDetails(result.size() + " item(s) fetched successfully");
        } else {
            generalResponse.setResponseCode("99");
            generalResponse.setResponseMessage("FAILURE");
            generalResponse.setResponseDetails("No item found");
        }
        return generalResponse;
    }

    public GeneralResponse getARecordUsingAnId(Long id) {
        GeneralResponse generalResponse = new GeneralResponse();
        AddressBookRequest addressBookRequest = new AddressBookRequest();

        Optional<AddressBookEntity> book = addressBookRepository.findById(id);
        if (book.isPresent()) {

            addressBookRequest.setEmailAddress(book.get().getEmailAddress());
            addressBookRequest.setFirstName(book.get().getFirstName());
            addressBookRequest.setLastName(book.get().getLastName());
            addressBookRequest.setPhoneNumber(book.get().getPhoneNumber());
            addressBookRequest.setId(book.get().getId());

            generalResponse.setAddressBookRequest(addressBookRequest);
            generalResponse.setResponseCode("00");
            generalResponse.setResponseMessage("SUCCESS");
            generalResponse.setResponseDetails("Item found");
        } else {
            generalResponse.setResponseCode("99");
            generalResponse.setResponseMessage("FAILURE");
            generalResponse.setResponseDetails("Item not added");
        }
        return generalResponse;
    }

    public GeneralResponse saveRecordInService
            (AddressBookRequest addressBookRequest) {
        ModelMapper modelMapper = new ModelMapper();
        GeneralResponse generalResponse = new GeneralResponse();
        GeneralResponse addressBookProcessingResponse = new GeneralResponse();
        AddressBookEntity addressBookEntity = modelMapper.map(addressBookRequest, AddressBookEntity.class);
        AddressBookEntity addressBookEntityOptional = null;
        try {
            addressBookEntityOptional = addressBookRepository.save(addressBookEntity);
            if (addressBookEntityOptional != null) {
                AddressBookRequest addressBookRequest1 = modelMapper.map(addressBookEntityOptional, AddressBookRequest.class);
                generalResponse.setAddressBookRequest(addressBookRequest1);
                generalResponse.setResponseCode("00");
                generalResponse.setResponseMessage("SUCCESS");
                generalResponse.setResponseDetails("Item added");
            } else {
                generalResponse.setResponseCode("99");
                generalResponse.setResponseMessage("FAILURE");
                generalResponse.setResponseDetails("Item not added");
            }
        } catch (Exception e) {
            generalResponse.setResponseCode("99");
            generalResponse.setResponseMessage("FAILURE");
            generalResponse.setResponseDetails("Item not added. An exception occurred " + e.toString());
        }


        return generalResponse;
    }

    public GeneralResponse deleteRecordById(Long addressId) {
        GeneralResponse generalResponse = new GeneralResponse();
        try {
            addressBookRepository.deleteById(addressId);
            generalResponse.setResponseCode("00");
            generalResponse.setResponseMessage("SUCCESS");
            generalResponse.setResponseDetails("Item deleted");
        } catch (Exception e) {
            generalResponse.setResponseCode("99");
            generalResponse.setResponseMessage("FAILURE");
            generalResponse.setResponseDetails("Item not deleted");
        }
        return generalResponse;
    }

    public GeneralResponse update(AddressBookRequest addressBookRequest) {
        ModelMapper modelMapper = new ModelMapper();
        GeneralResponse generalResponse = new GeneralResponse();
        GeneralResponse addressBookProcessingResponse = new GeneralResponse();
        AddressBookEntity addressBookEntity = modelMapper.map(addressBookRequest, AddressBookEntity.class);
        boolean idExist = addressBookRepository.existsById(addressBookEntity.getId());
        logger.info("does record exist in db " + idExist);
        logger.info("before update addressBookEntity = " + addressBookEntity);

        if (idExist == true) {
            try {
                logger.info("Valid and Ready for saving = " + idExist);
                addressBookEntity = addressBookRepository.save(addressBookEntity);
                logger.info("after saving addressBookEntity = " + addressBookEntity);
                AddressBookRequest dtoResponseAfterUpdate = modelMapper.map(addressBookEntity, AddressBookRequest.class);
                logger.info("after saving dtoResponseAfterUpdate = " + dtoResponseAfterUpdate);
                generalResponse.setAddressBookRequest(dtoResponseAfterUpdate);
                generalResponse.setResponseCode("00");
                generalResponse.setResponseMessage("SUCCESS");
                generalResponse.setResponseDetails("Item with id " + addressBookEntity.getId() + " updated");
            } catch (Exception e) {
                generalResponse.setResponseCode("99");
                generalResponse.setResponseMessage("FAILURE");
                generalResponse.setResponseDetails("An error occurred while updating the address with id " + addressBookEntity.getId()
                        + " Exception info " + e.toString());
            }
        } else {
            generalResponse.setResponseCode("99");
            generalResponse.setResponseMessage("FAILURE");
            generalResponse.setResponseDetails("Item with id " + idExist + " does not exist so update did not happen");

        }
        return generalResponse;
    }
}
