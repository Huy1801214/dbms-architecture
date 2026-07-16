package dbms.storage.page;

public interface IPageAllocator {

    Page allocate();

    void free(int pageId);

}