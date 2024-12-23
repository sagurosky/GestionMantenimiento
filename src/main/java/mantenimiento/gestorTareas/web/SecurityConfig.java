package mantenimiento.gestorTareas.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //este metodo nos va a servir para agregar mas usuarios
    //a esto se le llama autenticacion
    @Autowired
    private UserDetailsService userDetailsService;//es una instancia de usuarioService

    @Bean//al declararlo como bean se agrega al contenedor de spring
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder build) throws Exception {
        //simplemente con haber definido el metodo con autowired ya tenemos disponible
        //el objeto AuthenticationManagerBuilder
        build.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    }

    //para seleccionar lo que voy a restringir
    //a esto se le llama autorizacion
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/editar/**","/modificar/**","/eliminar","/crearUsuario/**","/gestionUsuarios/**","/gestionar/")
//                .hasRole("ADMIN")//no hace falta ponerlo como ROLE_ADMIN, spring lo entiende
//                .antMatchers("/","/editar/**","/modificar/**")
//                .hasAnyRole("MANT","ADMIN","PROD")
//                .antMatchers("/","/index/**")
//                 .hasAnyRole("MANT","ADMIN","PROD","TECNICO","MONITOR")
//        .and()
//                .formLogin()
//                .loginPage("/login")
//                .and()
//                .exceptionHandling().accessDeniedPage("/errores/403");

        http.authorizeRequests()
                .antMatchers("/editar/**", "/modificar/**", "/eliminar", "/crearUsuario/**", "/gestionUsuarios/**", "/gestionar/")
                .hasRole("ADMIN")
                .antMatchers("/", "/editar/**", "/modificar/**")
                .hasAnyRole("MANT", "ADMIN", "PROD")
                .antMatchers("/", "/index/**")
                .hasAnyRole("MANT", "ADMIN", "PROD", "TECNICO", "MONITOR")
                .and()
                .formLogin()
                .loginPage("/login")
                .and()
//                .logout()
//                .logoutUrl("/logout") // URL que maneja el logout
//                .logoutSuccessUrl("/login?logout") // Redirige a la página de login después del logout
//                .invalidateHttpSession(true) // Invalida la sesión después del logout
//                .deleteCookies("JSESSIONID") // Elimina la cookie de sesión
//                .permitAll() // Permite que todos accedan al logout
//                .and()
                .exceptionHandling().accessDeniedPage("/errores/403");

    }

}
