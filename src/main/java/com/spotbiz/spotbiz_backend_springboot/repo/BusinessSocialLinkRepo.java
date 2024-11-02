package com.spotbiz.spotbiz_backend_springboot.repo;

import com.spotbiz.spotbiz_backend_springboot.entity.BusinessSocialLink;
import com.spotbiz.spotbiz_backend_springboot.entity.SocialLinkType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BusinessSocialLinkRepo extends JpaRepository<BusinessSocialLink, Long> {
    Optional<BusinessSocialLink> findByBusinessIdAndType(Integer businessId, SocialLinkType type);
    List<BusinessSocialLink> findByBusinessId(Integer businessId);
}
