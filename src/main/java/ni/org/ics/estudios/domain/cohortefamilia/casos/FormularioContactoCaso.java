package ni.org.ics.estudios.domain.cohortefamilia.casos;

import java.util.Date;

import ni.org.ics.estudios.domain.BaseMetaData;
import ni.org.ics.estudios.domain.audit.Auditable;
import ni.org.ics.estudios.domain.cohortefamilia.ParticipanteCohorteFamilia;

import javax.persistence.*;

import org.hibernate.annotations.ForeignKey;

/**
 * Created by William Aviles on 6/07/2017.
 * V1.0
 */
@Entity
@Table(name = "chf_contactos_casos", catalog = "estudios_ics")
public class FormularioContactoCaso extends BaseMetaData implements Auditable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigoCasoContacto;
	private ParticipanteCohorteFamiliaCaso codigoParticipanteCaso;
	private Date fechaVisita;
	private ParticipanteCohorteFamilia partContacto;
	private String tiempoInteraccion;
	private String tipoInteraccion;
    
	@Id
    @Column(name = "CODIGO_VISITA_CASO", length = 50, nullable = false)
	public String getCodigoCasoContacto() {
		return codigoCasoContacto;
	}

	public void setCodigoCasoContacto(String codigoCasoContacto) {
		this.codigoCasoContacto = codigoCasoContacto;
	}

	@ManyToOne
    @JoinColumn(name = "CODIGO_PARTICIPANTE_CASO", nullable = false)
    @ForeignKey(name = "FK_CONTACTO_CASO")
	public ParticipanteCohorteFamiliaCaso getCodigoParticipanteCaso() {
		return codigoParticipanteCaso;
	}

	public void setCodigoParticipanteCaso(ParticipanteCohorteFamiliaCaso codigoParticipanteCaso) {
		this.codigoParticipanteCaso = codigoParticipanteCaso;
	}
	
	@Column(name = "FECHA_VISITA", nullable = false)
	public Date getFechaVisita() {
		return fechaVisita;
	}

	public void setFechaVisita(Date fechaVisita) {
		this.fechaVisita = fechaVisita;
	}

	@ManyToOne
    @JoinColumn(name = "CODIGO_PARTICIPANTE", referencedColumnName = "CODIGO_PARTICIPANTE", nullable = false)
    @ForeignKey(name = "FK_CONTACTO_PARTICIPANTE")
	public ParticipanteCohorteFamilia getPartContacto() {
		return partContacto;
	}

	public void setPartContacto(ParticipanteCohorteFamilia partContacto) {
		this.partContacto = partContacto;
	}

	@Column(name = "TIEMPO_INTERACCION", length = 10, nullable = false)
	public String getTiempoInteraccion() {
		return tiempoInteraccion;
	}

	public void setTiempoInteraccion(String tiempoInteraccion) {
		this.tiempoInteraccion = tiempoInteraccion;
	}

	@Column(name = "TIPO_INTERACCION", length = 100, nullable = false)
	public String getTipoInteraccion() {
		return tipoInteraccion;
	}

	public void setTipoInteraccion(String tipoInteraccion) {
		this.tipoInteraccion = tipoInteraccion;
	}

	@Override
	public String toString(){
		return codigoParticipanteCaso.getCodigoCaso().getCasa().getCodigoCHF() + "-" + codigoParticipanteCaso.getParticipante().getParticipante().getCodigo() + "-" + codigoParticipanteCaso.getCodigoCaso().getFechaInicio();
	}
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FormularioContactoCaso)) return false;

        FormularioContactoCaso that = (FormularioContactoCaso) o;

        if (!codigoCasoContacto.equals(that.codigoCasoContacto)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = codigoCasoContacto.hashCode();
        result = 31 * result + codigoParticipanteCaso.getParticipante().hashCode();
        return result;
    }

	@Override
	public boolean isFieldAuditable(String fieldname) {
		// TODO Auto-generated method stub
		return true;
	}
}
