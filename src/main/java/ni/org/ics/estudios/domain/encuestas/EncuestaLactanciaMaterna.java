package ni.org.ics.estudios.domain.encuestas;

import ni.org.ics.estudios.domain.BaseMetaData;
import ni.org.ics.estudios.domain.Participante;
import ni.org.ics.estudios.domain.audit.Auditable;
import org.hibernate.annotations.ForeignKey;

import javax.persistence.*;

/**
 * Simple objeto de dominio que representa los datos de la lactancia materna
 * 
 * @author Brenda Lopez
 **/

@Entity
@Table(name = "encuestas_lactancia_materna", catalog = "estudios_ics")
public class EncuestaLactanciaMaterna extends BaseMetaData implements Auditable {

	/**
	 * 
	 */

    private Participante participante;
    private Integer edad;
	private Integer dioPecho;
	private Integer tiemPecho;
	private Integer mesDioPecho;
	private Integer pechoExc;
	private Integer pechoExcAntes;
	private Integer tiempPechoExcAntes;
	private Integer mestPechoExc;
	private Integer formAlim;
	private String otraAlim;
	private Integer edadLiqDistPecho;
	private Integer mesDioLiqDisPecho;
	private Integer edadLiqDistLeche;
	private Integer mesDioLiqDisLeche;
	private Integer edAlimSolidos;
	private Integer mesDioAlimSol;
	private Integer otrorecurso1;
	private Integer otrorecurso2;

    @Id
    @ManyToOne
    @JoinColumn(name = "CODIDO_PARTICIPANTE")
    @ForeignKey(name = "FK_PARTICIPANTE_LACTANCIAMAT")
    public Participante getParticipante() {
        return participante;
    }

    public void setParticipante(Participante participante) {
        this.participante = participante;
    }

	@Column(name = "EDAD", nullable = true, length = 2)
	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	@Column(name = "DIO_PECHO", nullable = true, length = 1)
	public Integer getDioPecho() {
		return dioPecho;
	}

	public void setDioPecho(Integer dioPecho) {
		this.dioPecho = dioPecho;
	}

	@Column(name = "TIEMPO_PECHO", nullable = true, length = 1)
	public Integer getTiemPecho() {
		return tiemPecho;
	}

	public void setTiemPecho(Integer tiemPecho) {
		this.tiemPecho = tiemPecho;
	}

	@Column(name = "MES_DIO_PECHO", nullable = true, length = 1)
	public Integer getMesDioPecho() {
		return mesDioPecho;
	}

	public void setMesDioPecho(Integer mesDioPecho) {
		this.mesDioPecho = mesDioPecho;
	}

	@Column(name = "PECHO_EXCLU", nullable = true, length = 1)
	public Integer getPechoExc() {
		return pechoExc;
	}

	public void setPechoExc(Integer pechoExc) {
		this.pechoExc = pechoExc;
	}

	@Column(name = "PECHO_EXCLU_ANTES", nullable = true, length = 1)
	public Integer getPechoExcAntes() {
		return pechoExcAntes;
	}

	public void setPechoExcAntes(Integer pechoExcAntes) {
		this.pechoExcAntes = pechoExcAntes;
	}

	@Column(name = "TIEMPO_PECHO_EXCLU_ANTES", nullable = true, length = 1)
	public Integer getTiempPechoExcAntes() {
		return tiempPechoExcAntes;
	}

	public void setTiempPechoExcAntes(Integer tiempPechoExcAntes) {
		this.tiempPechoExcAntes = tiempPechoExcAntes;
	}

	@Column(name = "MES_PECHO_EXCLU_ANTES", nullable = true)
	public Integer getMestPechoExc() {
		return mestPechoExc;
	}

	public void setMestPechoExc(Integer mestPechoExc) {
		this.mestPechoExc = mestPechoExc;
	}

	@Column(name = "FORMA_ALIMENTA", nullable = true, length = 1)
	public Integer getFormAlim() {
		return formAlim;
	}

	public void setFormAlim(Integer formAlim) {
		this.formAlim = formAlim;
	}

	@Column(name = "OTRA_ALIMENTACION", nullable = true, length = 50)
	public String getOtraAlim() {
		return otraAlim;
	}

	public void setOtraAlim(String otraAlim) {
		this.otraAlim = otraAlim;
	}

	@Column(name = "EDAD_LIQUIDO_DIST_PECHO", nullable = true)
	public Integer getEdadLiqDistPecho() {
		return edadLiqDistPecho;
	}

	public void setEdadLiqDistPecho(Integer edadLiqDistPecho) {
		this.edadLiqDistPecho = edadLiqDistPecho;
	}

	@Column(name = "MES_DIO_LIQ_DIST_PECHO", nullable = true)
	public Integer getMesDioLiqDisPecho() {
		return mesDioLiqDisPecho;
	}

	public void setMesDioLiqDisPecho(Integer mesDioLiqDisPecho) {
		this.mesDioLiqDisPecho = mesDioLiqDisPecho;
	}

	@Column(name = "EDAD_LIQUIDO_DIST_LECHE", nullable = true)
	public Integer getEdadLiqDistLeche() {
		return edadLiqDistLeche;
	}

	public void setEdadLiqDistLeche(Integer edadLiqDistLeche) {
		this.edadLiqDistLeche = edadLiqDistLeche;
	}
	
	@Column(name = "MES_DIO_LIQ_DIST_LECHE", nullable = true)
	public Integer getMesDioLiqDisLeche() {
		return mesDioLiqDisLeche;
	}

	public void setMesDioLiqDisLeche(Integer mesDioLiqDisLeche) {
		this.mesDioLiqDisLeche = mesDioLiqDisLeche;
	}

	@Column(name = "EDAD_ALIMEN_SOLIDOS", nullable = true)
	public Integer getEdAlimSolidos() {
		return edAlimSolidos;
	}

	public void setEdAlimSolidos(Integer edAlimSolidos) {
		this.edAlimSolidos = edAlimSolidos;
	}

	@Column(name = "MES_DIO_ALIMEN_SOLIDOS", nullable = true)
	public Integer getMesDioAlimSol() {
		return mesDioAlimSol;
	}

	public void setMesDioAlimSol(Integer mesDioAlimSol) {
		this.mesDioAlimSol = mesDioAlimSol;
	}

	@Column(name = "OTRO_RECURSO1", nullable = true, length = 10)
	public Integer getOtrorecurso1() {
		return otrorecurso1;
	}

	public void setOtrorecurso1(Integer otrorecurso1) {
		this.otrorecurso1 = otrorecurso1;
	}

	@Column(name = "OTRO_RECURSO2", nullable = true, length = 10)
	public Integer getOtrorecurso2() {
		return otrorecurso2;
	}

	public void setOtrorecurso2(Integer otrorecurso2) {
		this.otrorecurso2 = otrorecurso2;
	}

    @Override
    public boolean isFieldAuditable(String fieldname) {
        return true;
    }

    @Override
    public String toString() {
        return "EncuestaLactanciaMaterna{" + participante.getCodigo() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EncuestaLactanciaMaterna)) return false;

        EncuestaLactanciaMaterna that = (EncuestaLactanciaMaterna) o;

        return  (!participante.equals(that.participante));
    }

    @Override
    public int hashCode() {
        return participante.hashCode();
    }
}