//Spring Security no sabe nada de la clase User: trabaja con una interfaz propia llamada UserDetails. Necesitamos
//crear una clase que "envuelva" al User y le diga a Spring Security cómo interpretarlo.
package com.gamevault.security;

import com.gamevault.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

//La interfaz UserDetails exige que cualquier clase que la implemente tenga estos métodos: getUsername(), getPassword(),
//getAuthorities(), y los cuatro booleanos de estado de cuenta (isEnabled(), etc.).
public class UserPrincipal implements UserDetails {

    private final User user;

    public UserPrincipal(User user) {
        this.user = user;
    }

    public Long getId() {
        return user.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() { //¿la cuenta ha caducado (ej. cuentas temporales)?
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { //¿está bloqueada (ej. por múltiples intentos fallidos)?
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { //¿la contraseña necesita renovarse?
        return true;
    }

    @Override
    public boolean isEnabled() { //¿la cuenta está activa (ej. no fue desactivada por un admin)?
        return true;
    }
}