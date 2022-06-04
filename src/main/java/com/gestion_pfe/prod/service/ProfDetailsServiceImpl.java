package com.gestion_pfe.prod.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestion_pfe.prod.model.Prof;
import com.gestion_pfe.prod.repositoy.ProfRepository;

import lombok.AllArgsConstructor;

import static java.util.Collections.singletonList;

@Service
@AllArgsConstructor
public class ProfDetailsServiceImpl implements UserDetailsService{

	private final ProfRepository profRepository;
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		Optional<Prof> profOpt = profRepository.findByEmail(email);
		Prof prof = profOpt
						.orElseThrow(() -> new UsernameNotFoundException("prof not found : "+email) );
		
		return new org.springframework.security.core.userdetails.User
                (
                		prof.getEmail(),
                		prof.getPassword(),
                		prof.isEnabled(),
                		true, //accountNonExpired
                		true, //credentialsNonExpired
                		true, //accountNonLocked
                		getAuthorities("PROF")
                		);
	}
	
	private Collection<? extends GrantedAuthority> getAuthorities(String role){
		return singletonList(new SimpleGrantedAuthority(role));
	}

}





