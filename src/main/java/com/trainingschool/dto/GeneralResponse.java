package com.trainingschool.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeneralResponse {
    private String responseCode;
    private String responseMessage;
    private String responseDetails;
    private List<AddressBookRequest> addressBookRequestList;
    private AddressBookRequest addressBookRequest;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseDetails() {
        return responseDetails;
    }

    public void setResponseDetails(String responseDetails) {
        this.responseDetails = responseDetails;
    }

    public List<AddressBookRequest> getAddressBookRequestList() {
        return addressBookRequestList;
    }

    public void setAddressBookRequestList(List<AddressBookRequest> addressBookRequestList) {
        this.addressBookRequestList = addressBookRequestList;
    }

    public AddressBookRequest getAddressBookRequest() {
        return addressBookRequest;
    }

    public void setAddressBookRequest(AddressBookRequest addressBookRequest) {
        this.addressBookRequest = addressBookRequest;
    }

    @Override
    public String toString() {
        return "GeneralResponse{" +
                "responseCode='" + responseCode + '\'' +
                ", responseMessage='" + responseMessage + '\'' +
                ", responseDetails='" + responseDetails + '\'' +
                ", addressBookRequestList=" + addressBookRequestList +
                ", addressBookRequest=" + addressBookRequest +
                '}';
    }
}
