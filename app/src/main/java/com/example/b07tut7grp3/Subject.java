package com.example.b07tut7grp3;

@SuppressWarnings("unused")
/**
 * An enumeration of all possible subjects at UTSC
 * @author Kevin Li
 * @since 0.1
 */
public enum Subject{
    AFRICAN_STUDIES("African Studies"),
    ANTHROPOLOGY("Anthropology"),
    APPLIED_MICROBIOLOGY("Applied Microbiology"),
    ARTS_CULTURE_MEDIA("Arts, Culture, and Media"),
    ARTS_MANAGEMENT("Arts Management"),
    ART_HISTORY("Art History"),
    ASTRONOMY("Astronomy"),
    BIOLOGICAL_SCIENCES("Biological Sciences"),
    CHEMISTRY("Chemistry"),
    CITY_STUDIES("City Studies"),
    CLASSICAL_STUDIES("Classical Studies"),
    COGNITIVE_SCIENCE("Cognitive Science"),
    COMPUTER_SCIENCE("Computer Science"),
    CONCURRENT_TEACHER_EDUCATION("Concurrent Teacher Education"),
    DIASPORA_TRANSNATIONAL_STUDIES("Diaspora and Transnational Studies"),
    ECONOMICS_FOR_MANAGEMENT_STUDIES("Economics for Management Studies"),
    ENGLISH("English"),
    ENVIRONMENTAL_SCIENCE("Environmental Science"),
    ENVIRONMENTAL_SCIENCE_AND_TECHNOLOGY("Environmental Science and Technology"),
    ENVIRONMENTAL_STUDIES("Environmental Studies"),
    FRENCH("French"),
    GEOGRAPHY("Geography"),
    GLOBAL_ASIA_STUDIES("Global Asia Studies"),
    HEALTH_STUDIES("Health Studies"),
    HISTORICAL_CULTURAL_STUDIES("Historical and Cultural Studies"),
    HISTORY("History"),
    INTERNATIONAL_DEVELOPMENT_STUDIES("International Development Studies"),
    INTERNATIONAL_STUDIES("International Studies"),
    INTERSECTIONS_EXCHANGES_ENCOUNTERS("Intersections, Exchanges, and Encounters"),
    JOURNALISM("Journalism"),
    LANGUAGES("Languages"),
    LINGUISTICS("Linguistics"),
    MANAGEMENT("Management"),
    MATHEMATICS("Mathematics"),
    MEDIA_STUDIES("Media Studies"),
    MUSIC_AND_CULTURE("Music and Culture"),
    NEUROSCIENCE("Neuroscience"),
    NEW_MEDIA_STUDIES("New Media Studies"),
    PARAMEDICINE("Paramedicine"),
    PHYSICAL_SCIENCES("Physical Sciences"),
    PHYSICS_AND_ASTROPHYSICS("Physics and Astrophysics"),
    POLITICAL_SCIENCE("Political Science"),
    PSYCHOLOGY("Psychology"),
    RELIGION("Religion"),
    SOCIETY_AND_ENVIRONMENT("Society and Environment"),
    SOCIOLOGY("Sociology"),
    STATISTICS("Statistics"),
    STUDIO("Studio"),
    TEACHING_AND_LEARNING("Teaching and Learning"),
    THEATRE_AND_PERFORMANCE_STUDIES("Theatre and Performance Studies"),
    VISUAL_AND_PERFORMING_ARTS("Visual and Performing Arts"),
    WOMENS_AND_GENDER_STUDIES("Women's and Gender Studies");

    private final String subject;
    Subject(String subject){
        this.subject = subject;
    }

    @Override
    public String toString(){
        return this.subject;
    }


    public static Subject getProgram(String postname) {
        if ("African Studies".equals(postname)) { return AFRICAN_STUDIES; }
        else if ("Anthropology".equals(postname)) { return ANTHROPOLOGY; }
        else if ("Applied Microbiology".equals(postname)) { return APPLIED_MICROBIOLOGY; }
        else if ("Arts, Culture, and Media".equals(postname)) { return ARTS_CULTURE_MEDIA; }
        else if ("Arts Management".equals(postname)) { return ARTS_MANAGEMENT; }
        else if ("Art History".equals(postname)) { return ART_HISTORY; }
        else if ("Astronomy".equals(postname)) { return ASTRONOMY; }
        else if ("Biological Sciences".equals(postname)) { return BIOLOGICAL_SCIENCES; }
        else if ("Chemistry".equals(postname)) { return CHEMISTRY; }
        else if ("City Studies".equals(postname)) { return CITY_STUDIES; }
        else if ("Classical Studies".equals(postname)) { return CLASSICAL_STUDIES; }
        else if ("Cognitive Science".equals(postname)) { return COGNITIVE_SCIENCE; }
        else if ("Computer Science".equals(postname)) { return COMPUTER_SCIENCE; }
        else if ("Concurrent Teacher Education".equals(postname)) { return CONCURRENT_TEACHER_EDUCATION; }
        else if ("Diaspora and Transnational Studies".equals(postname)) { return DIASPORA_TRANSNATIONAL_STUDIES; }
        else if ("Economics for Management Studies".equals(postname)) { return ECONOMICS_FOR_MANAGEMENT_STUDIES; }
        else if ("English".equals(postname)) { return ENGLISH; }
        else if ("Environmental Science".equals(postname)) { return ENVIRONMENTAL_SCIENCE; }
        else if ("Environmental Science and Technology".equals(postname)) { return ENVIRONMENTAL_SCIENCE_AND_TECHNOLOGY; }
        else if ("Environmental Studies".equals(postname)) { return ENVIRONMENTAL_STUDIES; }
        else if ("French".equals(postname)) { return FRENCH; }
        else if ("Geography".equals(postname)) { return GEOGRAPHY; }
        else if ("Global Asia Studies".equals(postname)) { return GLOBAL_ASIA_STUDIES; }
        else if ("Health Studies".equals(postname)) { return HEALTH_STUDIES; }
        else if ("Historical and Cultural Studies".equals(postname)) { return HISTORICAL_CULTURAL_STUDIES; }
        else if ("History".equals(postname)) { return HISTORY; }
        else if ("International Development Studies".equals(postname)) { return INTERNATIONAL_DEVELOPMENT_STUDIES; }
        else if ("International Studies".equals(postname)) { return INTERNATIONAL_STUDIES; }
        else if ("Intersections, Exchanges, and Encounters".equals(postname)) { return INTERSECTIONS_EXCHANGES_ENCOUNTERS; }
        else if ("Journalism".equals(postname)) { return JOURNALISM; }
        else if ("Languages".equals(postname)) { return LANGUAGES; }
        else if ("Linguistics".equals(postname)) { return LINGUISTICS; }
        else if ("Management".equals(postname)) { return MANAGEMENT; }
        else if ("Mathematics".equals(postname)) { return MATHEMATICS; }
        else if ("Media Studies".equals(postname)) { return MEDIA_STUDIES; }
        else if ("Music and Culture".equals(postname)) { return MUSIC_AND_CULTURE; }
        else if ("Neuroscience".equals(postname)) { return NEUROSCIENCE; }
        else if ("New Media Studies".equals(postname)) { return NEW_MEDIA_STUDIES; }
        else if ("Paramedicine".equals(postname)) { return PARAMEDICINE; }
        else if ("Physical Sciences".equals(postname)) { return PHYSICAL_SCIENCES; }
        else if ("Physics and Astrophysics".equals(postname)) { return PHYSICS_AND_ASTROPHYSICS; }
        else if ("Political Science".equals(postname)) { return POLITICAL_SCIENCE; }
        else if ("Psychology".equals(postname)) { return PSYCHOLOGY; }
        else if ("Religion".equals(postname)) { return RELIGION; }
        else if ("Society and Environment".equals(postname)) { return SOCIETY_AND_ENVIRONMENT; }
        else if ("Sociology".equals(postname)) { return SOCIOLOGY; }
        else if ("Statistics".equals(postname)) { return STATISTICS; }
        else if ("Studio".equals(postname)) { return STUDIO; }
        else if ("Teaching and Learning".equals(postname)) { return TEACHING_AND_LEARNING; }
        else if ("Theatre and Performance Studies".equals(postname)) { return THEATRE_AND_PERFORMANCE_STUDIES; }
        else if ("Visual and Performing Arts".equals(postname)) { return VISUAL_AND_PERFORMING_ARTS; }
        else { return WOMENS_AND_GENDER_STUDIES; }
    }
}
