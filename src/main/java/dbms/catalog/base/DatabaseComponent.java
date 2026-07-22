package dbms.catalog.base;

import java.util.UUID;

public interface DatabaseComponent {
    UUID getId();
    String getName();
    String getOwner();
    String getQualifiedName();
}
