//-----------------------------------------------------
// Assignment: 3
// Written by: (Alexander Smagorinski (ID: 40190986) && Zakaria El Manar (ID: 40190432)
//----------------------------------------------------- 

/**
 * This class implements a InvalidException of type Exception
 * @author Alexander Smagorinski 
 * @author Zakaria El Manar
 */
public class InvalidException extends Exception{

	/**
	* Default constructor with the InvalidException object created
	*/
    public InvalidException() {
        super("Error: Input row cannot be parsed due to missing information");
    }

    /**
	* Parameterized constructor with the InvalidException object created using values from the parameter
	* @param message a String value
	*/
    public InvalidException(String message) {
        super(message);
    }
}