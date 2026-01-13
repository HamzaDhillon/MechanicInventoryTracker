package info.hccis.main;

import info.hccis.student.util.CisUtility;
import info.hccis.student.util.UtilityRest;

public class Controller {

    public static void main(String[] args) {

        String input1 = CisUtility.getInputString("Enter the end point, leave blank for default");
        if(input1 == null || input1.isEmpty()){
            input1 = "https://aisenseapi.com/services/v1/qrcode_encode";
        }
        String parameter = CisUtility.getInputString("Enter the parameter input for the service");
        if(parameter == null || parameter.isEmpty()){
            parameter = "{ \"payload\": \"Hello, World!\" }";
        }

        String result = (String) UtilityRest.postUsingRest(input1, parameter );
        System.out.println(result);


    }
}