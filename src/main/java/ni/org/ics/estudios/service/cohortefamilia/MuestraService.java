package ni.org.ics.estudios.service.cohortefamilia;

import ni.org.ics.estudios.domain.cohortefamilia.Muestra;
import ni.org.ics.estudios.domain.cohortefamilia.Paxgene;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Miguel Salinas on 5/17/2017.
 * V1.0
 */
@Transactional
@Service("muestraService")
public class MuestraService {

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    public List<Muestra> getMuestras()
    {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Muestra where pasive = false ");
        return  query.list();
    }

    public List<Muestra> getMuestrasByUser(String username)
    {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Muestra ec where ec.pasive = false and ec.participanteCHF.casaCHF.casa.codigo in (" +
                "select cc.participante.casa.codigo from CartaConsentimiento cc where cc.tamizaje.estudio.codigo in (" +
                " select us.estudio.codigo from UserStudy us where us.usuario.username = :username))");
        query.setParameter("username",username);
        return  query.list();
    }

    public void saveOrUpdate(Muestra muestra){
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(muestra);
    }

    public List<Paxgene> getDatosPaxgene()
    {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Paxgene where pasive = false ");
        return  query.list();
    }

    public List<Paxgene> getDatosPaxgeneByUser(String username)
    {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("select px from Paxgene px where px.pasive = false and px.muestra.participanteCHF.casaCHF.casa.codigo in (" +
                "select cc.participante.casa.codigo from CartaConsentimiento cc where cc.tamizaje.estudio.codigo in (" +
                " select us.estudio.codigo from UserStudy us where us.usuario.username = :username))");
        query.setParameter("username",username);
        return  query.list();
    }

    public void saveOrUpdateDatosPaxgene(Paxgene paxgene){
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(paxgene);
    }
}