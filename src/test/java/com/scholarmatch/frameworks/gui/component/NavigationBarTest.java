package com.scholarmatch.frameworks.gui.component;

import com.scholarmatch.frameworks.gui.testsupport.SwingTestSupport;

import org.junit.jupiter.api.Test;

import javax.swing.JButton;
import javax.swing.JToggleButton;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NavigationBarTest {

    @Test
    void testEachToggleInvokesListenerWithItsOwnTarget() {
        final List<String> selections = new ArrayList<>();
        final NavigationBar navBar = new NavigationBar(selections::add, () -> { });

        final List<JToggleButton> toggles = SwingTestSupport.findAll(navBar, JToggleButton.class);
        assertEquals(8, toggles.size());
        for (final JToggleButton toggle : toggles) {
            toggle.doClick();
        }

        assertEquals(List.of(
                "recommend", "matched", "chat", "opportunities",
                "my-postings", "my-applications", "profile", "settings"), selections);
    }

    @Test
    void testLogoutButtonInvokesOnLogoutCallback() {
        final AtomicInteger logoutCount = new AtomicInteger();
        final NavigationBar navBar = new NavigationBar(target -> { }, logoutCount::incrementAndGet);

        final List<JButton> buttons = SwingTestSupport.findAll(navBar, JButton.class);
        assertEquals(1, buttons.size(), "Delete Account now lives in Account Settings, not the nav bar");
        buttons.get(0).doClick();

        assertEquals(1, logoutCount.get());
    }
}
