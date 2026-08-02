package store_api.exception;

public class CustomerUserNotFoundException extends RuntimeException {
    public CustomerUserNotFoundException(String customerId, String userId) {
        super("Customer user not found with customerId: " + customerId + " and userId: " + userId);
    }
}
