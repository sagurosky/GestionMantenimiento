package mantenimiento.gestorTareas.web;

import java.util.ArrayList;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import mantenimiento.gestorTareas.datos.RolDao;
import mantenimiento.gestorTareas.datos.UsuarioDao;
import mantenimiento.gestorTareas.dominio.Rol;
import mantenimiento.gestorTareas.dominio.Tarea;
import mantenimiento.gestorTareas.dominio.Usuario;
import mantenimiento.gestorTareas.servicio.Servicio;
import mantenimiento.gestorTareas.servicio.UsuarioService;
import mantenimiento.gestorTareas.util.EncriptarPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class ControladorUsuarios {

    @Autowired
    Servicio servicio;
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    UsuarioDao usuarioDao;
    @Autowired
    RolDao rolDao;

    @GetMapping("/gestionUsuarios")
    public String gestionarUsuarios(Usuario usuario, Model model) {

        var usuarios = usuarioService.listarUsuarios();
        var rolesCodificados = new ArrayList<Rol>();
        for (Usuario u : usuarios) {

            Long idUsuario = u.getIdUsuario();
            var roles = u.getRoles();

            Rol rol = new Rol();
            rol.setIdUsuario(idUsuario);
            for (Rol r : roles) {
                if (r.getNombre().equals("ROLE_ADMIN")) {
                    rol.setNombre("ADMINISTRADOR");
                    break;
                } else if (r.getNombre().equals("ROLE_MANT")) {
                    rol.setNombre("MANTENIMIENTO");
                } else if (r.getNombre().equals("ROLE_PROD")) {
                    rol.setNombre("PRODUCCION");
                }
            }

            rolesCodificados.add(rol);
        }
        log.info("roles: " + rolesCodificados);
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("roles", rolesCodificados);

        return "gestionUsuarios";
    }

    @GetMapping("/crearUsuario")
    public String crearUsuarios(Usuario usuario, Rol rol) {

        return "crearUsuario";
    }

    @PostMapping("/gestionar")
    public String gestionar(@Valid Usuario usuario, Errors errores, Rol rol) {

        Boolean yaExiste = usuarioDao.findByUsername(usuario.getUsername()) != null;

        if (errores.hasErrors() || yaExiste) {
            if (yaExiste) {
                errores.rejectValue("username", "500", "ya existe ese usuario");
            }
            return "crearUsuario";
        }
        limpiarRoles();
        rol.setIdUsuario(usuario.getIdUsuario());
        if (rol.getNombre() != null) {
            if (rol.getNombre().equals("MANTENIMIENTO")) {
                rol.setNombre("ROLE_MANT");
            } else if (rol.getNombre().equals("PRODUCCION")) {
                rol.setNombre("ROLE_PROD");
            } else if (rol.getNombre().equals("ADMINISTRADOR")) {
                rol.setNombre("ROLE_ADMIN");

            }
        }

        usuario.setPassword(EncriptarPassword.encriptarPassword(usuario.getPassword()));
        usuarioService.guardar(usuario);
        usuario = usuarioDao.findByUsername(usuario.getUsername());
        rol.setIdUsuario(usuario.getIdUsuario());
        rolDao.save(rol);

        //si es administrador lo guardo con todos los permisos
        if (rol.getNombre().equals("ROLE_ADMIN")) {

            Rol rolMant = new Rol();
            rolMant.setIdUsuario(rol.getIdUsuario());
            rolMant.setNombre("ROLE_MANT");
            rolDao.save(rolMant);
            Rol rolProd = new Rol();
            rolProd.setIdUsuario(rol.getIdUsuario());
            rolProd.setNombre("ROLE_PROD");
            rolDao.save(rolProd);

        }
        return "redirect:/gestionUsuarios";
    }

    @GetMapping("/editarUsuario/{idUsuario}")
    public String editarUsuario(Usuario usuario, Model model) {
        usuario = servicio.encontrarUsuario(usuario);
        Long idUsuario = usuario.getIdUsuario();

        //logica para levantar el rol del usuario
        var roles = rolDao.findAll().stream()
                .filter(x -> x.getIdUsuario() == idUsuario)
                .toArray();

        Rol rolForEach = new Rol();
        Rol rol = new Rol();
        for (Object r : roles) {
            rolForEach = (Rol) r;
            if (rolForEach.getNombre().equals("ROLE_ADMIN")) {
                rol.setNombre("ADMINISTRADOR");
                break;
            } else if (rolForEach.getNombre().equals("ROLE_MANT")) {
                rol.setNombre("MANTENIMIENTO");
            } else if (rolForEach.getNombre().equals("ROLE_PROD")) {
                rol.setNombre("PRODUCCION");
            }
        }
        model.addAttribute("usuario", usuario);
        model.addAttribute("rol", rol);

        return "editarUsuario";
    }

    @PostMapping("/guardarUsuarioEditado")
    public String guardarUsuarioEditado(@Valid Usuario usuario, Errors errores, Rol rol) {

        if (errores.hasErrors()) {
            return "editarUsuario";
        }

        rol.setIdUsuario(usuario.getIdUsuario());
        if (rol.getNombre() != null) {
            if (rol.getNombre().equals("MANTENIMIENTO")) {
                rol.setNombre("ROLE_MANT");
            } else if (rol.getNombre().equals("PRODUCCION")) {
                rol.setNombre("ROLE_PROD");
            } else if (rol.getNombre().equals("ADMINISTRADOR")) {
                rol.setNombre("ROLE_ADMIN");
            }
        }

        usuario.setPassword(EncriptarPassword.encriptarPassword(usuario.getPassword()));

        usuarioService.guardar(usuario);
        rol.setIdUsuario(usuario.getIdUsuario());
        rolDao.save(rol);
        rolDao.flush();
        //si es administrador lo guardo con todos los permisos
        if (rol.getNombre().equals("ROLE_ADMIN")) {

            Rol rolMant = new Rol();
            rolMant.setIdUsuario(rol.getIdUsuario());
            rolMant.setNombre("ROLE_MANT");
            rolDao.save(rolMant);
            Rol rolProd = new Rol();
            rolProd.setIdUsuario(rol.getIdUsuario());
            rolProd.setNombre("ROLE_PROD");
            rolDao.save(rolProd);

        }
        limpiarRoles();
        return "redirect:/gestionUsuarios";
    }

    @GetMapping("/eliminarUsuario/{idUsuario}")
    public String eliminarUsuario(Usuario usuario) {

        usuarioDao.delete(usuario);
        limpiarRoles();

        return "redirect:/gestionUsuarios";
    }

    private void limpiarRoles() {
        var roles = new ArrayList<Rol>();
        roles = (ArrayList<Rol>) rolDao.findAll();

        for (Rol r : roles) {
            if (r.getIdUsuario() == null) {
                rolDao.delete(r);
            }
        }
        rolDao.flush();
    }

}
