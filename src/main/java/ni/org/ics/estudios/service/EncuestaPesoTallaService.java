package ni.org.ics.estudios.service;

import ni.org.ics.estudios.domain.encuestas.EncuestaPesoTalla;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Miguel Salinas on 5/5/2017.
 * V1.0
 */
@Transactional
@Service("encuestaPesoTallaService")
public class EncuestaPesoTallaService {

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public List<EncuestaPesoTalla> getEncuestasPesoTalla(){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from EncuestaPesoTalla");
        return query.list();
    }

    public EncuestaPesoTalla getEncuestaPesoTalla(Integer codigo){
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from EncuestaPesoTalla where participante.codigo = :codigo");
        query.setParameter("codigo", codigo);
        return (EncuestaPesoTalla)query.uniqueResult();
    }

    public void saveOrUpdateEncuestaPesoTalla(EncuestaPesoTalla encuestaPesoTalla){
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(encuestaPesoTalla);
    }
}
