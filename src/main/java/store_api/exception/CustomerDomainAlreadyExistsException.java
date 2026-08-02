package store_api.exception;

public class CustomerDomainAlreadyExistsException extends RuntimeException {
    public CustomerDomainAlreadyExistsException(String domain) {
        super("Customer domain already exists: " + domain);
    }
}
