package store_api.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import store_api.model.design.StoreDesign;
import store_api.repository.DesignRepository;

@Service
@RequiredArgsConstructor
public class DesignService {
    private final DesignRepository designRepository;

    public List<StoreDesign> getDesigns() {
        return designRepository.findAll();
    }
}
