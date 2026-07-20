package com.scholarmatch.frameworks.gui.component;

import com.scholarmatch.entity.Education;
import com.scholarmatch.entity.Publication;
import com.scholarmatch.frameworks.gui.style.Buttons;
import com.scholarmatch.frameworks.gui.style.Format;
import com.scholarmatch.frameworks.gui.style.Icons;
import com.scholarmatch.frameworks.gui.style.Reflowable;
import com.scholarmatch.frameworks.gui.style.RoundedPanel;
import com.scholarmatch.frameworks.gui.style.Theme;
import com.scholarmatch.usecase.dto.UserData;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeRegular;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;


import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
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
import java.util.Locale;

/**
 * Card widget that displays a single user's full profile — everything except
 * email and password, since those aren't relevant to deciding whether to connect.
 *
 * <p>Landscape layout: name/avatar/vitals across the top, then three columns
 * below — Education, Publications, and Research (description +
 * interests stacked). Used in com.scholarmatch.frameworks.gui.view.RecommendView
 * as the top card of the recommendation stack, with Dislike / Skip / Connect buttons
 * standing in for the drag-to-connect gesture from the previous JavaFX version.
 *
 * <p>Implements Reflowable: CenteringScrollPanel calls
 * reflow(int) whenever the scroll viewport is resized, so the card shrinks with
 * a narrow window instead of being clipped, and drops the three columns to a single
 * vertically-stacked column (each one extending downward) once too narrow to show them
 * side by side.
 *
 * <p>Deliberately independent of MatchedUserCard: the two cards show
 * different fields (this one omits email/phone, has action buttons) and are not
 * meant to share rendering code.
 */
public final class RecommendUserCard extends RoundedPanel implements Reflowable {


    private static final int MAX_CARD_WIDTH = 1040;
    private static final int MIN_CARD_WIDTH = 360;
    private static final int STACK_BREAKPOINT = 720;
    private static final int AVATAR_SIZE = 84;


    private final UserData cardUser;
    private final ConnectListener connectCallback;


    /**
     * Callback interface for connect decisions on this card.
     */
    public interface ConnectListener {
        /**
         * Called when the user permanently dislikes (rejects) the displayed user —
         * they will never be recommended again.
         */
        void onDislike();


        /**
         * Called when the user skips the displayed user for now — no permanent
         * exclusion, they may be recommended again on a future fetch.
         */
        void onSkip();


        /**
         * Called when the user chooses to connect with the displayed user.
         */
        void onConnect();
    }


    /**
     * Constructs a RecommendUserCard.
     *
     * @param user            the user whose profile this card displays
     * @param connectListener the callback for pass / connect decisions
     */
    public RecommendUserCard(final UserData user, final ConnectListener connectListener) {
        super(Theme.SCHOLAR_CARD_RADIUS, 24);
        this.cardUser = user;
        this.connectCallback = connectListener;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        reflow(MAX_CARD_WIDTH);
    }


    @Override
    public void reflow(final int width) {
        final int cardWidth = Math.max(MIN_CARD_WIDTH, Math.min(MAX_CARD_WIDTH, width));
        removeAll();
        setMaximumSize(new Dimension(cardWidth, Integer.MAX_VALUE));
        add(buildHeader(this.cardUser, cardWidth));
        add(verticalStrut(8));
        add(buildColumns(this.cardUser, cardWidth));
        add(verticalStrut(8));
        add(buildButtons(this.connectCallback));
        revalidate();
        repaint();
    }

    private JPanel buildHeader(final UserData user, final int cardWidth) {
        final CircleAvatar avatar = new CircleAvatar(initial(user.getFirstName()) + initial(user.getLastName()));

        final JLabel nameLabel = new JLabel(user.getFirstName() + " " + user.getLastName());
        nameLabel.setForeground(Theme.FG_DEFAULT);
        nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD, 26f));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        final JLabel institutionLabel = new JLabel(joinNonBlank(" · ",
                user.getInstitution() == null ? null : user.getInstitution().getDisplayName(),
                formatEnum(user.getAcademicLevel())));
        institutionLabel.setForeground(Theme.FG_MUTED);
        institutionLabel.setFont(institutionLabel.getFont().deriveFont(16f));
        institutionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        final JLabel lookingForLabel = new JLabel("Looking for: " + formatEnum(user.getLookingFor()));
        lookingForLabel.setForeground(Theme.FG_MUTED);
        lookingForLabel.setFont(lookingForLabel.getFont().deriveFont(14f));
        lookingForLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        final JLabel fundingAvailabilityLabel = new JLabel(joinNonBlank("   ",
                "Field: " + formatEnum(user.getResearchField()),
                "Funding: " + formatEnum(user.getFundingStatus()),
                "Availability: " + Format.stat(user.getWeeklyAvailabilityHours()) + " h/wk"));
        fundingAvailabilityLabel.setForeground(Theme.FG_MUTED);
        fundingAvailabilityLabel.setFont(fundingAvailabilityLabel.getFont().deriveFont(14f));
        fundingAvailabilityLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        final JLabel statsLabel = new JLabel(
                "h-index: " + Format.stat(user.gethIndex()) + "    Citations: " + Format.stat(user.getTotalCitations()));
        statsLabel.setForeground(Theme.FG_DEFAULT);
        statsLabel.setFont(statsLabel.getFont().deriveFont(Font.BOLD, 14f));
        statsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        final JPanel textBlock = new JPanel();
        textBlock.setLayout(new BoxLayout(textBlock, BoxLayout.Y_AXIS));
        textBlock.setOpaque(false);
        textBlock.setBorder(new EmptyBorder(0, 14, 0, 0));
        textBlock.add(nameLabel);
        textBlock.add(verticalStrut(4));
        textBlock.add(institutionLabel);
        textBlock.add(verticalStrut(2));
        textBlock.add(lookingForLabel);
        textBlock.add(verticalStrut(2));
        textBlock.add(fundingAvailabilityLabel);
        textBlock.add(verticalStrut(4));
        textBlock.add(statsLabel);
        if (!user.getCollaborationDescription().isBlank()) {
            textBlock.add(verticalStrut(4));
            textBlock.add(wrappedLabel(user.getCollaborationDescription(), cardWidth - AVATAR_SIZE - 90));
        }

        // BorderLayout.WEST stretches its child to the row's full height, which would squash
        // the circular avatar into an oval since the text block next to it is tall — wrap it
        // in a non-stretching FlowLayout cell so it keeps its fixed 64x64 size.
        final JPanel avatarWrapper = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));
        avatarWrapper.setOpaque(false);
        avatarWrapper.add(avatar);

        final JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setAlignmentX(Component.LEFT_ALIGNMENT);
        header.add(avatarWrapper, BorderLayout.WEST);
        header.add(textBlock, BorderLayout.CENTER);
        // Cap to preferred height so this row can't be stretched if an ancestor ever gives
        // the card more vertical space than its content needs.
        header.setMaximumSize(new Dimension(cardWidth, header.getPreferredSize().height));
        return header;
    }

    private JPanel buildColumns(final UserData user, final int cardWidth) {
        final boolean stacked = cardWidth < STACK_BREAKPOINT;
        final int columnWidth = stacked ? cardWidth : (cardWidth - 48 - 32) / 3;

        final JPanel educationColumn = column("EDUCATION", FontAwesomeSolid.GRADUATION_CAP);
        if (user.getEducations().isEmpty()) {
            educationColumn.add(mutedLine("None on file"));
        } else {
            for (final Education ed : user.getEducations()) {
                educationColumn.add(wrappedLabel(ed.getInstitution() + " — " + formatEnum(ed.getDegreeType())
                        + (ed.isOngoing() ? " (ongoing)" : ""), columnWidth));
                educationColumn.add(verticalStrut(8));
            }
        }

        final JPanel publicationsColumn = column("PUBLICATIONS", FontAwesomeSolid.BOOK);
        if (user.getPublications().isEmpty()) {
            publicationsColumn.add(mutedLine("None on file"));
        } else {
            for (final Publication publication : user.getPublications()) {
                publicationsColumn.add(wrappedLabel(
                        publication.getYear() > 0
                                ? publication.getTitle() + " (" + publication.getYear() + ")"
                                : publication.getTitle(),
                        columnWidth));
                publicationsColumn.add(verticalStrut(8));
            }
        }

        final JPanel researchColumn = column("RESEARCH DESCRIPTION", FontAwesomeSolid.FLASK);
        researchColumn.add(wrappedLabel(
                user.getResearchDescription().isBlank() ? "None on file" : user.getResearchDescription(),
                columnWidth));
        researchColumn.add(verticalStrut(10));
        researchColumn.add(columnHeading("RESEARCH INTERESTS", FontAwesomeSolid.TAGS));
        researchColumn.add(wrappedLabel(
                user.getResearchInterests().isEmpty() ? "None on file" : String.join(", ", user.getResearchInterests()),
                columnWidth));

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

        final JPanel columns = new JPanel(new GridLayout(1, 3, 16, 0));
        columns.setOpaque(false);
        columns.setAlignmentX(Component.LEFT_ALIGNMENT);
        columns.setMaximumSize(new Dimension(cardWidth, Integer.MAX_VALUE));
        columns.add(educationColumn);
        columns.add(publicationsColumn);
        columns.add(researchColumn);
        return columns;
    }

    private JPanel buildButtons(final ConnectListener connectListener) {
        final JButton dislikeButton = new JButton(
                "Dislike", Icons.of(FontAwesomeRegular.THUMBS_DOWN, 15, Theme.FG_EMPHASIS));
        Buttons.danger(dislikeButton);
        dislikeButton.setFont(dislikeButton.getFont().deriveFont(Font.BOLD, 14f));
        dislikeButton.setIconTextGap(8);
        dislikeButton.addActionListener(evt -> connectListener.onDislike());

        final JButton skipButton = new JButton(
                "Skip", Icons.of(FontAwesomeSolid.FORWARD, 15, Theme.FG_DEFAULT));
        Buttons.outlined(skipButton);
        skipButton.setFont(skipButton.getFont().deriveFont(Font.BOLD, 14f));
        skipButton.setIconTextGap(8);
        skipButton.addActionListener(evt -> connectListener.onSkip());

        final JButton connectButton = new JButton(
                "Connect", Icons.of(FontAwesomeSolid.CHECK, 15, Theme.FG_EMPHASIS));
        Buttons.success(connectButton);
        connectButton.setFont(connectButton.getFont().deriveFont(Font.BOLD, 14f));
        connectButton.setIconTextGap(8);
        connectButton.addActionListener(evt -> connectListener.onConnect());

        final JPanel buttonBox = new JPanel(new GridLayout(1, 3, 14, 0));
        buttonBox.setOpaque(false);
        buttonBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonBox.setPreferredSize(new Dimension(480, 48));
        buttonBox.setMaximumSize(new Dimension(480, 48));
        buttonBox.add(dislikeButton);
        buttonBox.add(skipButton);
        buttonBox.add(connectButton);
        return buttonBox;
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
        panel.add(verticalStrut(8));
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
        label.setFont(label.getFont().deriveFont(Font.PLAIN, 14f));
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
        label.setFont(label.getFont().deriveFont(14.5f));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Cap the maximum height to the label's own preferred height — otherwise BoxLayout
        // treats the unbounded default maximum as "willing to stretch" and inflates this
        // label to fill whatever leftover space GridLayout gives the tallest sibling column.
        label.setMaximumSize(new Dimension(columnWidth, label.getPreferredSize().height));
        return label;
    }

    /**
     * Returns the first character of a name for the avatar's initials, or "?" if the name
     * is null/blank — a bare {@code name.substring(0, 1)} throws
     * StringIndexOutOfBoundsException for an empty string.
     */
    private String initial(final String name) {
        return name == null || name.isBlank() ? "?" : name.substring(0, 1);
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
            return "";
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

    private static Component verticalStrut(final int height) {
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
