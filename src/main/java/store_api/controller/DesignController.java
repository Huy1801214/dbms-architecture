package store_api.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import store_api.model.design.StoreDesign;
import store_api.service.DesignService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/designs")
@RequiredArgsConstructor
public class DesignController {

    private final DesignService designService;

    @GetMapping
    public ResponseEntity<List<StoreDesign>> getDesigns() {
        return ResponseEntity.ok(designService.getDesigns());
    }
}
