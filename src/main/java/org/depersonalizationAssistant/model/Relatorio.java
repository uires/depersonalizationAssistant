package org.depersonalizationAssistant.model;

public class Relatorio {
	private Long id;
	private Long idPaciente;
	private Long idPatologia;
	private String titulo;
	private Patologia patologia;
	private boolean publico;
	private String descricao;

	public Relatorio() {

	}

	public Relatorio(Long id, Long idPaciente, Patologia patologia, String descricao) {
		this.id = id;
		this.idPaciente = idPaciente;
		this.patologia = patologia;
		this.descricao = descricao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Long getIdPaciente() {
		return idPaciente;
	}

	public void setIdPaciente(Long idPaciente) {
		this.idPaciente = idPaciente;
	}

	public Patologia getPatologia() {
		return patologia;
	}

	public void setPatologia(Patologia patologia) {
		this.patologia = patologia;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getIdPatologia() {
		return idPatologia;
	}

	public void setIdPatologia(Long idPatologia) {
		this.idPatologia = idPatologia;
	}

	public boolean isPublico() {
		return publico;
	}

	public void setPublico(boolean publico) {
		this.publico = publico;
	}

}
