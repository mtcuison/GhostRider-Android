package org.rmj.guanzongroup.ghostrider.samsungknox.Etc;

public class KnoxErrorCode {

    public static String getMessage(String fsError, String message){
        switch(fsError){
            case "400":
                return "The client has issued an invalid request. ";

            case "401":
                return "Authorization for the API is required, but the request has not been authenticated.";

            case "403":
                return "The request has been authenticated but does not have the appropriate permissions, or the requested resource cannot be found.";

            case "404":
                return "Requested path does not exist.";

            case "406":
                return "The client has requested a MIME type via the Accept header for a value not supported by the server.";

            case "422":
                return "The client has made a valid request, but the server cannot process it. This is often returned for APIs when certain limits have been exceeded.";

            case "429":
                return "The client has exceeded the number of requests allowed for a given time window.";

            case "500":
                return "An unexpected error on the SmartThings servers has occurred. ";

            case "501":
                return "The client request was valid and understood by the server, but the requested feature has yet to be implemented.";

            //Message and description
            case "4010000":
                return "The API key is not valid or the restriction data does not match.";

            case "4002102":
                return "IP address or http referer format are incorrect.";

            case "4042101":
                return "The IP address or http referer are empty.";

            case "4040100":
                return "There is no user linked to the API key.";

            case "4040900":
                return "There is no tenant linked to the API key.";

            case "4000000":
                return "Argument is invalid. Please enter valid Device IMEI.";

            case "4001809":
                return "There are no additional licenses that can be activated.";

            case "4040300":
                //message = "Error Code 4040300 (Device Not Found): A device was not found with the specified condition.";
                return "Specified Device IMEI was not found. Please contact MIS Support for uploading Device IMEI.";

            case "4000310":
                //message = "Error Code 4040300 (Device State Invalid): The operation is not permitted, in the current state.";
                return "Device IMEI was already activated. Please enroll your the device.";

            case "4000316":
                return "The bulk operation limit has been exceeded.";

            case "4040400":
                return "Internal profile not found.";

            case "4001805":
                return "License name already exists.";

            case "5001804":
                return "Error communication with the license server.";

            case "4001813":
                return "This license key is already registered to another region (EU/US).";

            case "4001802":
                return "Max trial license count reached.";

            case "4041800":
                return "License not found.";

            case "4001806":
                return "This device is already registered with a license.";

            case "4001812":
                return "License already deleted.";

            default:
                return message;
        }
    }
}
