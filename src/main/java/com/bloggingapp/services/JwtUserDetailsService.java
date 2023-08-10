package com.bloggingapp.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bloggingapp.entity.UserMaster;
import com.bloggingapp.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserMaster> user = null;
		List<GrantedAuthority> grantedAuthority = null;

		try {
			user = repository.findByName(username);
			grantedAuthority = Arrays.stream(user.get().getRoles().split(",")).map(SimpleGrantedAuthority::new)
					.collect(Collectors.toList());
			return new User(user.get().getName(), user.get().getPassword(), grantedAuthority);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// return user.map(JwtUserService::new)
		// .orElseThrow(() -> new UsernameNotFoundException("user not found" +
		// username));
		throw new UsernameNotFoundException("User not found with username: " + username);
	}

}
