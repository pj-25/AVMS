package avms.databaseHandler;

public class InvalidDataException extends Exception{
    public InvalidDataException(String errMsg){
        super(errMsg);
    }
}
