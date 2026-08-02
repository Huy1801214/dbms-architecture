package store_api.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import store_api.model.email.EmailCampaign;
import store_api.repository.EmailCampaignRepository;

@Service
@RequiredArgsConstructor
public class EmailCampaignService {
    private final EmailCampaignRepository emailCampaignRepository;

    public List<EmailCampaign> getEmailCampaigns() {
        return emailCampaignRepository.findAll();
    }
}
