//-----------------------------------------------------
// Assignment: 3
// Written by: (Alexander Smagorinski (ID: 40190986) && Zakaria El Manar (ID: 40190432)
//----------------------------------------------------- 

/**
 * This class implements a CSVDataException of type Exception
 * @author Alexander Smagorinski 
 * @author Zakaria El Manar
 */
public class CSVDataMissing extends Exception{

	/**
	* Default constructor with the CSVDataMissing object created
	*/
    public CSVDataMissing() {
        super();
    }

    /**
	* Parameterized constructor with the CSVDataMissing object created using values from the parameter
	* @param message a String value
	*/
    public CSVDataMissing(String message) {
        super(message);
    }
}
