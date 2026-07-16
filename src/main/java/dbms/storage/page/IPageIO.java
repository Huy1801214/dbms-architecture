package dbms.storage.page;

public interface IPageIO {

    Page read(int pageId);

    void write(Page page);

}
