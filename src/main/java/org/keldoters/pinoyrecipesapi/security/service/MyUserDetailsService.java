package org.keldoters.pinoyrecipesapi.security.service;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.keldoters.pinoyrecipesapi.security.model.Account;
import org.keldoters.pinoyrecipesapi.security.model.Role;
import org.keldoters.pinoyrecipesapi.security.repository.AccountRepository;
import org.keldoters.pinoyrecipesapi.security.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder getEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public MyUserDetailsService(AccountRepository accountRepository, PasswordEncoder getEncoder, RoleRepository roleRepository) {
        this.accountRepository = accountRepository;
        this.getEncoder = getEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Account> account = accountRepository.findAccountByEmail(email);
        return account.map(user -> {
            //map account roles to authorities
            Collection<SimpleGrantedAuthority> authorities = user.getRoles()
                    .stream().map(roles -> new SimpleGrantedAuthority(roles.getName()))
                    .collect(Collectors.toList());
            return new User(user.getEmail(), user.getPassword(), authorities);
        }).orElseThrow(() -> new UsernameNotFoundException("Account not found"));

    }


    public boolean register(Account account, String role) {
        Optional<Account> newAccount = accountRepository.findAccountByEmail(account.getEmail());
        if (newAccount.isEmpty()) {
            Role newRole = roleRepository.findRoleByName(role)
                    .orElseGet(() -> roleRepository.save(new Role(role)));
            account.addRole(newRole);

            account.setPassword(getEncoder.encode(account.getPassword()));
            accountRepository.save(account);
            return true;
        }
        return false;

    }
}


