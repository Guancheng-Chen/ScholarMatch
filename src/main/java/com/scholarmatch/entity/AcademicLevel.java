package com.scholarmatch.entity;

/**
 * Represents the academic career stage of a registered User.
 *
 * <p>Used as a typed field on User in place of a raw String
 * to prevent invalid values and enable exhaustive switch handling.
 */
public enum AcademicLevel {


    /**
     * A student working toward a bachelor's degree.
     */
    UNDERGRADUATE,

    /**
     * A student working toward a master's or doctoral degree.
     */
    GRADUATE_STUDENT,

    /**
     * A researcher who has completed a PhD and holds a temporary
     * research position.
     */
    POSTDOCTORAL_RESEARCHER,

    /**
     * A permanent academic staff member at a university or research
     * institute.
     */
    FACULTY,

    /**
     * A researcher working in industry rather than academia.
     */
    INDUSTRY_RESEARCHER,
}
