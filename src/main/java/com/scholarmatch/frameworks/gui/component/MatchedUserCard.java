package com.scholarmatch.frameworks.gui.component;

import com.scholarmatch.entity.Education;
import com.scholarmatch.entity.Publication;
import com.scholarmatch.frameworks.gui.style.Format;
import com.scholarmatch.frameworks.gui.style.Icons;
import com.scholarmatch.frameworks.gui.style.Reflowable;
import com.scholarmatch.frameworks.gui.style.RoundedPanel;
import com.scholarmatch.frameworks.gui.style.Theme;
import com.scholarmatch.usecase.dto.UserData;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

/**
 * Card widget that displays a single matched user's full profile, including
 * every field on {@link UserData} — unlike {@link RecommendUserCard}, a mutual
 * match is exactly the point at which contact info (email, phone) becomes
 * relevant for reaching out.
 *
 * <p>Landscape layout, sized to match the proportions of {@link RecommendUserCard}
 * so both card types make full use of the app's default frame width instead of
 * sitting in a narrow column: name/avatar/vitals across the top, then three
 * columns below — Education (with full date ranges), Publications (with DOI and
 * citation counts), and Research (description, interests, collaboration note).
 *
 * <p>Implements {@link Reflowable}: CenteringScrollPanel calls
 * {@link #reflow(int)} whenever the scroll viewport is resized, so the card shrinks with
 * a narrow window instead of being clipped, and drops the three columns to a single
 * vertically-stacked column once too narrow to show them side by side.
 *
 * <p>Deliberately independent of {@link RecommendUserCard}: the two cards show
 * different fields and this one has no action buttons, so no rendering code is
 * shared between them. Used in
 * com.scholarmatch.frameworks.gui.view.LoadMatchesView as one entry in the
 * scrollable stack of matched-user cards.
 */
public final class MatchedUserCard extends RoundedPanel implements Reflowable {

    private static final int MAX_CARD_WIDTH = 1040;
    private static final int MIN_CARD_WIDTH = 360;
    private static final int STACK_BREAKPOINT = 720;
    private static final int AVATAR_SIZE = 84;
    private static final String NOT_SPECIFIED = "Not specified";

    private final UserData cardUser;

    /**
     * Constructs a MatchedUserCard.
     *
     * @param user the matched user whose profile this card displays
     */
    public MatchedUserCard(final UserData user) {
        super(Theme.SCHOLAR_CARD_RADIUS, 24);
        this.cardUser = user;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.LEFT_ALIGNMENT);
        reflow(MAX_CARD_WIDTH);
    }

    @Override
    public void reflow(final int width) {
        final int cardWidth = Math.max(MIN_CARD_WIDTH, Math.min(MAX_CARD_WIDTH, width));
        removeAll();
        setMaximumSize(new Dimension(cardWidth, Integer.MAX_VALUE));
        add(buildHeader(this.cardUser, cardWidth));
        add(strut(14));
        add(buildColumns(this.cardUser, cardWidth));
        revalidate();
        repaint();
    }

    private JPanel buildHeader(final UserData user, final int cardWidth) {
        final CircleAvatar avatar = new CircleAvatar(initial(user.getFirstName()) + initial(user.getLastName()));

        final JLabel nameLabel = new JLabel(user.getFirstName() + " " + user.getLastName());
        nameLabel.setForeground(Theme.FG_DEFAULT);
        nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD, 26f));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        final JLabel subtitleLabel = new JLabel(joinNonBlank(" · ",
                displayOr(user.getInstitution() == null ? null : user.getInstitution().getDisplayName()),
                formatEnum(user.getAcademicLevel())));
        subtitleLabel.setForeground(Theme.FG_MUTED);
        subtitleLabel.setFont(subtitleLabel.getFont().deriveFont(16f));
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        final JLabel contactLabel = new JLabel(joinNonBlank("    ",
                "Email: " + displayOr(user.getEmail()),
                "Phone: " + displayOr(user.getPhoneNumber())));
        contactLabel.setForeground(Theme.ACCENT_FG);
        contactLabel.setFont(contactLabel.getFont().deriveFont(Font.BOLD, 14f));
        contactLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        final JLabel collabInfoLabel = new JLabel(joinNonBlank("    ",
                "Field: " + formatEnum(user.getResearchField()),
                "Looking for: " + formatEnum(user.getLookingFor()),
                "Funding: " + formatEnum(user.getFundingStatus())));
        collabInfoLabel.setForeground(Theme.FG_MUTED);
        collabInfoLabel.setFont(collabInfoLabel.getFont().deriveFont(14f));
        collabInfoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        final JLabel availabilityLabel = new JLabel(
                "Availability: " + Format.stat(user.getWeeklyAvailabilityHours())
                        + (user.getWeeklyAvailabilityHours() == null ? "" : " h/wk"));
        availabilityLabel.setForeground(Theme.FG_MUTED);
        availabilityLabel.setFont(availabilityLabel.getFont().deriveFont(14f));
        availabilityLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        final JLabel statsLabel = new JLabel(
                "h-index: " + Format.stat(user.gethIndex()) + "    Citations: " + Format.stat(user.getTotalCitations()));
        statsLabel.setForeground(Theme.FG_DEFAULT);
        statsLabel.setFont(statsLabel.getFont().deriveFont(Font.BOLD, 14f));
        statsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        final JPanel textBlock = new JPanel();
        textBlock.setLayout(new BoxLayout(textBlock, BoxLayout.Y_AXIS));
        textBlock.setOpaque(false);
        textBlock.setBorder(new EmptyBorder(0, 16, 0, 0));
        textBlock.add(nameLabel);
        textBlock.add(strut(4));
        textBlock.add(subtitleLabel);
        textBlock.add(strut(6));
        textBlock.add(contactLabel);
        textBlock.add(strut(3));
        textBlock.add(collabInfoLabel);
        textBlock.add(strut(3));
        textBlock.add(availabilityLabel);
        textBlock.add(strut(6));
        textBlock.add(statsLabel);

        // BorderLayout.WEST stretches its child to the row's full height, which would squash
        // the circular avatar into an oval since the text block next to it is tall — wrap it
        // in a non-stretching FlowLayout cell so it keeps its fixed size.
        final JPanel avatarWrapper = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));
        avatarWrapper.setOpaque(false);
        avatarWrapper.add(avatar);

        final JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        header.add(avatarWrapper, BorderLayout.WEST);
        header.add(textBlock, BorderLayout.CENTER);
        header.setMaximumSize(new Dimension(cardWidth, header.getPreferredSize().height));
        return header;
    }

    private JPanel buildColumns(final UserData user, final int cardWidth) {
        final boolean stacked = cardWidth < STACK_BREAKPOINT;
        final int columnWidth = stacked ? cardWidth : (cardWidth - 48 - 40) / 3;

        final JPanel educationColumn = buildEducationColumn(user.getEducations(), columnWidth);
        final JPanel publicationsColumn = buildPublicationsColumn(user.getPublications(), columnWidth);
        final JPanel researchColumn = buildResearchColumn(user, columnWidth);

        if (stacked) {
            final JPanel columns = new JPanel();
            columns.setLayout(new BoxLayout(columns, BoxLayout.Y_AXIS));
            columns.setOpaque(false);
            columns.setAlignmentX(Component.LEFT_ALIGNMENT);
            columns.setMaximumSize(new Dimension(cardWidth, Integer.MAX_VALUE));
            columns.add(educationColumn);
            columns.add(publicationsColumn);
            columns.add(researchColumn);
            return columns;
        }

        final JPanel columns = new JPanel(new GridLayout(1, 3, 20, 0));
        columns.setOpaque(false);
        columns.setAlignmentX(Component.LEFT_ALIGNMENT);
        columns.setMaximumSize(new Dimension(cardWidth, Integer.MAX_VALUE));
        columns.add(educationColumn);
        columns.add(publicationsColumn);
        columns.add(researchColumn);
        return columns;
    }

    private JPanel buildEducationColumn(final List<Education> educations, final int columnWidth) {
        final JPanel panel = column("EDUCATION", FontAwesomeSolid.GRADUATION_CAP);
        if (educations.isEmpty()) {
            panel.add(mutedLine("None on file"));
        } else {
            for (final Education ed : educations) {
                panel.add(wrappedLabel(ed.getInstitution() + " — " + formatEnum(ed.getDegreeType()),
                        columnWidth));
                panel.add(strut(2));
                panel.add(mutedLine(formatEducationRange(ed)));
                panel.add(strut(8));
            }
        }
        return panel;
    }

    private JPanel buildPublicationsColumn(final List<Publication> publications, final int columnWidth) {
        final JPanel panel = column("PUBLICATIONS", FontAwesomeSolid.BOOK);
        if (publications.isEmpty()) {
            panel.add(mutedLine("None on file"));
        } else {
            for (final Publication publication : publications) {
                panel.add(wrappedLabel(
                        publication.getYear() > 0
                                ? publication.getTitle() + " (" + publication.getYear() + ")"
                                : publication.getTitle(),
                        columnWidth));
                panel.add(strut(2));
                panel.add(mutedLine(joinNonBlank("  ·  ",
                        "Citations: " + publication.getCitationCount(),
                        publication.getDoi().isBlank() ? "" : "DOI: " + publication.getDoi())));
                panel.add(strut(8));
            }
        }
        return panel;
    }

    private JPanel buildResearchColumn(final UserData user, final int columnWidth) {
        final JPanel panel = column("RESEARCH DESCRIPTION", FontAwesomeSolid.FLASK);
        panel.add(wrappedLabel(displayOr(user.getResearchDescription()), columnWidth));
        panel.add(strut(10));
        panel.add(columnHeading("RESEARCH INTERESTS", FontAwesomeSolid.TAGS));
        panel.add(wrappedLabel(
                user.getResearchInterests().isEmpty() ? NOT_SPECIFIED : String.join(", ", user.getResearchInterests()),
                columnWidth));
        panel.add(strut(10));
        panel.add(columnHeading("COLLABORATION DESCRIPTION", FontAwesomeSolid.HANDSHAKE));
        panel.add(wrappedLabel(displayOr(user.getCollaborationDescription()), columnWidth));
        return panel;
    }

    private JPanel column(final String heading, final Ikon glyph) {
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, Theme.BORDER_MUTED),
                new EmptyBorder(10, 0, 0, 0)));
        panel.add(columnHeading(heading, glyph));
        panel.add(strut(8));
        return panel;
    }

    private JLabel columnHeading(final String text, final Ikon glyph) {
        final JLabel label = new JLabel(text, Icons.of(glyph, 13, Theme.FG_SUBTLE), JLabel.LEFT);
        label.setIconTextGap(6);
        label.setForeground(Theme.FG_SUBTLE);
        label.setFont(label.getFont().deriveFont(Font.BOLD, 13f));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        capHeight(label);
        return label;
    }

    private JLabel mutedLine(final String text) {
        final JLabel label = new JLabel(text);
        label.setForeground(Theme.FG_SUBTLE);
        label.setFont(label.getFont().deriveFont(Font.PLAIN, 12.5f));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        capHeight(label);
        return label;
    }

    /**
     * Caps a label's maximum height to its own preferred height. Without this, BoxLayout
     * treats the JLabel default (unbounded) maximum as "willing to stretch" and inflates it
     * to fill whatever leftover space GridLayout gives the tallest sibling column.
     */
    private void capHeight(final JLabel label) {
        label.setMaximumSize(new Dimension(Integer.MAX_VALUE, label.getPreferredSize().height));
    }

    private JLabel wrappedLabel(final String text, final int columnWidth) {
        // FlatLaf's HTML view measures noticeably wider than the declared CSS width (roughly
        // 1.3x), so the declared value here is well under the target width — otherwise text
        // overflows the label's bounds and gets clipped instead of wrapping.
        final JLabel label = new JLabel(
                "<html><body style='width:" + (int) (columnWidth / 1.3) + "px'>" + escapeHtml(text) + "</body></html>");
        label.setForeground(Theme.FG_DEFAULT);
        label.setFont(label.getFont().deriveFont(14f));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setMaximumSize(new Dimension(columnWidth, label.getPreferredSize().height));
        return label;
    }

    private String formatEducationRange(final Education ed) {
        final String start = formatYearMonth(ed.getStartYear(), ed.getStartMonth());
        final String end = ed.isOngoing() ? "Present" : formatYearMonth(ed.getEndYear(), ed.getEndMonth());
        return start + " – " + end;
    }

    private String formatYearMonth(final int year, final Month month) {
        if (year <= 0) {
            return "Unknown";
        }
        return (month == null ? "" : month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + " ") + year;
    }

    /**
     * Returns the first character of a name for the avatar's initials, or "?" if the name
     * is null/blank — a bare {@code name.substring(0, 1)} throws
     * StringIndexOutOfBoundsException for an empty string.
     */
    private String initial(final String name) {
        return name == null || name.isBlank() ? "?" : name.substring(0, 1);
    }

    private String displayOr(final String value) {
        return value == null || value.isBlank() ? NOT_SPECIFIED : value;
    }

    private String joinNonBlank(final String separator, final String... parts) {
        final StringBuilder result = new StringBuilder();
        for (final String part : parts) {
            if (part == null || part.isBlank()) {
                continue;
            }
            if (result.length() > 0) {
                result.append(separator);
            }
            result.append(part);
        }
        return result.toString();
    }

    private String formatEnum(final Enum<?> value) {
        if (value == null) {
            return NOT_SPECIFIED;
        }
        final String[] words = value.name().split("_");
        final StringBuilder result = new StringBuilder();
        for (final String word : words) {
            if (result.length() > 0) {
                result.append(' ');
            }
            result.append(word.charAt(0)).append(word.substring(1).toLowerCase(Locale.ROOT));
        }
        return result.toString();
    }

    private String escapeHtml(final String text) {
        return text.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    private static Component strut(final int height) {
        return javax.swing.Box.createVerticalStrut(height);
    }

    /** A small circular initials avatar, painted with the theme's accent color. */
    private static final class CircleAvatar extends JComponent {

        private final String initials;

        CircleAvatar(final String initials) {
            this.initials = initials;
            setPreferredSize(new Dimension(AVATAR_SIZE, AVATAR_SIZE));
            setMinimumSize(new Dimension(AVATAR_SIZE, AVATAR_SIZE));
            setMaximumSize(new Dimension(AVATAR_SIZE, AVATAR_SIZE));
        }

        @Override
        protected void paintComponent(final Graphics g) {
            final Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Theme.ACCENT);
            g2.fillOval(0, 0, getWidth(), getHeight());
            g2.setColor(Theme.FG_EMPHASIS);
            g2.setFont(getFont().deriveFont(Font.BOLD, 28f));
            final java.awt.FontMetrics fm = g2.getFontMetrics();
            final int textWidth = fm.stringWidth(this.initials);
            g2.drawString(this.initials,
                    (getWidth() - textWidth) / 2f,
                    (getHeight() - fm.getHeight()) / 2f + fm.getAscent());
            g2.dispose();
        }
    }
}

