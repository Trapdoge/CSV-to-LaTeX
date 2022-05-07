//-----------------------------------------------------
// Assignment: 3
// Written by: (Alexander Smagorinski (ID: 40190986) && Zakaria El Manar (ID: 40190432)
//----------------------------------------------------- 

/**
 * This class implements a CSVFileInvalidException of type Exception
 * @author Alexander Smagorinski 
 * @author Zakaria El Manar
 */
public class CSVFileInvalidException extends Exception{

	/**
	* Default constructor with the CSVFileInvalidException object created
	*/
    public CSVFileInvalidException() {
        super();
    }

    /**
	* Parameterized constructor with the CSVFileInvalidException object created using values from the parameter
	* @param message a String value
	*/
    public CSVFileInvalidException(String message) {
        super(message);
    }
}
