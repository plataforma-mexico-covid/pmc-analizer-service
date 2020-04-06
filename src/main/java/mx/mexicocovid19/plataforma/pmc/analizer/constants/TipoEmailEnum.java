package mx.mexicocovid19.plataforma.pmc.analizer.constants;

public enum TipoEmailEnum {
    OFRECE_AYUDA_REMINDER("email/ofreceAyuda.vm", "Seguimos buscando personas con NECESIDADES a tu alrededor!"),
    SOLICITA_AYUDA_REMINDER("email/solicitaAyuda.vm", "Seguimos buscando personas GENEROSOS a tu alrededor!");
    
    private String template;
    private String subject;

    TipoEmailEnum(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }

    public String getTemplate() {
        return this.template;
    }

    public String getSubject() {
        return this.subject;
    }
}