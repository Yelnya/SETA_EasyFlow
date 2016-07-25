package util;

public class ValidateInput {

	// validate max number of vehicles 
	public static boolean validateMaxVehicleNumber( String maxNumber )
	{
		// Validating user information using regular expressions
		return maxNumber.matches( "\\d{2}" ); 
	} // end method validateMaxVehicleNumber
	
}
