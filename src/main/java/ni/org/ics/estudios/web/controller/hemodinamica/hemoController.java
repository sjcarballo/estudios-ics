package ni.org.ics.estudios.web.controller.hemodinamica;

import com.google.gson.Gson;
import ni.org.ics.estudios.domain.Participante;
import ni.org.ics.estudios.domain.catalogs.Barrio;
import ni.org.ics.estudios.domain.hemodinamica.DatosHemodinamica;
import ni.org.ics.estudios.domain.hemodinamica.HemoDetalle;
import ni.org.ics.estudios.domain.muestreoanual.ParticipanteProcesos;
import ni.org.ics.estudios.language.MessageResource;
import ni.org.ics.estudios.service.MessageResourceService;
import ni.org.ics.estudios.service.muestreoanual.ParticipanteProcesosService;
import ni.org.ics.estudios.web.utils.DateUtil;
import org.apache.commons.lang3.text.translate.UnicodeEscaper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ICS_Inspiron3 on 22/05/2019.
 */
@Controller
@RequestMapping("/hemo")
public class hemoController {
    private static final Logger logger = LoggerFactory.getLogger(hemoController.class);

    /*Agregar Detalle Hemodinamico del Participante */
    @RequestMapping(value="/listDetailsHemo/{idDatoHemo}", method = RequestMethod.GET)
    public ModelAndView addDetalleHemo(@PathVariable(value="idDatoHemo" ) String idDatoHemo)throws ParseException {
        ModelAndView mv = new ModelAndView();
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            List<HemoDetalle>  hemo = datoshemodinamicaService.getListHemoDetalle(idDatoHemo);
            DatosHemodinamica obj = datoshemodinamicaService.getbyId(idDatoHemo);
            /* Esto es para obtener la lista de nivel de conciencia compararla recorrerla en la vista con un foreach i un if*/
            List<MessageResource> nivel = messageResourceService.getCatalogo("NIVELCONCIENCIA");
            mv.addObject("nivel",nivel);
            List<MessageResource> extremidades = messageResourceService.getCatalogo("EXTREMIDADES");
            mv.addObject("extremidades", extremidades);
            List<MessageResource> pulsoCalidad = messageResourceService.getCatalogo("PULSOCALIDAD");
            mv.addObject("pulsoCalidad", pulsoCalidad);
            List<MessageResource> llenadoCapilar = messageResourceService.getCatalogo("LLENADOCAPILAR");
            mv.addObject("llenadoCapilar", llenadoCapilar);
            mv.addObject("hemo", hemo);
            mv.addObject("codigo", obj.getParticipante().getCodigo());
            mv.addObject("nombre", obj.getParticipante().getNombreCompleto());
            mv.addObject("edad",obj.getParticipante().getEdad());
            mv.addObject("fechanac", obj.getParticipante().getFechaNac());
            mv.addObject("expediente", obj.getnExpediente());
            mv.addObject("idDatoHemo", obj.getIdDatoHemo());
            mv.setViewName("/hemodinamica/listadoDetalle");
            return mv;
        }
        catch (Exception e){
            return mv;
        }
    }



    @RequestMapping(value="/create", method = RequestMethod.GET)
    public String login(ModelMap model) {
        List<Barrio> barrios = datoshemodinamicaService.getBarrios();
        model.addAttribute("barrios", barrios);
        return "/hemodinamica/datos";
    }

    /* Instancia de mi Servicio Hemodinamico */
    @Resource(name = "messageResourceService")
    private MessageResourceService messageResourceService;

    @Resource(name = "participanteProcesosService")
    private ParticipanteProcesosService participanteProcesosService;

    @RequestMapping(value="adddetails/{idDatoHemo}", method = RequestMethod.GET)
    public String adddetails(@PathVariable(value="idDatoHemo" ) String idDatoHemo, ModelMap model) {
        /*Select poblar el select de nivel de conciencia*/
            List<MessageResource> nivelConciencia = messageResourceService.getCatalogo("NIVELCONCIENCIA");
            model.addAttribute("nivelConciencia", nivelConciencia);
            List<MessageResource> pulsoCalidad = messageResourceService.getCatalogo("PULSOCALIDAD");
            model.addAttribute("pulsoCalidad", pulsoCalidad);
            List<MessageResource> extremidades = messageResourceService.getCatalogo("EXTREMIDADES");
            model.addAttribute("extremidades", extremidades);
            List<MessageResource> clasificacion = messageResourceService.getCatalogo("CLASIFICACIONDENGUE");
            model.addAttribute("clasificacion", clasificacion);
            List<MessageResource> llenadoCapilar = messageResourceService.getCatalogo("LLENADOCAPILAR");
            model.addAttribute("llenadoCapilar", llenadoCapilar);
            model.addAttribute("idDatoHemo", idDatoHemo);
            /* Persona que Valida */
            List<MessageResource> personaValida = messageResourceService.getCatalogo("PERSONAVALIDA");

            List<MessageResource> diuresis = messageResourceService.getCatalogo("DIURESIS");
            model.addAttribute("diuresis", diuresis);

            model.addAttribute("personaValida", personaValida);
            return "/hemodinamica/formhemodetalle";
    }

    /* Instancia de mi Servicio Hemodinamico */
    @Resource(name = "datoshemodinamicaService")
    private ni.org.ics.estudios.service.hemodinanicaService.datoshemodinamicaService datoshemodinamicaService;


    /*MAPEAR LISTADO DE TODOS LOS PACIENTE CON HOJA HEMODINAMICA*/
    @RequestMapping(value = "/listado", method = RequestMethod.GET)
    public ModelAndView ListadoHemo() throws Exception {
        try {
        List<DatosHemodinamica> lista = datoshemodinamicaService.getListadoHemo();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("lista", lista);
        modelAndView.setViewName("/hemodinamica/listado");
        return modelAndView;
        }catch (Exception e){
            throw e;
        }
    }
    /* FIN DEL METODO LISTADO*/

    /*Buscar lo faltante para modals*/
    @RequestMapping(value = "ViewResutl/{idHemoDetalle}", method = RequestMethod.GET, produces="application/json" )
    public @ResponseBody
    String BuscarResultado(@PathVariable(value = "idHemoDetalle") String idHemoDetalle)  throws ParseException {
        Map<String, String> map = new HashMap<String, String>();
        HemoDetalle obj = datoshemodinamicaService.getByHemoDetalleId(idHemoDetalle);
        map.put("pa", obj.getPa());
        map.put("pp", obj.getPp());
        map.put("pam", obj.getPam());
        map.put("fcardi", obj.getFc());
        map.put("fr", obj.getFr());
        map.put("tc", obj.getTc());
        map.put("sa", obj.getSa());
        map.put("diuresis", obj.getDiuresis());
        map.put("densidadU", obj.getDensidadUrinaria());
        map.put("personaValida", obj.getPersonaValida());
        String jsonResponse;
        jsonResponse = new Gson().toJson(map);
        //escapar caracteres especiales, escape de los caracteres con valor num�rico mayor a 127
        UnicodeEscaper escaper = UnicodeEscaper.above(127);
        return escaper.translate(jsonResponse);
    }

    /* Controlador para realizar por una búsqueda de participante
    * http://localhost:8080/estudios_ics/hemo/searchParticipant?participantCode=100
    * */
    @RequestMapping(value = "/searchParticipant", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
     String BuscarParticipanteByID(@RequestParam(value="parametro", required=true ) Integer parametro)
            throws Exception {
        try {
        Map<String, String> map = new HashMap<String, String>();
        Participante participante = datoshemodinamicaService.getParticipante(parametro);
        if (participante != null){
            ParticipanteProcesos procesos = participanteProcesosService.getParticipante(parametro);
            map.put("estado",procesos.getEstPart().toString());
        }
        map.put("codigo", participante.getCodigo().toString());
        map.put("nombre", participante.getNombreCompleto());
        map.put("fecha", DateUtil.DateToString(participante.getFechaNac(),"dd/MM/yyyy"));
        map.put("edad", participante.getEdad());
        map.put("direccion", participante.getCasa().getDireccion());
        map.put("barrio", participante.getCasa().getBarrio().getCodigo().toString());
        String jsonResponse;
        jsonResponse = new Gson().toJson(map);
        //escapar caracteres especiales, escape de los caracteres con valor num�rico mayor a 127
        UnicodeEscaper escaper = UnicodeEscaper.above(127);
        return escaper.translate(jsonResponse);
    }catch (Exception e){
        throw e;
    }
    }



        /* Guardar datos Hemodinamica 29/05/2019 - first save */
        @RequestMapping(value="addHemodinamica", method=RequestMethod.POST)
        public ResponseEntity<String>addHemodinamica(@RequestParam( value="asc", required=true ) double asc
                , @RequestParam( value="direccion", required=true ) String direccion
                , @RequestParam( value="edad", required=true ) String edad
                , @RequestParam( value="fecha", required=true ) Date fecha
                , @RequestParam( value="imc", required=true ) double imc
                , @RequestParam( value="municipio", required=true ) String municipio
                , @RequestParam( value="expediente", required=true ) String nExpediente
                , @RequestParam( value="peso", required=true ) String peso
                , @RequestParam( value="sector", required=true ) String sector
                , @RequestParam(value = "barrioF") String barrioF
                , @RequestParam( value="silais", required=true ) String silais
                , @RequestParam( value="talla", required=true ) double talla
                , @RequestParam( value="telefono", required=true ) String telefono
                , @RequestParam( value="uSalud",  required=true ) String uSalud
                , @RequestParam( value="idParticipante" ) int idParticipante
                , @RequestParam( value="IMCdetallado", required=true ) String IMCdetallado
                , @RequestParam( value="fie", required=true ) String fie
                , @RequestParam( value="diasenf", required=true ) Integer diasenf
                , @RequestParam( value="sdMin", required=false ) String sdMin
                , @RequestParam( value="sdMed", required=false ) String sdMed
                , @RequestParam( value="sdMax", required=false ) String sdMax
                , @RequestParam( value="pamMin", required=false ) String pamMin
                , @RequestParam( value="pamMed", required=false ) String pamMed
                , @RequestParam( value="pamMax", required=false ) String pamMax
                , @RequestParam( value="fcMin", required=false ) Integer fcMin
                , @RequestParam( value="fcMed", required=false ) Integer fcMed
                , @RequestParam( value="fcProm", required=false ) Integer fcProm
                , @RequestParam( value="frMin", required=false ) Integer frMin
                , @RequestParam( value="frMax", required=false ) Integer frMax
        ){
        try{
            DatosHemodinamica obj = new DatosHemodinamica();
            obj.setAsc(Double.valueOf(asc));
            obj.setDireccion(direccion);
            obj.setEdad(edad);
            obj.setFecha(fecha);
            obj.setImc(imc);
            obj.setMunicipio(municipio);
            obj.setnExpediente(nExpediente);
            obj.setPeso(peso);
            obj.setSector(sector);
            obj.setBarrioF(barrioF);
            obj.setSilais(silais);
            obj.setTalla(talla);
            obj.setTelefono(telefono);
            obj.setuSalud(uSalud);
            Participante p = new Participante();
            p.setCodigo(idParticipante);
            obj.setParticipante(p);
            obj.setIMCdetallado(IMCdetallado);
            obj.setRecordUser(SecurityContextHolder.getContext().getAuthentication().getName());
            obj.setRecordDate(new Date());
            obj.setFie(DateUtil.StringToDate(fie, "dd/MM/yyyy"));
            obj.setDiasenf(diasenf);
            obj.setSdMin(sdMin);
            obj.setSdMed(sdMed);
            obj.setSdMax(sdMax);
            obj.setPamMin(pamMin);
            obj.setPamMax(pamMax);
            obj.setPamMed(pamMed);
            obj.setFcMin(fcMin);
            obj.setFcMed(fcMed);
            obj.setFcProm(fcProm);
            obj.setFrMax(frMin);
            obj.setFrMin(frMax);
            obj.setEstado('1');
            obj.setPasive('0');
            obj.setDeviceid("server");
            datoshemodinamicaService.SaveDatosHemo(obj);
            return createJsonResponse(obj) ;
        }
        catch (Exception e){
            Gson gson = new Gson();
            String json = gson.toJson(e.toString());
            return  new ResponseEntity<String>( json, HttpStatus.CREATED);
        }
}

    /* Modificar datos Hemodinamica */
    /*@RequestMapping(value = "UpdateHemodinamica/{idDatoHemo}", method = RequestMethod.PUT)
    @PathVariable(value="idDatoHemo") String idDatoHemo,
    * */
    @RequestMapping(value = "UpdateHemodinamica", method = RequestMethod.POST)
    public ResponseEntity<String>UpdateHemodinamica(@RequestParam (value="idDatoHemo") String idDatoHemo,
              @RequestParam( value="asc", required=true ) double asc
            , @RequestParam( value="direccion", required=true ) String direccion
            , @RequestParam( value="edad", required=true ) String edad
            , @RequestParam( value="fecha", required=true ) String fecha
            , @RequestParam( value="imc", required=true ) double imc
            , @RequestParam( value="municipio", required=true ) String municipio
            , @RequestParam( value="expediente", required=true ) String nExpediente
            , @RequestParam( value="peso", required=true ) String peso
            , @RequestParam( value="sector", required=true ) String sector
            , @RequestParam(value = "barrioF") String barrioF
            , @RequestParam( value="silais", required=true ) String silais
            , @RequestParam( value="talla", required=true ) double talla
            , @RequestParam( value="telefono", required=true ) String telefono
            , @RequestParam( value="uSalud",  required=true ) String uSalud
            , @RequestParam( value="idParticipante" ) int idParticipante
            , @RequestParam( value="IMCdetallado", required=true ) String IMCdetallado
            , @RequestParam( value="fie", required=true ) String fie
            , @RequestParam( value="diasenf", required=true ) Integer diasenf
            , @RequestParam( value="sdMin", required=false ) String sdMin
            , @RequestParam( value="sdMed", required=false ) String sdMed
            , @RequestParam( value="sdMax", required=false ) String sdMax
            , @RequestParam( value="pamMin", required=false ) String pamMin
            , @RequestParam( value="pamMed", required=false ) String pamMed
            , @RequestParam( value="pamMax", required=false ) String pamMax
            , @RequestParam( value="fcMin", required=false ) Integer fcMin
            , @RequestParam( value="fcMed", required=false ) Integer fcMed
            , @RequestParam( value="fcProm", required=false ) Integer fcProm
            , @RequestParam( value="frMin", required=false ) Integer frMin
            , @RequestParam( value="frMax", required=false ) Integer frMax
    ){
        try{
            DatosHemodinamica obj = new DatosHemodinamica();
            obj.setIdDatoHemo(idDatoHemo);
            obj.setAsc(Double.valueOf(asc));
            obj.setDireccion(direccion);
            obj.setEdad(edad);
            obj.setFecha(DateUtil.StringToDate(fecha, "dd/MM/yyyy"));
            obj.setImc(imc);
            obj.setMunicipio(municipio);
            obj.setnExpediente(nExpediente);
            obj.setPeso(peso);
            obj.setSector(sector);
            obj.setBarrioF(barrioF.toUpperCase().trim());
            obj.setSilais(silais);
            obj.setTalla(talla);
            obj.setTelefono(telefono);
            obj.setuSalud(uSalud);
            Participante p = new Participante();
            p.setCodigo(idParticipante);
            obj.setParticipante(p);
            obj.setIMCdetallado(IMCdetallado);
            obj.setRecordUser(SecurityContextHolder.getContext().getAuthentication().getName());
            obj.setRecordDate(new Date());
            obj.setFie(DateUtil.StringToDate(fie,"dd/MM/yyyy"));
            obj.setDiasenf(diasenf);
            obj.setSdMin(sdMin);
            obj.setSdMed(sdMed);
            obj.setSdMax(sdMax);
            obj.setPamMin(pamMin);
            obj.setPamMed(pamMed);
            obj.setPamMax(pamMax);
            obj.setFcMin(fcMin);
            obj.setFcMed(fcMed);
            obj.setFcProm(fcProm);
            obj.setFrMax(frMin);
            obj.setFrMin(frMax);
            obj.setEstado('1');
            obj.setPasive('0');
            obj.setDeviceid("server");
            datoshemodinamicaService.SaveDatosHemo(obj);
            return createJsonResponse(obj) ;
        }catch (Exception e){
            Gson gson = new Gson();
            String json = gson.toJson(e.toString());
            return  new ResponseEntity<String>( json, HttpStatus.OK);
        }
    }

    /*Obtiene por Id de una lista/tabla de registro*/
    @RequestMapping(value = "/edithemo/{idDatoHemo}",method = RequestMethod.GET)
    public ModelAndView edithemo(@PathVariable(value="idDatoHemo" ) String idDatoHemo){
        ModelAndView modeliew = new ModelAndView();
        Map<String, String> map = new HashMap<String, String>();/* esta funcion es para mapear un objeto */
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            DatosHemodinamica obj = datoshemodinamicaService.getbyId(idDatoHemo);
            modeliew.addObject("obj", obj);
            List<Barrio> barrios = datoshemodinamicaService.getBarrios();
            modeliew.addObject("barrios", barrios);
            map.put("barrio",obj.getParticipante().getCasa().getBarrio().getCodigo().toString());
            modeliew.setViewName("/hemodinamica/formedit");
            return modeliew;
        }catch (Exception e){
            Gson gson = new Gson();
            String json = gson.toJson(e.toString());
            return (modeliew);
        }
    }

    /* -* OBTIENE UN REGISTRO PARA EDITARLO EN FORMULARIO -* */
    @RequestMapping(value = "/editdetails/{idHemoDetalle}", method = RequestMethod.GET)
    public ModelAndView editDetalleHemo(@PathVariable(value = "idHemoDetalle") String idHemoDetalle)throws ParseException{
        ModelAndView modelView = new ModelAndView();
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            HemoDetalle objDet = datoshemodinamicaService.getByHemoDetalleId(idHemoDetalle);
            modelView.addObject("objDet",objDet);
            List<MessageResource> nivelConciencia = messageResourceService.getCatalogo("NIVELCONCIENCIA");
            modelView.addObject("nivelConciencia", nivelConciencia);
            List<MessageResource> pulsoCalidad = messageResourceService.getCatalogo("PULSOCALIDAD");
            modelView.addObject("pulsoCalidad", pulsoCalidad);
            List<MessageResource> extremidades = messageResourceService.getCatalogo("EXTREMIDADES");
            modelView.addObject("extremidades", extremidades);
            List<MessageResource> clasificacion = messageResourceService.getCatalogo("CLASIFICACIONDENGUE");
            modelView.addObject("clasificacion", clasificacion);
            List<MessageResource> llenadoCapilar = messageResourceService.getCatalogo("LLENADOCAPILAR");
            modelView.addObject("llenadoCapilar", llenadoCapilar);
            List<MessageResource> personaValida = messageResourceService.getCatalogo("PERSONAVALIDA");
            modelView.addObject("personaValida", personaValida);
            List<MessageResource> diuresis = messageResourceService.getCatalogo("DIURESIS");
            modelView.addObject("diuresis", diuresis);
            modelView.setViewName("/hemodinamica/formEditDetalle");
            return modelView;
        }
        catch (Exception e){
            Gson gson = new Gson();
            String json = gson.toJson(e.toString());
            return (modelView);
        }
    }
        /* ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
    /* -- Guardar el Detalle de la hoja hemodinamica--  */
    @RequestMapping(value = "addHemoDetalle", method = RequestMethod.POST)
    public ResponseEntity<String>addHemoDetalle(@RequestParam( value="signo", required=true ) String signo
            ,@RequestParam( value="fecha", required=true ) String fecha
            ,@RequestParam( value="hora", required=true ) String hora
            ,@RequestParam( value="nivelConciencia", required=true ) String nivelConciencia
            ,@RequestParam( value="pa", required=true ) String pa
            ,@RequestParam( value="pp", required=true ) String pp
            ,@RequestParam( value="pam", required=true ) String pam
            ,@RequestParam( value="fc", required=true ) String fc
            ,@RequestParam( value="fr", required=true ) String fr
            ,@RequestParam( value="tc", required=true ) String tc
            ,@RequestParam( value="sa", required=true ) String sa
            ,@RequestParam( value="extremidades", required=true ) String extremidades
            ,@RequestParam( value="llenadoCapilar", required=true ) String llenadoCapilar
            ,@RequestParam( value="pulsoCalidad", required=true ) String pulsoCalidad
            ,@RequestParam( value="diuresis", required=true ) String diuresis
            ,@RequestParam( value="densidadUrinaria", required=true ) String densidadUrinaria
            ,@RequestParam( value="personaValida", required=true ) String personaValida
            ,@RequestParam( value="idDatoHemo", required=true) String idDatoHemo
            ,@RequestParam( value="impreso", required=false ) Boolean impreso
            ,@RequestParam( value="dx" ) String dx

    ){
        try{

            if (!datoshemodinamicaService.SiExisteHemo(idDatoHemo, DateUtil.StringToDate(fecha, "dd/MM/yyyy"), hora)) {
                HemoDetalle obj = new HemoDetalle();
                obj.setSigno(signo);
                obj.setDx(dx);
                obj.setFecha(DateUtil.StringToDate(fecha, "dd/MM/yyyy"));
                obj.setHora(hora);
                obj.setNivelConciencia(nivelConciencia);
                obj.setPa(pa);
                obj.setPp(pp);
                obj.setPam(pam);
                obj.setFc(fc);
                obj.setFr(fr);
                obj.setTc(tc);
                obj.setSa(sa);
                obj.setExtremidades(extremidades);
                obj.setLlenadoCapilar(llenadoCapilar);
                obj.setPulsoCalidad(pulsoCalidad);
                obj.setDiuresis(diuresis);
                obj.setDensidadUrinaria(densidadUrinaria);
                obj.setPersonaValida(personaValida);
                Character imp = '0';
                obj.setImpreso(imp);
                DatosHemodinamica o = datoshemodinamicaService.getbyId(idDatoHemo);
                obj.setDatoshemodinamica(o);
                obj.setEstado('1');
                obj.setPasive('0');
                obj.setDeviceid("server");
                obj.setRecordDate(new Date());
                obj.setRecordUser(SecurityContextHolder.getContext().getAuthentication().getName());
                datoshemodinamicaService.SaveDetalleHemo(obj);
                return createJsonResponse(obj);
            }else{
                Map<String, String> map = new HashMap<String, String>();
                map.put("msj", "El Detalle ya existe." );
                return createJsonResponse(map);
            }
        }
        catch (Exception e){
            Gson gson = new Gson();
            Map<String, String> map = new HashMap<String, String>();
            map.put("msj", e.toString() );
            //String json = gson.toJson(e.toString());
            return   createJsonResponse(map); // new ResponseEntity<String>( json, HttpStatus.CREATED);
        }
    }
    /* --  fin -- */
    /* MODIFICAR EL DETALLE DE LA HEMODINAMICA*/
    @RequestMapping(value = "UpdateDetalleHemo", method = RequestMethod.POST)
    public ResponseEntity<String> UpdateDetalleHemo(@RequestParam( value="idHemoDetalle", required=true ) String idHemoDetalle
            ,@RequestParam( value="signo", required=true ) String signo
            ,@RequestParam( value="dx", required=false ) String dx
            ,@RequestParam( value="fecha", required=true ) String fecha
            ,@RequestParam( value="hora", required=true ) String hora
            ,@RequestParam( value="nivelConciencia", required=true ) String nivelConciencia
            ,@RequestParam( value="pa", required=true ) String pa
            ,@RequestParam( value="pp", required=true ) String pp
            ,@RequestParam( value="pam", required=true ) String pam
            ,@RequestParam( value="fc", required=true ) String fc
            ,@RequestParam( value="fr", required=true ) String fr
            ,@RequestParam( value="tc", required=true ) String tc
            ,@RequestParam( value="sa", required=true ) String sa
            ,@RequestParam( value="extremidades", required=true ) String extremidades
            ,@RequestParam( value="llenadoCapilar", required=true ) String llenadoCapilar
            ,@RequestParam( value="pulsoCalidad", required=true ) String pulsoCalidad
            ,@RequestParam( value="diuresis", required=true ) String diuresis
            ,@RequestParam( value="densidadUrinaria", required=true ) String densidadUrinaria
            ,@RequestParam( value="personaValida", required=true ) String personaValida
            ,@RequestParam( value="idDatoHemo", required=true) String idDatoHemo
            ,@RequestParam( value="impreso", required=false ) Boolean impreso

    ){
        try{
            HemoDetalle objDetalle = new HemoDetalle();
            objDetalle.setIdHemoDetalle(idHemoDetalle);
            objDetalle.setSigno(signo);
            objDetalle.setDx(dx);
            objDetalle.setFecha(DateUtil.StringToDate(fecha, "dd/MM/yyyy"));
            objDetalle.setHora(hora);
            objDetalle.setNivelConciencia(nivelConciencia);
            objDetalle.setPa(pa);
            objDetalle.setPp(pp);
            objDetalle.setPam(pam);
            objDetalle.setFc(fc);
            objDetalle.setFr(fr);
            objDetalle.setTc(tc);
            objDetalle.setSa(sa);
            objDetalle.setExtremidades(extremidades);
            objDetalle.setLlenadoCapilar(llenadoCapilar);
            objDetalle.setPulsoCalidad(pulsoCalidad);
            objDetalle.setDiuresis(diuresis);
            objDetalle.setDensidadUrinaria(densidadUrinaria);
            objDetalle.setPersonaValida(personaValida);
            /*if (impreso){imp='1';}else {imp = '0';}*/
            Character imp='0';
            objDetalle.setImpreso(imp);
            DatosHemodinamica o = datoshemodinamicaService.getbyId(idDatoHemo);
            objDetalle.setDatoshemodinamica(o);
            objDetalle.setEstado('1');
            objDetalle.setPasive('0');
            objDetalle.setDeviceid("server");
            objDetalle.setRecordDate(new Date());
            objDetalle.setRecordUser(SecurityContextHolder.getContext().getAuthentication().getName());
            datoshemodinamicaService.SaveDetalleHemo(objDetalle);
            return createJsonResponse(objDetalle) ;
        }
        catch (Exception e){
            Gson gson = new Gson();
            String json = gson.toJson(e.toString());
            return  new ResponseEntity<String>( json, HttpStatus.CREATED);
        }
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