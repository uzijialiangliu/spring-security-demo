package com.uzi.service.impl;

import com.uzi.entity.Permission;
import com.uzi.entity.User;
import com.uzi.repository.PermissionRepository;
import com.uzi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PermissionRepository permissionRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);

        List<GrantedAuthority> authorities = new ArrayList<>();
        if (user != null) {
            List<Permission> permissions = permissionRepository.selectByUserId(user.getId());
            permissions.forEach(permission -> {
                if (permission != null && StringUtils.hasText(permission.getEname())) {
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getEname());
                    authorities.add(grantedAuthority);
                }
            });

            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        } else {
            throw new UsernameNotFoundException("username not found");
        }
    }
}
