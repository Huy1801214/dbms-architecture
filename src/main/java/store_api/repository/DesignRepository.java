package store_api.repository;

import java.util.List;

import store_api.model.design.StoreDesign;

public interface DesignRepository {
    List<StoreDesign> findAll();
}
