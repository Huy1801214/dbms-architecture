package store_api.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import store_api.model.email.EmailCampaign;
import store_api.service.EmailCampaignService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/emails")
@RequiredArgsConstructor
public class EmailController {

    private final EmailCampaignService emailCampaignService;

    @GetMapping
    public ResponseEntity<List<EmailCampaign>> getEmailCampaigns() {
        return ResponseEntity.ok(emailCampaignService.getEmailCampaigns());
    }
}
