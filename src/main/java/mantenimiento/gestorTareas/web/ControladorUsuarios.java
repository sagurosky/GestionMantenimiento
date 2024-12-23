package mantenimiento.gestorTareas.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import mantenimiento.gestorTareas.datos.RolDao;
import mantenimiento.gestorTareas.datos.UsuarioDao;
import mantenimiento.gestorTareas.dominio.Rol;
import mantenimiento.gestorTareas.dominio.Tarea;
import mantenimiento.gestorTareas.dominio.Tecnico;
import mantenimiento.gestorTareas.dominio.Usuario;
import mantenimiento.gestorTareas.servicio.Servicio;
import mantenimiento.gestorTareas.servicio.TecnicoService;
import mantenimiento.gestorTareas.servicio.UsuarioService;
import mantenimiento.gestorTareas.util.EncriptarPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    @Autowired
    TecnicoService tecnicoService;

    
    @PostMapping("/generarAdmin")
    public String generarAdmin(Usuario usuario, Errors errores) {
        
        
          Boolean yaExiste = usuarioDao.findByUsername(usuario.getUsername()) != null;

        if (errores.hasErrors() || yaExiste) {
            if (yaExiste) {
                errores.rejectValue("username", "500", "ya existe ese usuario");
            }
            return "/login";
        }
        
        
        
        List<Rol> roles=new ArrayList<>();
        
// agregar aca para cada rol que vaya a usar

        Rol rol=new Rol();
        rol.setNombre("ROLE_ADMIN");
        rol.setUsuario(usuario);
        roles.add(rol);
        
        Rol rolMant = new Rol();
        rolMant.setUsuario(rol.getUsuario());
        rolMant.setNombre("ROLE_MANT");
        roles.add(rolMant);

        Rol rolProd = new Rol();
        rolProd.setUsuario(rol.getUsuario());
        rolProd.setNombre("ROLE_PROD");
        roles.add(rolProd);

        usuario.setRoles(roles);
        
        usuario.setPassword(EncriptarPassword.encriptarPassword(usuario.getPassword()));
        usuarioService.guardar(usuario);
        
        
        return "/login";
    }
    
    @GetMapping("/gestionUsuarios")
    public String gestionarUsuarios(Usuario usuario, Model model) {

        var usuarios = usuarioService.listarUsuarios();
        model.addAttribute("usuarios", usuarios);

        return "gestionUsuarios";
    }

    @GetMapping("/crearUsuario")
    public String crearUsuarios(Usuario usuario, Rol rol) {

        return "crearUsuario";
    }

    @PostMapping("/gestionar")
    public String gestionar(@Valid Usuario usuario, Errors errores, Rol rol, Model model) {

        Boolean yaExiste = usuarioDao.findByUsername(usuario.getUsername()) != null;

        if (errores.hasErrors() || yaExiste) {
            if (yaExiste) {
                errores.rejectValue("username", "500", "ya existe ese usuario");
            }
            return "crearUsuario";
        }
//        limpiarRoles();
        rol.setUsuario(usuario);
        if (rol.getNombre() != null) {
            if (rol.getNombre().equals("MANTENIMIENTO")) {
                rol.setNombre("ROLE_MANT");
            } else if (rol.getNombre().equals("PRODUCCION")) {
                rol.setNombre("ROLE_PROD");
            } else if (rol.getNombre().equals("ADMINISTRADOR")) {
                rol.setNombre("ROLE_ADMIN");
            }else if (rol.getNombre().equals("TECNICO")) {
                rol.setNombre("ROLE_TECNICO");
            }else if (rol.getNombre().equals("MONITOR")) {
                rol.setNombre("ROLE_MONITOR");
            }
        }

        usuario.setPassword(EncriptarPassword.encriptarPassword(usuario.getPassword()));
        usuarioService.guardar(usuario);
        usuario = usuarioDao.findByUsername(usuario.getUsername());
        rol.setUsuario(usuario);
        rolDao.save(rol);

        //si es administrador lo guardo con todos los permisos
        if (rol.getNombre().equals("ROLE_ADMIN")) {

            Rol rolMant = new Rol();
             rolMant.setUsuario(rol.getUsuario());
            rolMant.setNombre("ROLE_MANT");
            rolDao.save(rolMant);
            
            Rol rolProd = new Rol();
            rolProd.setUsuario(rol.getUsuario());
            rolProd.setNombre("ROLE_PROD");
            rolDao.save(rolProd);
            
            Rol rolTec = new Rol();
            rolTec.setUsuario(rol.getUsuario());
            rolTec.setNombre("ROLE_TECNICO");
            rolDao.save(rolTec);
            
            Rol rolMonitor = new Rol();
            rolMonitor.setUsuario(rol.getUsuario());
            rolMonitor.setNombre("ROLE_MONITOR");
            rolDao.save(rolMonitor);

        }
        //si es un técnico que vaya a la pagina para cargar sus datos
        if (rol.getNombre().equals("ROLE_TECNICO")) {
            Tecnico tecnico=new Tecnico();
            tecnico.setUsuario(usuario);
            model.addAttribute("tecnico", tecnico);
            return "TecnicoDatosEmpresa";
        }
        return "redirect:/gestionUsuarios";
    }

    @GetMapping("/editarUsuario/{idUsuario}")
    public String editarUsuario(Usuario usuario, Model model) {
        usuario = servicio.encontrarUsuario(usuario);
        
        model.addAttribute("usuario", usuario);

        return "editarUsuario";
    }

    @PostMapping("/guardarUsuarioEditado")
    public String guardarUsuarioEditado(@RequestParam(value="rol", required=false)String[] rolesReq,@Valid Usuario usuarioReq, Errors errores) {
        
        if (errores.hasErrors()) {
            return "editarUsuario";
        }
        Usuario usuario=usuarioDao.getById(usuarioReq.getIdUsuario());
        
       Usuario nuevoUsuario=new Usuario();
       
        nuevoUsuario.setUsername(usuarioReq.getUsername());
        nuevoUsuario.setPassword(EncriptarPassword.encriptarPassword(usuarioReq.getPassword()));
        List<Rol> roles=new ArrayList<>();
        for (String role : rolesReq) {
            Rol rol=new Rol();
            rol.setNombre(role);
            rol.setUsuario(nuevoUsuario);
            
            roles.add(rol);
        }
         nuevoUsuario.setRoles(roles);
        
      // si alguno de los roles es administrador le agrego todos los roles
        for (Rol rol : roles) {
            if(rol.getNombre().equals("ROLE_ADMIN")){
                List<Rol> rolesAdmin=new ArrayList<>();
                
                Rol rolAdmin = new Rol();
                Rol rolMant = new Rol();
                Rol rolProd = new Rol();
                Rol rolTecnico = new Rol();
                Rol rolMonitor = new Rol();
                
                rolAdmin.setUsuario(usuario);
                rolMant.setUsuario(usuario);
                rolProd.setUsuario(usuario);
                rolTecnico.setUsuario(usuario);
                rolMonitor.setUsuario(usuario);
                
                rolAdmin.setNombre("ROLE_ADMIN");
                rolMant.setNombre("ROLE_MANT");
                rolProd.setNombre("ROLE_PROD");
                rolTecnico.setNombre("ROLE_TECNICO");
                rolMonitor.setNombre("ROLE_MONITOR");
                
                rolesAdmin.add(rolAdmin);
                rolesAdmin.add(rolMant);
                rolesAdmin.add(rolProd);
                rolesAdmin.add(rolTecnico);
                rolesAdmin.add(rolMonitor);
                
                nuevoUsuario.setRoles(rolesAdmin);
                break;
            }
        }
        
        
//        if (rol.getNombre().equals("ROLE_ADMIN")) {
//
//            Rol rolMant = new Rol();
//             rolMant.setUsuario(rol.getUsuario());
//            rolMant.setNombre("ROLE_MANT");
//            rolDao.save(rolMant);
//            
//            Rol rolProd = new Rol();
//            rolProd.setUsuario(rol.getUsuario());
//            rolProd.setNombre("ROLE_PROD");
//            rolDao.save(rolProd);
//            
//            Rol rolTec = new Rol();
//            rolTec.setUsuario(rol.getUsuario());
//            rolTec.setNombre("ROLE_TECNICO");
//            rolDao.save(rolTec);
//            
//            Rol rolMonitor = new Rol();
//            rolMonitor.setUsuario(rol.getUsuario());
//            rolMonitor.setNombre("ROLE_MONITOR");
//            rolDao.save(rolMonitor);
//
//        }
       usuarioDao.save(nuevoUsuario);
        
        for (Rol role : nuevoUsuario.getRoles()) {
            role.setUsuario(nuevoUsuario);
         rolDao.save(role);
         rolDao.flush();
        }
        
        
        if (usuario.getRoles().stream().anyMatch(rol -> "ROLE_TECNICO".equals(rol.getNombre())) &&
            usuario.getRoles().stream().noneMatch(rol -> "ROLE_ADMIN".equals(rol.getNombre()))) 
        {
            Tecnico tecnico = tecnicoService.traerPorUsuario(usuario);
            tecnico.setUsuario(nuevoUsuario);
            tecnicoService.save(tecnico);
        }
        usuarioDao.delete(usuario);
        
        
        return "redirect:/gestionUsuarios";
    }

    @GetMapping("/eliminarUsuario/{idUsuario}")
    public String eliminarUsuario(Usuario usuario) {
        
//        Usuari
        
        usuarioDao.delete(usuario);
//        limpiarRoles();

        return "redirect:/gestionUsuarios";
    }

//    private void limpiarRoles() {
//        var roles = new ArrayList<Rol>();
//        roles = (ArrayList<Rol>) rolDao.findAll();
//
//        for (Rol r : roles) {
//            if (r.getIdUsuario() == null) {
//                rolDao.delete(r);
//            }
//        }
//        rolDao.flush();
//    }

}
