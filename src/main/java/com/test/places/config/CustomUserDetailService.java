package com.test.places.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.test.places.model.DataAccessObjectUsers;
import com.test.places.model.DataTransferObjectUsers;
import com.test.places.repository.UserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder noop;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<SimpleGrantedAuthority> roles = null;

		DataAccessObjectUsers daoUsers = userRepository.findByUsername(username);
		if (daoUsers != null) {
			roles = Arrays.asList(new SimpleGrantedAuthority(daoUsers.getRole()));
			return new User(daoUsers.getUsername(), daoUsers.getPassword(), roles);
		}
		throw new UsernameNotFoundException("user with this username is not found" + username);

	}

	public DataAccessObjectUsers saveUser(DataTransferObjectUsers users) {
		DataAccessObjectUsers newUser = new DataAccessObjectUsers();
		String username = users.getUsername();
		newUser.setUsername(username);
		newUser.setPassword(noop.encode(users.getPassword()));
		newUser.setRole(users.getRole());
		return userRepository.save(newUser);
	}

}
