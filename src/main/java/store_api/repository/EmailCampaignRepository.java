package store_api.repository;

import java.util.List;

import store_api.model.email.EmailCampaign;

public interface EmailCampaignRepository {
    List<EmailCampaign> findAll();
}
