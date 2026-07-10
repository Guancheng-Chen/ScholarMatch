package com.scholarmatch.entity;

/**
 * Broad academic or industry discipline that best describes a {@link User}'s primary
 * research focus.
 *
 * <p>This is a coarse, single-select classification meant for filtering and display —
 * distinct from {@link User#getResearchInterests()}, which holds free-form, fine-grained
 * keywords. Deliberately broad enough to cover the large majority of real-world researchers;
 * anything that doesn't fit cleanly elsewhere is captured by {@link #OTHER}.
 */
public enum ResearchField {

    /**
     * Computer science in general, not covered by a more specific sub-field below.
     */
    COMPUTER_SCIENCE,

    /**
     * Artificial intelligence, including planning, search, and multi-agent systems.
     */
    ARTIFICIAL_INTELLIGENCE,

    /**
     * Machine learning, including deep learning and statistical learning theory.
     */
    MACHINE_LEARNING,

    /**
     * Data science, including data mining, analytics, and applied data engineering.
     */
    DATA_SCIENCE,

    /**
     * Statistics, including probability theory and experimental design.
     */
    STATISTICS,

    /**
     * Computer vision and image/video understanding.
     */
    COMPUTER_VISION,

    /**
     * Natural language processing and computational linguistics.
     */
    NATURAL_LANGUAGE_PROCESSING,

    /**
     * Robotics, including manipulation, motion planning, and autonomous systems.
     */
    ROBOTICS,

    /**
     * Human-computer interaction and user experience research.
     */
    HUMAN_COMPUTER_INTERACTION,

    /**
     * Cybersecurity, cryptography, and information security.
     */
    CYBERSECURITY,

    /**
     * Distributed systems, computer networking, and cloud computing.
     */
    DISTRIBUTED_SYSTEMS_NETWORKING,

    /**
     * Software engineering, programming languages, and software architecture.
     */
    SOFTWARE_ENGINEERING,

    /**
     * Bioinformatics and computational biology.
     */
    BIOINFORMATICS_COMPUTATIONAL_BIOLOGY,

    /**
     * Quantum computing and quantum information science.
     */
    QUANTUM_COMPUTING,

    /**
     * Information science and library science.
     */
    INFORMATION_SCIENCE_LIBRARY_SCIENCE,

    /**
     * Pure mathematics, including algebra, analysis, topology, and number theory.
     */
    MATHEMATICS,

    /**
     * Applied mathematics, including numerical methods and mathematical modeling.
     */
    APPLIED_MATHEMATICS,

    /**
     * Physics in general, including theoretical, experimental, and condensed matter physics.
     */
    PHYSICS,

    /**
     * Astrophysics and astronomy.
     */
    ASTROPHYSICS_ASTRONOMY,

    /**
     * Chemistry in general, including organic and inorganic chemistry.
     */
    CHEMISTRY,

    /**
     * Physical chemistry and chemical physics.
     */
    PHYSICAL_CHEMISTRY,

    /**
     * Materials science and engineering.
     */
    MATERIALS_SCIENCE,

    /**
     * Nanotechnology and nanoscale science and engineering.
     */
    NANOTECHNOLOGY,

    /**
     * Nuclear science and nuclear engineering.
     */
    NUCLEAR_SCIENCE_ENGINEERING,

    /**
     * Earth sciences and geology.
     */
    EARTH_SCIENCES_GEOLOGY,

    /**
     * Atmospheric science and climate science.
     */
    ATMOSPHERIC_CLIMATE_SCIENCE,

    /**
     * Oceanography and marine science.
     */
    OCEANOGRAPHY_MARINE_SCIENCE,

    /**
     * Environmental science in general.
     */
    ENVIRONMENTAL_SCIENCE,

    /**
     * Sustainability studies and energy systems research.
     */
    SUSTAINABILITY_ENERGY_SYSTEMS,

    /**
     * Ecology and evolutionary biology.
     */
    ECOLOGY_EVOLUTIONARY_BIOLOGY,

    /**
     * Biology in general, not covered by a more specific sub-field below.
     */
    BIOLOGY,

    /**
     * Molecular biology and cell biology.
     */
    MOLECULAR_CELL_BIOLOGY,

    /**
     * Genetics and genomics.
     */
    GENETICS_GENOMICS,

    /**
     * Microbiology and immunology.
     */
    MICROBIOLOGY_IMMUNOLOGY,

    /**
     * Neuroscience, including cognitive and computational neuroscience.
     */
    NEUROSCIENCE,

    /**
     * Physiology and related basic medical sciences.
     */
    PHYSIOLOGY,

    /**
     * Biomedical engineering.
     */
    BIOMEDICAL_ENGINEERING,

    /**
     * Medicine and clinical research.
     */
    MEDICINE_CLINICAL_RESEARCH,

    /**
     * Public health and epidemiology.
     */
    PUBLIC_HEALTH_EPIDEMIOLOGY,

    /**
     * Pharmacology and pharmacy.
     */
    PHARMACOLOGY_PHARMACY,

    /**
     * Nursing.
     */
    NURSING,

    /**
     * Dentistry and oral health research.
     */
    DENTISTRY,

    /**
     * Veterinary science.
     */
    VETERINARY_SCIENCE,

    /**
     * Nutrition science and food science.
     */
    NUTRITION_FOOD_SCIENCE,

    /**
     * Agricultural science.
     */
    AGRICULTURAL_SCIENCE,

    /**
     * Electrical and electronic engineering.
     */
    ELECTRICAL_ENGINEERING,

    /**
     * Mechanical engineering.
     */
    MECHANICAL_ENGINEERING,

    /**
     * Civil engineering.
     */
    CIVIL_ENGINEERING,

    /**
     * Chemical engineering.
     */
    CHEMICAL_ENGINEERING,

    /**
     * Aerospace engineering.
     */
    AEROSPACE_ENGINEERING,

    /**
     * Industrial engineering and systems engineering.
     */
    INDUSTRIAL_SYSTEMS_ENGINEERING,

    /**
     * Environmental engineering.
     */
    ENVIRONMENTAL_ENGINEERING,

    /**
     * Economics.
     */
    ECONOMICS,

    /**
     * Political science.
     */
    POLITICAL_SCIENCE,

    /**
     * Sociology.
     */
    SOCIOLOGY,

    /**
     * Anthropology.
     */
    ANTHROPOLOGY,

    /**
     * Psychology.
     */
    PSYCHOLOGY,

    /**
     * Cognitive science.
     */
    COGNITIVE_SCIENCE,

    /**
     * Geography.
     */
    GEOGRAPHY,

    /**
     * Demography and population studies.
     */
    DEMOGRAPHY_POPULATION_STUDIES,

    /**
     * International relations.
     */
    INTERNATIONAL_RELATIONS,

    /**
     * Criminology.
     */
    CRIMINOLOGY,

    /**
     * Business administration and management.
     */
    BUSINESS_MANAGEMENT,

    /**
     * Finance.
     */
    FINANCE,

    /**
     * Accounting.
     */
    ACCOUNTING,

    /**
     * Marketing.
     */
    MARKETING,

    /**
     * Entrepreneurship and innovation studies.
     */
    ENTREPRENEURSHIP_INNOVATION,

    /**
     * Law and legal studies.
     */
    LAW,

    /**
     * Public policy and public administration.
     */
    PUBLIC_POLICY_ADMINISTRATION,

    /**
     * Education and pedagogy research.
     */
    EDUCATION,

    /**
     * Linguistics.
     */
    LINGUISTICS,

    /**
     * Philosophy.
     */
    PHILOSOPHY,

    /**
     * History.
     */
    HISTORY,

    /**
     * Literature and language studies.
     */
    LITERATURE_LANGUAGES,

    /**
     * Religious studies and theology.
     */
    RELIGIOUS_STUDIES_THEOLOGY,

    /**
     * Cultural studies and media studies.
     */
    CULTURAL_STUDIES,

    /**
     * Art and design.
     */
    ART_DESIGN,

    /**
     * Music, including composition and musicology.
     */
    MUSIC,

    /**
     * Architecture and urban planning.
     */
    ARCHITECTURE_URBAN_PLANNING,

    /**
     * A research field that doesn't fit cleanly into any of the categories above.
     */
    OTHER,
}
