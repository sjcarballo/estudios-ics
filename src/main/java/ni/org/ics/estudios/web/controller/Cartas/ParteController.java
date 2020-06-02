package ni.org.ics.estudios.web.controller.Cartas;

import com.google.gson.Gson;
import ni.org.ics.estudios.domain.catalogs.Carta;
import ni.org.ics.estudios.domain.catalogs.Parte;
import ni.org.ics.estudios.domain.catalogs.Version;
import ni.org.ics.estudios.service.scancarta.ScanCartaService;
import org.apache.commons.lang3.text.translate.UnicodeEscaper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ICS on 10/05/2020.
 */

@Controller
@RequestMapping("/CatalogoParte")
public class ParteController {

    private static final Logger logger = LoggerFactory.getLogger(CartasController.class);
    @Resource(name = "scanCartaService")
    private ScanCartaService scanCartaService;

    @RequestMapping(value = "/ListadoParte", method = RequestMethod.GET)
    public String ListadoVersion(Model model)throws Exception{
        List<Parte> parte = scanCartaService.getListParte();
        if (parte != null){
            model.addAttribute("parte", parte);
            return "/CatalogoScanCarta/ListaParte";
        }else {
            return "404";
        }
    }

    /* Crear una nueva Parte */
    @RequestMapping(value = "/CrearNuevaParte", method = RequestMethod.GET)
    public String CrearNuevaCarta(Model model)throws Exception{
        List<Carta> cartas = scanCartaService.getCartaActiva();
        model.addAttribute("cartas", cartas);
        List<Version> version = scanCartaService.getVersionActiva();
        model.addAttribute("version",version);
        return"/CatalogoScanCarta/Parte";
    }

    @RequestMapping( value="saveParte", method=RequestMethod.POST)
    public ResponseEntity<String> saveVersion(@RequestParam( value="idversion", required=true ) Integer idversion
            , @RequestParam( value="parte", required=true ) String parte
            , @RequestParam( value="activo", required=true ) String activo
    )throws Exception{
        try {
            Version v = new Version();
            Parte p = new Parte();
            if (!scanCartaService.CheckequalsParte(parte,idversion)){
                p.setParte(parte);
                String ac ="";
                if (activo == null || activo.equals(null) || activo.equals("off")){
                    ac="false";
                }
                else if (activo.equals("on") || activo.equals("true")){
                    ac ="true";
                }
                else {
                    ac ="false";
                }
                p.setActivo(ac);
                v.setIdversion(idversion);
                p.setVersion(v);
                p.setCod("");
                p.setAcepta("false");
                p.setDeviceid("server");
                p.setEstado('0');
                p.setPasive('1');
                p.setRecordDate(new Date());
                p.setRecordUser(SecurityContextHolder.getContext().getAuthentication().getName());
                scanCartaService.SaveParte(p);
                return createJsonResponse(p);
            } else{
                Map<String, String> map = new HashMap<String, String>();
                map.put("msj", "Parte ya existe!" );
                return createJsonResponse(map);
            }
        }
        catch (Exception ex){
            Gson gson = new Gson();
            String json = gson.toJson(ex.toString());
            return new ResponseEntity<String>( json, HttpStatus.CREATED);
        }
    }

    @RequestMapping(value = "/editParte/{idparte}", method = RequestMethod.GET)
    public ModelAndView editParte(@PathVariable(value = "idparte") Integer idparte, Model model)throws ParseException {
        ModelAndView modelView = new ModelAndView();
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Parte part = this.scanCartaService.getParteById(idparte);
            modelView.addObject("part",part);
            List<Carta> cartas = scanCartaService.getCartaActiva();
            model.addAttribute("cartas",cartas);
            List<Version> version = scanCartaService.getVersioCarta(part.getVersion().getCarta().getIdcarta());
            model.addAttribute("version",version);
            modelView.setViewName("/CatalogoScanCarta/EditParte");
            return modelView;
        }
        catch (Exception e){
            Gson gson = new Gson();
            String json = gson.toJson(e.toString());
            return (modelView);
        }
    }

    @RequestMapping(value = "/GetVersion", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
            String GetVersion(@RequestParam(value = "idcarta", required = true) Integer idcarta, Model model)
            throws ParseException {
        List<Version> version = null;
        try{
            version =  scanCartaService.getByIdCarta(idcarta);
            model.addAttribute("version",version);
            String jsonResponse;
            jsonResponse = new Gson().toJson(model);
            UnicodeEscaper escaper = UnicodeEscaper.above(127);
            return escaper.translate(jsonResponse);
        }catch (Exception e){
            return "Not Found!";
        }
    }

        @RequestMapping(value = "UpdateParte", method = RequestMethod.POST)
    public ResponseEntity<String> UpdateParte(@RequestParam( value="idparte", required=true ) Integer idparte
            ,@RequestParam( value="idversion", required=true ) Integer idversion
            ,@RequestParam( value="parte", required=true ) String parte
            ,@RequestParam( value="activo", required=false ) String activo){
        try{
            Parte p = new Parte();
            p.setIdparte(idparte);
            Version v = new Version();
            v.setIdversion(idversion);
            p.setVersion(v);
            p.setParte(parte);
            String ac="";
            if (activo == null || activo.equals(null) || activo.equals("off")){
                ac="false";
            }
            else if (activo.equals("on") || activo.equals("true")){
                ac ="true";
            }
            else {
                ac ="false";
            }
            p.setActivo(ac);
            p.setCod("");
            p.setAcepta("false");
            p.setDeviceid("server");
            p.setEstado('0');
            p.setPasive('1');
            p.setRecordDate(new Date());
            p.setRecordUser(SecurityContextHolder.getContext().getAuthentication().getName());
            scanCartaService.SaveParte(p);
            return createJsonResponse(v);
        }
        catch (Exception e){
            Gson gson = new Gson();
            String json = gson.toJson(e.toString());
            return  new ResponseEntity<String>( json, HttpStatus.CREATED);
        }

    }

    @RequestMapping(value = "/HabYDesParte/{accion}/{idparte}", method= RequestMethod.GET)
    public String HabYDesParte(@PathVariable("idparte") String idparte,
                               @PathVariable("accion") String accion, RedirectAttributes redirectAttributes) throws Exception{
        String redirecTo = "404";
        try{
            Integer id = Integer.parseInt(idparte);
            if (accion.matches("bloq")){
                redirecTo = "redirect:/CatalogoParte/ListadoParte";
                scanCartaService.DesHabilitarParte(id);
                redirectAttributes.addAttribute("usuarioDeshabilitado", true);
                redirectAttributes.addFlashAttribute("nombreUsuario", idparte);
            }
            else if (accion.matches("Unbloq")){
                redirecTo = "redirect:/CatalogoParte/ListadoParte";
                scanCartaService.HabilitarParte(id);
                redirectAttributes.addAttribute("usuarioHabilitado", true);
                redirectAttributes.addFlashAttribute("nombreUsuario", idparte);
            }else {
                redirecTo = "redirect:/CatalogoParte/ListadoParte";
            }
        }catch (Exception ex){
            return redirecTo;
        }
        return redirecTo;
    }



    /*  Esta Funcion retorna un Json  */
    private ResponseEntity<String> createJsonResponse( Object o )
    {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        Gson gson = new Gson();
        String json = gson.toJson(o);
        UnicodeEscaper escaper = UnicodeEscaper.above(127);
        json = escaper.translate(json);
        return new ResponseEntity<String>( json, headers, HttpStatus.CREATED );
    }
}
