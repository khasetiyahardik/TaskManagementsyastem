package com.example.TMS.serviceImpl;

import com.example.TMS.entity.AdminEntity;
import com.example.TMS.exception.AdminNotFoundException;
import com.example.TMS.repository.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomAdminDetailService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(CustomAdminDetailService.class);
    @Autowired
    AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String adminName) throws UsernameNotFoundException {
        logger.info("CustomAdminDetailService : loadUserByUsername");
        AdminEntity admin = null;
        AdminEntity existingData = adminRepository.findByAdminName(adminName);
        if (existingData != null) {
            admin = existingData;
        }
        if (existingData == null) {
            System.out.println("User not found");
        }
        if (admin.getAdminName() == null) {
            throw new AdminNotFoundException("Admin not found");
        }
        UserDetails user = User.withUsername(admin.getAdminName()).password(admin.getAdminPassword()).authorities("Admin").build();
        return user;
    }
}