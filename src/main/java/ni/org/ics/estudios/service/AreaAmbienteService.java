package ni.org.ics.estudios.service;

import ni.org.ics.estudios.domain.encuestas.*;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Miguel Salinas on 5/3/2017.
 * V1.0
 */
@Transactional
@Service("areaAmbienteService")
public class AreaAmbienteService {
    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public List<Banio> getBaniosByHabitacion(String habitacion){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Banio where habitacion.codigo = :habitacion ");
        query.setParameter("habitacion",habitacion);
        return query.list();
    }

    public List<Cama> getCamasByHabitacion(String habitacion)
    {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Cama where habitacion.codigo = :habitacion");
        query.setParameter("habitacion",habitacion);
        return query.list();
    }

    public List<Ventana> getVentanasByArea(String codigoArea){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Ventana where codigo = :codigoArea");
        query.setParameter("codigoArea",codigoArea);
        return query.list();
    }

    public Banio getBanioByCodigo(String codigo){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Banio where codigo = :codigo");
        query.setParameter("codigo", codigo);
        return (Banio)query.uniqueResult();
    }

    public Cama getCamaByCodigo(String codigo){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Cama where codigoCama = :codigo");
        query.setParameter("codigo", codigo);
        return (Cama)query.uniqueResult();
    }

    public Cocina getCocinaByCodigo(String codigo){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Cocina where codigo = :codigo");
        query.setParameter("codigo", codigo);
        return (Cocina)query.uniqueResult();
    }

    public Comedor getComedorByCodigo(String codigo){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Comedor where codigo = :codigo");
        query.setParameter("codigo", codigo);
        return (Comedor)query.uniqueResult();
    }

    public Habitacion getHabitacionByCodigo(String codigo){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Habitacion where codigo = :codigo");
        query.setParameter("codigo", codigo);
        return (Habitacion)query.uniqueResult();
    }

    public Sala getSalaByCodigo(String codigo){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Sala where codigo = :codigo");
        query.setParameter("codigo", codigo);
        return (Sala)query.uniqueResult();
    }

    public Ventana getVentanaByCodigo(String codigo){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Ventana where codigo = :codigo");
        query.setParameter("codigo", codigo);
        return (Ventana)query.uniqueResult();
    }

    public void saveOrUpdateBanio(Banio banio)
    {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(banio);
    }

    public void saveOrUpdateCama(Cama cama)
    {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(cama);
    }

    public void saveOrUpdateCocina(Cocina cocina)
    {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(cocina);
    }

    public void saveOrUpdateComedor(Comedor comedor)
    {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(comedor);
    }

    public void saveOrUpdateHabitacion(Habitacion habitacion)
    {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(habitacion);
    }

    public void saveOrUpdateSala(Sala sala)
    {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(sala);
    }

    public void saveOrUpdateVentana(Ventana ventana)
    {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(ventana);
    }
}

